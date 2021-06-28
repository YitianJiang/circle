package com.acircle.circle.service.impl;

import com.acircle.circle.mapper.CommentMapper;
import com.acircle.circle.model.Comment;
import com.acircle.circle.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public long create(Comment comment){
        commentMapper.insertSelective(comment);
        return comment.getId();
    }

    @Override
    public long delete(long id){
        return commentMapper.deleteByPrimaryKey(id);
    }

}
