package com.lsc.service.impl;

import com.lsc.domain.entity.UserRole;
import com.lsc.mapper.UserRoleMapper;
import com.lsc.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
