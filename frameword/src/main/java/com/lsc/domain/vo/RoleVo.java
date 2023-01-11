package com.lsc.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname RoleVo
 * @Description
 * @Date 2023/1/10 17:30
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo {
    private Long id;
    private String remark;
    private String roleKey;
    private String roleName;
    private Integer roleSort;
    private String status;
}
