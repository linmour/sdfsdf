package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.dto.ChangeStatusDto;
import com.lsc.domain.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
public interface RoleService extends IService<Role> {

    Result listL(Integer pageNum, Integer pageSize, String roleName, String status);

    Result changeStatus(ChangeStatusDto changStatusDto);

    Result add(Role role);

    Result getByIdL(Long id);

    Result updateL(Role role);

    Result delById(Long id);
}
