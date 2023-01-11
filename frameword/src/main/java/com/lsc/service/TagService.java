package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.dto.TagDto;
import com.lsc.domain.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 标签 服务类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
public interface TagService extends IService<Tag> {

    Result listL(Integer pageNum, Integer pageSize, String name, String remark);

    Result add(TagDto tagDto);

    Result delete(Long id);

    Result getTagById(Long id);

    Result updateByIdL(TagDto tagDto);

    Result listL();

}
