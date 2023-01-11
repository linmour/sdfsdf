package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Category;
import com.lsc.domain.entity.Link;
import com.lsc.domain.vo.LinkVo;
import com.lsc.domain.vo.PageVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.mapper.LinkMapper;
import com.lsc.service.LinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.lsc.constants.Constants.LINK_STATUS_NORMAL;

/**
 * <p>
 * 友链 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-11
 */
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public Result getAllLink() {
        //查询所有审核通过的link
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus,LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        //封装vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return Result.okResult(linkVos);
    }

    @Override
    public Result listL(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> linkLambdaQueryWrapper = new LambdaQueryWrapper<>();
        linkLambdaQueryWrapper.eq(StringUtils.hasText(status),Link::getStatus,status)
                .like(StringUtils.hasText(name),Link::getName,name);
        Page<Link> page = new Page<>(pageNum,pageSize);
        page(page,linkLambdaQueryWrapper);
        return Result.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    @Override
    public Result add(Link link) {


        if (save(link))
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result getByIdL(Long id) {
        Link link = getById(id);
        return Result.okResult(link);
    }

    @Override
    public Result updateL(Link link) {
        boolean b = updateById(link);
        if (b)
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result del(Long id) {

        LambdaUpdateWrapper<Link> linkLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        linkLambdaUpdateWrapper.eq(Link::getId,id)
                .set(Link::getDelFlag,1);

        if (update(linkLambdaUpdateWrapper))
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }

    @Override
    public Result changeLinkStatus(Link link) {
        LambdaUpdateWrapper<Link> linkLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        linkLambdaUpdateWrapper.eq(Link::getId,link.getId())
                .set(Link::getStatus,link.getStatus());
        if(update(linkLambdaUpdateWrapper))
            return Result.okResult();
        return Result.errorResult(AppHttpCodeEnum.ERROR);
    }
}
