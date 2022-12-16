package com.lsc.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Classname hotArticleVo
 * @Description
 * @Date 2022/12/10 20:37
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    private String status;
    private Long viewCount;
}
