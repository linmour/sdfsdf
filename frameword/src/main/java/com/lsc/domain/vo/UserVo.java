package com.lsc.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Classname UserVo
 * @Description
 * @Date 2023/1/10 21:30
 * @Created by linmour
 */
@AllArgsConstructor
@NoArgsConstructor
@Data

public class UserVo {
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "用户类型：0代表普通用户，1代表管理员")
    private String type;

    @ApiModelProperty(value = "账号状态（0正常 1停用）")
    private String status;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phonenumber;

    @ApiModelProperty(value = "用户性别（0男，1女，2未知）")
    private String sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "更新人")
    private Long updateBy;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
}
