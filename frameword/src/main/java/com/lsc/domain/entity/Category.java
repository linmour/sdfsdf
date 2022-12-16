package com.lsc.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分类表
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Category对象", description="分类表")

public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "分类名")
    private String name;

    @ApiModelProperty(value = "父分类id，如果没有父分类为-1")
    private Long pid;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "状态0:正常,1禁用")
    private String status;

    private Long createBy;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;


}
