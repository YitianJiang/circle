package com.acircle.circle.service.impl;

import com.acircle.circle.dao.ArticleDao;
import com.acircle.circle.dao.CommentDao;
import com.acircle.circle.dto.ArticleAbstract;
import com.acircle.circle.dto.CommentDetail;
import com.acircle.circle.dto.CommentWithArticleInfo;
import com.acircle.circle.mapper.CommentMapper;
import com.acircle.circle.model.Comment;
import com.acircle.circle.model.CommentExample;
import com.acircle.circle.service.ArticleService;
import com.acircle.circle.service.CommentService;
import com.acircle.circle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private UserService userService;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ArticleService articleService;

    @Override
    public long create(Comment comment){
        comment.setFromUserId(userService.getCurrentUserBaseInfoByJWT().getId());
        commentMapper.insertSelective(comment);
        return comment.getId();
    }

    @Override
    public List<CommentWithArticleInfo> getCommentWithArticleInfosByUserId(int pageNum, int pageSize){
        List<CommentWithArticleInfo> commentWithArticleInfos = commentDao.getComments(userService.getCurrentUserBaseInfoByJWT().getId(),pageNum * pageSize,pageSize);
        //填充文章简要
        for(int i = 0; i < commentWithArticleInfos.size(); i++){
            ArticleAbstract articleAbstract = articleService.getArticleAbstractByArticleId(commentWithArticleInfos.get(i).getArticleId()).get(0);
            commentWithArticleInfos.get(i).setArticleAbstract(articleAbstract);
        }
        return commentWithArticleInfos;
    }

    @Override
    public  List<CommentDetail>  getCommentDetailsByArticleId(long articleId, int pageNum, int pageSize){
        List<CommentDetail> commentDetailsByArticleId = commentDao.getCommentDetailsByArticleId(articleId, pageNum * pageSize, pageSize);
        if(commentDetailsByArticleId.size() == 1 && commentDetailsByArticleId.get(0) == null){
            commentDetailsByArticleId.remove(0);
        }
        return commentDetailsByArticleId;
    }

    @Override
    public long delete(long id){
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdEqualTo(id).andFromUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId());
        return commentMapper.deleteByExample(commentExample);
    }

    @Override
    public long batchDelete(List<Long> commentIds){
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andIdIn(commentIds).andFromUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId());
        return commentMapper.deleteByExample(commentExample);
    }

    @Override
    public long deleteAll(){
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andFromUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId());
        return commentMapper.deleteByExample(commentExample);
    }
}
