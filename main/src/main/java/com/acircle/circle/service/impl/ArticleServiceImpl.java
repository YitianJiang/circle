package com.acircle.circle.service.impl;

import com.acircle.circle.dao.*;
import com.acircle.circle.dto.*;
import com.acircle.circle.mapper.ArticleMapper;
import com.acircle.circle.model.Image;
import com.acircle.circle.service.ArticleService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sankuai.inf.leaf.api.SnowflakeService;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService{
    @Autowired
    private  ArticleMapper articleMapper;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private LikeDao likeDao;
    @Autowired
    private CommentDao commentDao;
    @Autowired
    private UserDao userDao;

    @Reference
    private SnowflakeService snowflakeService;

    @Override
    public long create(CreateArticleDto createArticleDto){
        articleMapper.insertSelective(createArticleDto);
        long articleId = createArticleDto.getId();
        String[] imageUrls = createArticleDto.getImageUrls();
        if(imageUrls != null && imageUrls.length != 0) {
            List<Image> images = new ArrayList<>();
            for (int i = 0; i < imageUrls.length; i++) {
                Image image = new Image();
                Result result = snowflakeService.getId("normal");
                if(result.getStatus() == Status.EXCEPTION) System.out.println("exception");
                image.setId(result.getId());
                image.setArticleId(articleId);
                image.setImageUrl(imageUrls[i]);
                images.add(image);
            }
            imageDao.insertImages(images);
        }
        return articleId;
    }

    @Override
    public long delete(long id){
        return articleMapper.deleteByPrimaryKey(id);
    }

    public void fillLikeCommentUserDetail(List<ArticleDetail> articleDetails){
        for( ArticleDetail articleDetail : articleDetails){
            //填充点赞详情
            List<LikeDetail> likeDetailsByArticleId = likeDao.getLikeDetailsByArticleId(articleDetail.getId());
            //即便查出来的记录数为空，mybatis还是会填充一个null到集合里，此时要去掉null
            if(likeDetailsByArticleId.size() == 1 && likeDetailsByArticleId.get(0) == null){
                likeDetailsByArticleId.remove(0);
            }
            articleDetail.setLikeDetails(likeDetailsByArticleId);
            //填充评论详情
            List<CommentDetail> commentDetailsByArticleId = commentDao.getCommentDetailsByArticleId(articleDetail.getId());
            if(commentDetailsByArticleId.size() == 1 && commentDetailsByArticleId.get(0) == null){
                commentDetailsByArticleId.remove(0);
            }
            articleDetail.setCommentDetails(commentDetailsByArticleId);
            //填充用户详情
            UserDetail userDetail = userDao.getUserDetailsByUserId(articleDetail.getUserId());
            articleDetail.setUserDetail(userDetail);
        }
    }

    @Override
    public List<ArticleDetail> getArticleDetails(){
        List<ArticleDetail> articleDetails = articleDao.getArticleBaseInfo();
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getArticleDetailsByUserId(long userId, int pageNum, int pageSize){
//        PageHelper.startPage(pageNum,pageSize);
        List<ArticleDetail> articleDetails = articleDao.getArticleDetailsByUserId(userId,(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getUserLikedArticleDetails(long userId, int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getUserLikedArticleDetails(userId,(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getUserCommentedArticleDetails(long userId, int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getUserCommentedArticleDetails(userId,(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getUserBookmarkedArticleDetails(long userId, int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getUserBookmarkedArticleDetails(userId,(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public  List<ArticleDetail> getBrowserHistory(long userId, int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getBrowserHistory(userId,(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }
}
