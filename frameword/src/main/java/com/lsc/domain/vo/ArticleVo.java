package com.lsc.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Classname ArticleVo
 * @Description
 * @Date 2023/1/9 20:50
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleVo {
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "所属分类id")
    private Long categoryId;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "是否置顶（0否，1是）")
    private String isTop;

    @ApiModelProperty(value = "状态（0已发布，1草稿）")
    private String status;

    @ApiModelProperty(value = "访问量")
    private Long viewCount;

    @ApiModelProperty(value = "是否允许评论 1是，0否")
    private String isComment;


}
