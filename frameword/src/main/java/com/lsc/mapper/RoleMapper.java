package com.lsc.mapper;

import com.lsc.domain.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
public interface RoleMapper extends BaseMapper<Role> {

    Role getByIdL(Long id);
}
