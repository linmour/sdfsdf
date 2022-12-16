package com.lsc.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Classname CommentVo
 * @Description
 * @Date 2022/12/13 16:08
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {

    private Long id;


    @ApiModelProperty(value = "文章id")
    private Long articleId;

    @ApiModelProperty(value = "根评论id")
    private Long rootId;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "所回复的目标评论的userid")
    private Long toCommentUserId;

    @ApiModelProperty(value = "所回复的目标评论的用户名")
    private String toCommentUserName;

    @ApiModelProperty(value = "回复目标评论id")
    private Long toCommentId;

    @ApiModelProperty(value = "评论人的id")
    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "评论人的昵称")
    private String username;

    @ApiModelProperty(value = "子评论")
    private List<CommentVo> children;


}
