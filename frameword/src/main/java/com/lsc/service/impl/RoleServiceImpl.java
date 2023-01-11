package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsc.domain.Result;
import com.lsc.domain.dto.ChangeStatusDto;
import com.lsc.domain.entity.ArticleTag;
import com.lsc.domain.entity.Role;
import com.lsc.domain.entity.RoleMenu;
import com.lsc.domain.vo.PageVo;
import com.lsc.domain.vo.RoleVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.mapper.RoleMapper;
import com.lsc.service.ArticleTagService;
import com.lsc.service.RoleMenuService;
import com.lsc.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import com.sun.xml.internal.ws.message.RootElementSniffer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuService roleMenuService;

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private RoleService roleService;

    @Override
    public Result listL(Integer pageNum, Integer pageSize, String roleName, String status) {

        LambdaUpdateWrapper<Role> roleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        roleLambdaUpdateWrapper.eq(StringUtils.hasText(status),Role::getStatus,status)
                .like(StringUtils.hasText(roleName),Role::getRoleName,roleName)
                .orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum,pageSize);
        Page<Role> rolePage = page(page, roleLambdaUpdateWrapper);
        return Result.okResult(new PageVo(rolePage.getRecords(), rolePage.getTotal()));

    }

    @Override
    public Result changeStatus(ChangeStatusDto changStatusDto) {
        LambdaUpdateWrapper<Role> roleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        roleLambdaUpdateWrapper.eq(Role::getId,changStatusDto.getRoleId())
                .set(Role::getStatus,changStatusDto.getStatus());
        boolean update = update(roleLambdaUpdateWrapper);
        if (update)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    @Transactional
    public Result add(Role role) {
        save(role);
        List<Long> menuIds = role.getMenuIds();
        List<RoleMenu> collect = menuIds.stream()
                .map(s -> new RoleMenu(role.getId(), s))
                .collect(Collectors.toList());
        boolean b = roleMenuService.saveBatch(collect);
        if (b)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result getByIdL(Long id) {
        Role role = roleMapper.getByIdL(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return Result.okResult(roleVo);
    }

    @Override
    public Result updateL(Role role) {
        //TODO DATE
        updateById(role);
        List<RoleMenu> collect = role.getMenuIds().stream()
                .map(m -> new RoleMenu(role.getId(), m))
                .collect(Collectors.toList());
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(roleMenuLambdaQueryWrapper);
        roleMenuService.saveBatch(collect);
        return Result.okResult();
    }

    @Override
    public Result delById(Long id) {
        LambdaUpdateWrapper<Role> roleLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        roleLambdaUpdateWrapper.eq(Role::getId,id)
                .set(Role::getDelFlag,1);
        roleService.update(roleLambdaUpdateWrapper);
        return Result.okResult();
    }
}
