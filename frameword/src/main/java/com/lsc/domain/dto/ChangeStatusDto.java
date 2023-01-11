package com.lsc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname changStatusDto
 * @Description
 * @Date 2023/1/10 10:50
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStatusDto {
    private String roleId;
    private String status;
}
