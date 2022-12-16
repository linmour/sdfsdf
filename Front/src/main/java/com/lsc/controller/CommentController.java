package com.lsc.controller;


import com.lsc.domain.Result;
import com.lsc.domain.entity.Comment;
import com.lsc.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import static com.lsc.constants.Constants.ARTICLE_COMMENT;
import static com.lsc.constants.Constants.LINK_COMMENT;

/**
 * <p>
 * 评论表 前端控制器
 * </p>
 *
 * @author linmour
 * @since 2022-12-13
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    private CommentService commentService;

    @GetMapping("/commentList")
    public Result commentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.commentList(ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @GetMapping("/linkCommentList")
    public Result linkCommentList(Integer pageNum,Integer pageSize){
        return commentService.commentList(LINK_COMMENT,null,pageNum,pageSize);
    }

    @PostMapping()
    public Result addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }
}
