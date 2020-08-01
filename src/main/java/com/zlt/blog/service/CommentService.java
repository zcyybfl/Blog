package com.zlt.blog.service;

import com.zlt.blog.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> listCommentByBlogId(Long id);

    int saveComment(Comment comment);
}
