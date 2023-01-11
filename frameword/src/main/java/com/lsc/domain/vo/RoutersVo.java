package com.lsc.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname RoutersVo
 * @Description
 * @Date 2023/1/8 9:24
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoutersVo {
    private List<MenuVo> menus;
}
