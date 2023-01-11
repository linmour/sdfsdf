package com.lsc.mapper;

import com.lsc.domain.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsc.domain.vo.MenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<MenuVo> RouterById(@Param("userId") Long userId);
}
