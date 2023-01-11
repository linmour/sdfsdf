package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Menu;
import com.lsc.domain.entity.RoleMenu;
import com.lsc.domain.vo.MenuVo;
import com.lsc.domain.vo.RoutersVo;
import com.lsc.domain.vo.roleMenuTreeselectVo;
import com.lsc.domain.vo.treeSelectVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.mapper.MenuMapper;
import com.lsc.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.service.RoleMenuService;
import com.lsc.utils.BeanConvertUtils;
import com.lsc.utils.BeanCopyUtils;
import com.lsc.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private MenuService menuservice;
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMenuService roleMenuService;

    @Override
    public Result getRouters(Long userId) {
        List<MenuVo> menuVos = null;
        RoutersVo routersVo = new RoutersVo();
        if (SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
            menuLambdaQueryWrapper
                    .eq(Menu::getStatus,0)
                    .eq(Menu::getDelFlag,0)
                    .in(Menu::getMenuType,"C","M");
            List<Menu> parentIds = menuservice.list(menuLambdaQueryWrapper);

            menuVos = BeanCopyUtils.copyBeanList(parentIds, MenuVo.class);
        }else{
            //用sql查
            menuVos = menuMapper.RouterById(userId);
        }

        //构建tree
        //找出父目录
        List<MenuVo> menuTree = buildTree(menuVos);
        routersVo.setMenus(menuTree);
        return Result.okResult(routersVo);
    }

    @Override
    public Result listL(String status, String menuName) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.like(StringUtils.hasText(menuName),Menu::getMenuName,menuName)
                .eq(StringUtils.hasText(status),Menu::getStatus,status)
                .orderByDesc(Menu::getId)
                .orderByDesc(Menu::getOrderNum);
        List<Menu> list = menuservice.list(menuLambdaQueryWrapper);
        return Result.okResult(list);

    }

    @Override
    public Result addMenu(Menu menu) {
        boolean save = save(menu);
        if (save)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);

    }

    @Override
    public Result getMenuById(Long id) {

        return Result.okResult(getById(id));
    }

    @Override
    public Result updateL(Menu menu) {
        if (menu.getId().equals(menu.getParentId()))
            return Result.errorResult(500,"修改菜单'"+menu.getMenuName()+"'失败，上级菜单不能选择自己");
        boolean b = updateById(menu);
        if (b)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result delById(Long id) {
        LambdaQueryWrapper<Menu> menuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        menuLambdaQueryWrapper.eq(Menu::getParentId,id);
        int count = menuservice.count(menuLambdaQueryWrapper);
        if (count>0)
            return Result.errorResult(500,"存在子菜单不允许删除");
        LambdaUpdateWrapper<Menu> menuLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        menuLambdaUpdateWrapper.eq(Menu::getId,id)
                .set(Menu::getDelFlag,1);
        boolean b = menuservice.update(menuLambdaUpdateWrapper);
        if (b)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result treeSelect() {
        List<Menu> list = menuservice.list();
        List<treeSelectVo> treeSelectVos = BeanCopyUtils.copyBeanList(list, treeSelectVo.class);
        List<treeSelectVo> collect = treeSelectVos.stream()
                .map(treeSelectVo -> treeSelectVo.setLabel(treeSelectVo.getMenuName()))
                .collect(Collectors.toList());
        List<treeSelectVo> treeSelectVos1 = buildTree1(collect);

        return Result.okResult(treeSelectVos1);

    }

    @Override
    public Result roleMenuTreeselect(Long id) {
        LambdaUpdateWrapper<RoleMenu> roleMenuLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        roleMenuLambdaUpdateWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> list = roleMenuService.list(roleMenuLambdaUpdateWrapper);
        List<Long> collect = list.stream()
                .map(roleMenu -> roleMenu.getMenuId())
                .collect(Collectors.toList());
        List<Menu> menus = menuservice.listByIds(collect);
        List<treeSelectVo> treeSelectVos = BeanCopyUtils.copyBeanList(menus, treeSelectVo.class);
        List<treeSelectVo> collect1 = treeSelectVos.stream()
                .map(treeSelectVo -> treeSelectVo.setLabel(treeSelectVo.getMenuName()))
                .collect(Collectors.toList());
        List<treeSelectVo> treeSelectVos1 = buildTree1(collect1);

        List<Menu> list1 = menuservice.list();
        List<treeSelectVo> treeSelectVos2 = BeanCopyUtils.copyBeanList(list1, treeSelectVo.class);
        List<treeSelectVo> collect2 = treeSelectVos2.stream()
                .map(treeSelectVo -> treeSelectVo.setLabel(treeSelectVo.getMenuName()))
                .collect(Collectors.toList());
        List<treeSelectVo> treeSelectVos3 = buildTree1(collect2);
        return Result.okResult( new roleMenuTreeselectVo(treeSelectVos3,collect));
    }

    private List<MenuVo> buildTree(List<MenuVo> menuVos) {
        List<MenuVo> collect = menuVos.stream()
                //找出父目录
                .filter(menuVo -> menuVo.getParentId().equals(0L))
                //把父目录和所有的比较找出子目录
                .map(menuVo -> menuVo.setChildren(getChildrens(menuVo, menuVos)))
                .collect(Collectors.toList());
        return collect;
    }

    private List<MenuVo> getChildrens(MenuVo menuVo, List<MenuVo> menuVos) {
        List<MenuVo> children = menuVos.stream()
                //如果id和父目录一样就说明是子目录
                .filter(m -> m.getParentId().equals(menuVo.getId()))
                //这个是找多级目录
                .map(m -> m.setChildren(getChildrens(m,menuVos)))
                .collect(Collectors.toList());
        return children;
    }



    private List<treeSelectVo> buildTree1(List<treeSelectVo> menuVos) {
        List<treeSelectVo> collect = menuVos.stream()
                //找出父目录
                .filter(menuVo -> menuVo.getParentId().equals(0L))
                //把父目录和所有的比较找出子目录
                .map(menuVo -> menuVo.setChildren(getChildrens1(menuVo, menuVos)))
                .collect(Collectors.toList());
        return collect;
    }
    private List<treeSelectVo> getChildrens1(treeSelectVo menuVo, List<treeSelectVo> menuVos) {
        List<treeSelectVo> children = menuVos.stream()
                //如果id和父目录一样就说明是子目录
                .filter(m -> m.getParentId().equals(menuVo.getId()))
                //这个是找多级目录
                .map(m -> m.setChildren(getChildrens1(m,menuVos)))
                .collect(Collectors.toList());
        return children;
    }


}
