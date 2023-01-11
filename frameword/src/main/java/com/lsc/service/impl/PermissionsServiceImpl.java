package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsc.domain.Result;
import com.lsc.domain.entity.*;
import com.lsc.domain.vo.UserInfoVo;
import com.lsc.service.*;
import com.lsc.utils.BeanCopyUtils;
import com.lsc.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname PermissionsServiceImpl
 * @Description
 * @Date 2023/1/6 21:40
 * @Created by linmour
 */
@Service
public class PermissionsServiceImpl implements PermissionsService {

    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;
    @Resource
    private RoleMenuService roleMenuService;
    @Resource
    private MenuService menuService;

    @Override
    public Result getInfo() {
       //如果是管理员就返回所有权限
        List<String> list = new ArrayList<>();
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType,"C","F")
                    .eq(Menu::getStatus, 0)
                    .eq(Menu::getDelFlag,0);

            List<Menu> menus = menuService.list(wrapper);
            List<String> perms = menus.stream()
                    .map(Menu::getPerms)
                    .collect(Collectors.toList());
            UserInfoVo userInfoVo = BeanCopyUtils.copyBean(SecurityUtils.getLoginUser().getUser(), UserInfoVo.class);

            list.add("admin");
            AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, list, userInfoVo);
            return Result.okResult(adminUserInfoVo);

        }

        //根据id查询角色信息
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,SecurityUtils.getUserId());
        UserRole role_id = userRoleService.getOne(userRoleLambdaQueryWrapper);
        Role role_key = roleService.getById(role_id.getRoleId());

        //根据角色id查找权限信息
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId,role_id.getRoleId());
        List<RoleMenu> Menulist = roleMenuService.list(roleMenuLambdaQueryWrapper);
        List<Long> MenuIds = Menulist.stream()
                .map(RoleMenu::getMenuId)
                .collect(Collectors.toList());

        List<Menu> menus = menuService.listByIds(MenuIds);
        List<String> perms = menus.stream()
                .filter(menu -> menu.getPerms() != null)
                .filter(menu -> menu.getStatus().equals("0") && menu.getDelFlag().equals("0"))
                .filter(menu -> menu.getMenuType().equals("C")||menu.getMenuType().equals("F"))
                .map(Menu::getPerms)
                .collect(Collectors.toList());

        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(SecurityUtils.getLoginUser().getUser(), UserInfoVo.class);
        list.add(role_key.getRoleKey());
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, list, userInfoVo);
        return Result.okResult(adminUserInfoVo);
}
}
