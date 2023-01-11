package com.lsc.domain.entity;

import com.lsc.domain.vo.UserInfoVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname AdminUserInfoVo
 * @Description
 * @Date 2023/1/7 19:20
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserInfoVo {
    private List<String> permissions;
    private List<String> roles;
    private UserInfoVo user;
}
