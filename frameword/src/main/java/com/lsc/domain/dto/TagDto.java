package com.lsc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname TagDto
 * @Description
 * @Date 2023/1/8 19:42
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;
    private String remark;
}
