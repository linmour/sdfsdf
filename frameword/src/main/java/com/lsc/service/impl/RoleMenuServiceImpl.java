package com.lsc.service.impl;

import com.lsc.domain.entity.RoleMenu;
import com.lsc.mapper.RoleMenuMapper;
import com.lsc.service.RoleMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
