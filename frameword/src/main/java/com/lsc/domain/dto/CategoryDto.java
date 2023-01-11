package com.lsc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname CategoryDto
 * @Description
 * @Date 2023/1/9 10:06
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
    private String status;
}
