package com.lsc.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname PageVo
 * @Description
 * @Date 2022/12/11 10:40
 * @Created by linmour
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageVo {

    private List rows;
    private Long total;
}
