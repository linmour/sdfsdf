package com.lsc.service;

import com.lsc.domain.Result;
import com.lsc.domain.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author linmour
 * @since 2022-12-13
 */
public interface CommentService extends IService<Comment> {

    Result commentList(String CommentType, Long articleId, Integer pageNum, Integer pageSize);

    Result addComment(Comment comment);
}
