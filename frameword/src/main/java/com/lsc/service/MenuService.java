package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
public interface MenuService extends IService<Menu> {

    Result getRouters(Long userId);


    Result listL(String status, String menuName);

    Result addMenu(Menu menu);

    Result getMenuById(Long id);

    Result updateL(Menu menu);

    Result delById(Long id);

    Result treeSelect();

    Result roleMenuTreeselect(Long id);
}
