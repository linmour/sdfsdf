package com.lsc.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname LinkVo
 * @Description
 * @Date 2022/12/11 22:00
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinkVo {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String logo;

    private String description;

    @ApiModelProperty(value = "网站地址")
    private String address;

}
