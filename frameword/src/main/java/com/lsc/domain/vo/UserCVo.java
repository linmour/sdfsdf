package com.lsc.domain.vo;

import com.lsc.domain.entity.Role;
import com.lsc.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname UserCVo
 * @Description
 * @Date 2023/1/11 9:12
 * @Created by linmour
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private UserVo user;
}
