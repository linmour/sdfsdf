package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsc.domain.Result;
import com.lsc.domain.dto.TagDto;
import com.lsc.domain.entity.Tag;
import com.lsc.domain.entity.User;
import com.lsc.domain.vo.PageVo;
import com.lsc.domain.vo.TagVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.mapper.TagMapper;
import com.lsc.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import com.lsc.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 标签 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2023-01-06
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private TagMapper tagMapper;
    @Resource
    private TagService tagService;

    @Override
    public Result listL(Integer pageNum, Integer pageSize, @RequestParam(value = "name",required = false) String name, @RequestParam(value = "remark",required = false)String remark) {
        LambdaQueryWrapper<Tag> tagLambdaQueryWrapper = new LambdaQueryWrapper<>();
        tagLambdaQueryWrapper.like(StringUtils.hasText(name),Tag::getName,name)
                .like(StringUtils.hasText(remark),Tag::getRemark,remark)
                .eq(Tag::getDelFlag,0);
        Page<Tag> page = new Page<>(pageNum,pageSize);
        page(page,tagLambdaQueryWrapper);

        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(page.getRecords(), TagVo.class);

        return Result.okResult(new PageVo(tagVos, page.getTotal()));
    }

    @Override
    public Result add(TagDto tagDto) {
        if (StringUtils.hasText(tagDto.getName())){
            Tag tag = BeanCopyUtils.copyBean(tagDto, Tag.class);
            int insert = tagMapper.insert(tag);
            if (insert>0)
                return Result.okResult();
        }
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result delete(Long id) {
        LambdaUpdateWrapper<Tag> tagLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        tagLambdaUpdateWrapper.eq(Tag::getId,id)
                .set(Tag::getDelFlag,1);

        int update = tagMapper.update(null, tagLambdaUpdateWrapper);

        if (update>0)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result getTagById(Long id) {
        LambdaQueryWrapper lambdaQueryWrapper = new LambdaQueryWrapper();
        Tag tag = tagService.getById(id);
        TagVo tagVo = BeanCopyUtils.copyBean(tag, TagVo.class);
        return Result.okResult(tagVo);
    }

    @Override
    public Result updateByIdL(TagDto tagDto) {
        LambdaUpdateWrapper<Tag> tagLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        tagLambdaUpdateWrapper.eq(Tag::getId,tagDto.getId())
                .set(Tag::getName,tagDto.getName())
                .set(Tag::getRemark,tagDto.getRemark());
        boolean update = tagService.update(null, tagLambdaUpdateWrapper);
        if(update)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result listL() {
        LambdaUpdateWrapper<Tag> tagLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        tagLambdaUpdateWrapper.eq(Tag::getDelFlag,0);
        List<Tag> list = tagService.list(tagLambdaUpdateWrapper);
        return Result.okResult(BeanCopyUtils.copyBeanList(list,TagVo.class));

    }
}
