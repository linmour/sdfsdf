package com.lsc.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Classname roleMenuTreeselectVo
 * @Description
 * @Date 2023/1/10 18:55
 * @Created by linmour
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class roleMenuTreeselectVo {
    private List<treeSelectVo> menus;
    private List<Long> checkedKeys;
}
