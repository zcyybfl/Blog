package com.zlt.blog.controller;

import com.zlt.blog.entity.Comment;
import com.zlt.blog.entity.User;
import com.zlt.blog.service.BlogService;
import com.zlt.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session,Model model){
        Long blogId = comment.getBlogId();
        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            if (user.getType()==1){
                comment.setAdminComment(true);
            }else {
                comment.setAdminComment(false);
            }

            //comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);
        }


        if (comment.getParentComment().getId() != null){
            comment.setParentCommentId(comment.getParentComment().getId());
        }

        commentService.saveComment(comment);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments",comments);

        return "blog :: commentList";
    }

    @PostMapping("/comments/delete")
    public String delete(Long id,Long blogId,Model model){
        commentService.deleteComment(id);
        model.addAttribute("comments",commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }
}
