package com.lsc.domain.vo;

import com.lsc.annotation.ConvertField;
import com.sun.org.apache.xpath.internal.SourceTree;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Classname treeSelectVo
 * @Description
 * @Date 2023/1/10 11:27
 * @Created by linmour
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class treeSelectVo {
    private List<treeSelectVo> children;
    private Long id;
    @ConvertField(targetName = "menuName")
    private String label;
    private String menuName;
    private Long parentId;
}
