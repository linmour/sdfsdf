package com.lsc.domain.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author linmour
 * @since 2022-12-13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Comment对象", description="评论表")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "评论类型（0代表文章评论，1代表友链评论）")
    private String type;

    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "根评论id")
    private Long rootId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "所回复的目标评论的userid")
    private Long toCommentUserId;

    @ApiModelProperty(value = "回复目标评论id")
    private Long toCommentId;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;


    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;


    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;


}
