package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Link;
import com.lsc.domain.vo.LinkVo;
import com.lsc.mapper.LinkMapper;
import com.lsc.service.LinkService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

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
}
