package com.lsc.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文章表
 * </p>
 *
 * @author linmour
 * @since 2022-12-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Article对象", description="文章表")
@Accessors(chain = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "所属分类id")
    private Long categoryId;

    @TableField(exist = false)
    private String categoryName;

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

    private Long createBy;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;

    @ApiModelProperty(value = "删除标志（0代表未删除，1代表已删除）")
    private Integer delFlag;


    public Article(Long id, long viewCount) {
        this.id = id;
        this.viewCount = viewCount;
    }
}
