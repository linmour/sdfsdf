package com.lsc.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Category;
import com.lsc.domain.entity.Role;
import com.lsc.domain.entity.User;
import com.lsc.domain.entity.UserRole;
import com.lsc.domain.vo.PageVo;
import com.lsc.domain.vo.UserCVo;
import com.lsc.domain.vo.UserInfoVo;
import com.lsc.domain.vo.UserVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.exception.SystemException;
import com.lsc.mapper.UserMapper;
import com.lsc.service.RoleService;
import com.lsc.service.UserRoleService;
import com.lsc.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import kotlin.jvm.internal.Lambda;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.lsc.utils.SecurityUtils.getUserId;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;


    @Override
    public Result userInfo() {
        User user = getById(getUserId());
        UserInfoVo userInfoVo = null;
        if (Objects.nonNull(user)) {
            userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        }
        return Result.okResult(userInfoVo);

    }

    @Override
    public Result updateUserInfo(User user) {
        updateById(user);
        return Result.okResult();
    }

    @Override
    public Result register(User user) {
        //对数据进行非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //对数据进行是否存在的判断
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }

        //对密码进行加密
        String encode = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入数据库
        save(user);
        return Result.okResult();
    }

    @Override
    public Result listL(Integer pageNum, Integer pageSize, String userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber)
                .like(StringUtils.hasText(userName),User::getUserName,userName)
                .like(StringUtils.hasText(status),User::getStatus,status);
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,userLambdaQueryWrapper);
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserVo.class);
        return Result.okResult(new PageVo(userVos,page.getTotal()));

    }

    private boolean userNameExist(String userName){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getUserName,userName);
        return count(userLambdaQueryWrapper)>0;
    }

    private boolean nickNameExist(String nickName){
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getNickName,nickName);
        return count(userLambdaQueryWrapper)>0;
    }

    @Override
    public Result delById(Long id) {

        LambdaUpdateWrapper<User> categoryLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        categoryLambdaUpdateWrapper.eq(User::getId,id)
                .set(User::getDelFlag,1);
        boolean update = update(categoryLambdaUpdateWrapper);
        if (update)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result getByIdL(Long id) {
        User user = getById(id);
        UserVo userVo = BeanCopyUtils.copyBean(user, UserVo.class);

        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,id);
        List<UserRole> list = userRoleService.list(userRoleLambdaQueryWrapper);
        List<Long> collect = list.stream()
                .map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList());
            List<Role> roles = roleService.list();

        return Result.okResult(new UserCVo(collect,roles,userVo));


    }

    @Override
    @Transactional
    public Result updateL(User user) {

        updateById(user);

        List<UserRole> collect = user.getRoleIds().stream()
                .map(a -> new UserRole(user.getId(), a))
                .collect(Collectors.toList());
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,user.getId());
        if(userRoleService.remove(userRoleLambdaQueryWrapper))
            return Result.errorResult(AppHttpCodeEnum.ERROR);
        if(userRoleService.saveBatch(collect))
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }
}
