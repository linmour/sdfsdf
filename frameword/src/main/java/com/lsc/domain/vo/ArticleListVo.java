package com.lsc.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Classname ArticleListVo
 * @Description
 * @Date 2022/12/11 10:34
 * @Created by linmour
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleListVo {

    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "文章摘要")
    private String summary;

    @ApiModelProperty(value = "所属分类名字")
    private String categoryName;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "访问量")
    private Long viewCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
