package com.zlt.blog.service.impl;

import com.zlt.blog.dao.CommentMapper;
import com.zlt.blog.entity.Comment;
import com.zlt.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查出父评论，也就是第一级评论
        List<Comment> comments = commentMapper.getCommentByBlogIdAndParentId(blogId,Long.parseLong("-1"));
        //return eachComment(comments);
        for(Comment comment : comments){
            Long id = comment.getId();
            String parentNickname1 = comment.getNickname();
            List<Comment> childComments = commentMapper.getCommentByBlogIdAndParentId(blogId,id);
//            查询出子评论
            combineChildren(blogId, childComments, parentNickname1);
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
        return comments;
    }

    private void combineChildren(Long blogId, List<Comment> childComments, String parentNickname1) {
//        判断是否有一级子评论
        if(childComments.size() > 0){
//                循环找出子评论的id
            for(Comment childComment : childComments){
                String parentNickname = childComment.getNickname();
                childComment.setParentNickname(parentNickname1);
                tempReplys.add(childComment);
                Long childId = childComment.getId();
//                    查询出子二级评论
                recursively(blogId, childId, parentNickname);
            }
        }
    }

    private void recursively(Long blogId, Long childId, String parentNickname1) {
//        根据子一级评论的id找到子二级评论
        List<Comment> replayComments = commentMapper.getCommentByBlogIdAndParentId(blogId,childId);

        if(replayComments.size() > 0){
            for(Comment replayComment : replayComments){
                String parentNickname = replayComment.getNickname();
                replayComment.setParentNickname(parentNickname1);
                Long replayId = replayComment.getId();
                tempReplys.add(replayComment);
                recursively(blogId,replayId,parentNickname);
            }
        }
    }

    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        return commentMapper.saveComment(comment);
    }

    @Override
    public void deleteComment(Long id) {
        ids.add(id);
        getDeleteCommentId(id);
        commentMapper.deleteComment(ids);
        ids.clear();
    }

    //用来存储查询得到要删除的评论的id
    private List<Long> ids = new ArrayList<>();

    //找出要删除的评论的所有子评论一起删除（避免数据库数据的多余）
    private void getDeleteCommentId(Long id){
        List<Long> commentIds = commentMapper.getAllDeleteComment(id);
        if (commentIds.size() > 0){
            for (Long commentId : commentIds){
                ids.add(commentId);
                getDeleteCommentId(commentId);
            }
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    

}
