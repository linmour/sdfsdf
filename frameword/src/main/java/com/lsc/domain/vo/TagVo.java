package com.lsc.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Classname TagVo
 * @Description
 * @Date 2023/1/8 19:13
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagVo {
    private Long id;

    @ApiModelProperty(value = "标签名")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;
}
