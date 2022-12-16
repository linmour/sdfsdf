package com.lsc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsc.domain.Result;
import com.lsc.domain.entity.Comment;
import com.lsc.domain.vo.CommentVo;
import com.lsc.domain.vo.PageVo;
import com.lsc.enums.AppHttpCodeEnum;
import com.lsc.exception.SystemException;
import com.lsc.mapper.CommentMapper;
import com.lsc.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsc.service.UserService;
import com.lsc.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

import static com.lsc.constants.Constants.ARTICLE_COMMENT;
import static com.lsc.constants.Constants.ROOT_COMMENT;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author linmour
 * @since 2022-12-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserService userService;

    @Resource
    private CommentService commentService;
    @Override
    public Result commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        LambdaQueryWrapper<Comment> lambdaQueryWrapper = new LambdaQueryWrapper<>();


        //判断评论的类型，查找对应的文章
        lambdaQueryWrapper.eq(ARTICLE_COMMENT.equals(commentType),Comment::getArticleId,articleId);

        //评论类型
        lambdaQueryWrapper.eq(Comment::getType,commentType);

        //查找文章下的根评论
        lambdaQueryWrapper.eq(Comment::getRootId,ROOT_COMMENT);

        Page<Comment> page = new Page<>(pageNum,pageSize);
        page(page,lambdaQueryWrapper);

        List<CommentVo> commentVos =toCommentVoList(page.getRecords());

        //查询所有根评论对应的子评论集合，并且赋值给对应的属性
        commentVos.stream()
                .forEach(commentVo -> commentVo.setChildren(getChildren(commentVo.getId())));

        return Result.okResult(new PageVo(commentVos,page.getTotal()));
    }

    @Override
    public Result addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return Result.okResult();
    }

    /**
     * 根据根评论的id查询所对应的子评论的集合
     * @param id 根评论的id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;
    }


    //这个是实现comment到commentvo的转化
    private List<CommentVo> toCommentVoList(List<Comment> list){

        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        //设置发表评论人的昵称和回复人的昵称
        commentVos.stream()
                .filter(commentVo -> commentVo.getCreateBy() != null)
                .forEach(commentVo -> commentVo.setUsername(userService.getById(commentVo.getCreateBy()).getNickName()));
        commentVos.stream()
                .filter(commentVo -> commentVo.getToCommentUserId()!=-1)
                .forEach(commentVo -> commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName()));


        return commentVos;

    }
}
