package com.lsc.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 用户和角色关联表
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

@EqualsAndHashCode(callSuper = false)
@TableName("sys_user_role")
@ApiModel(value="UserRole对象", description="用户和角色关联表")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;


}
