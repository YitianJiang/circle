package com.acircle.circle.service.impl;

import com.acircle.circle.dao.*;
import com.acircle.circle.dto.*;
import com.acircle.circle.mapper.ArticleMapper;
import com.acircle.circle.mapper.ImageMapper;
import com.acircle.circle.model.ArticleExample;
import com.acircle.circle.model.Image;
import com.acircle.circle.model.ImageExample;
import com.acircle.circle.model.User;
import com.acircle.circle.service.*;
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
    private UserService userService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CacheService cacheService;

    @Autowired
    private  ArticleMapper articleMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private ImageDao imageDao;

    @Reference
    private SnowflakeService snowflakeService;

    @Override
    public long create(CreateArticleDto createArticleDto){
        createArticleDto.setUserId(userService.getCurrentUserBaseInfoByJWT().getId());
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
        ArticleExample articleExample = new ArticleExample();
        articleExample.createCriteria().andUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId()).andIdEqualTo(id);
        //先删除文章
        int result = articleMapper.deleteByExample(articleExample);
        if(result <= 0){
            return result;
        }else {
            ImageExample imageExample = new ImageExample();
            imageExample.createCriteria().andArticleIdEqualTo(id);
            //再删除与文章相关的图片
            imageMapper.deleteByExample(imageExample);
            return result;
        }
    }

    public void fillLikeCommentUserDetail(List<ArticleDetail> articleDetails){
        for( ArticleDetail articleDetail : articleDetails){
            //填充点赞详情
            articleDetail.setLikeDetails(likeService.getLikeDetailsByArticleId(articleDetail.getId()));
            //填充评论详情
            articleDetail.setCommentDetails(commentService.getCommentDetailsByArticleId(articleDetail.getId()));
            //填充用户详情
            User user = userService.getUserByUserId(articleDetail.getUserId());
            UserDetail userDetail = new UserDetail();
            userDetail.setId(user.getId());
            userDetail.setName(user.getName());
            userDetail.setAvatarUrl(user.getAvatarUrl());
            articleDetail.setUserDetail(userDetail);
        }
    }

    @Override
    public List<ArticleDetail> getHomeRecommendArticles(int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getHomeRecommendArticlesBaseInfo((pageNum - 1) * pageSize,pageSize);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleAbstract> getArticleAbstractByArticleId(long articleId){
        List<ArticleAbstract> articleAbstracts = articleDao.getArticleAbstractByArticleId(articleId);
        return  articleAbstracts;
    }

    @Override
    public List<ArticleDetail> getArticleDetailByArticleId(long articleId){
        List<ArticleDetail> articleDetails = articleDao.getArticleBaseInfoByArticleId(articleId);
        fillLikeCommentUserDetail(articleDetails);
        return  articleDetails;
    }

    public void fillLikeCommentDetail(List<ArticleDetail> articleDetails){
        for( ArticleDetail articleDetail : articleDetails){
            //填充点赞详情
            articleDetail.setLikeDetails(likeService.getLikeDetailsByArticleId(articleDetail.getId()));
            //填充评论详情
            articleDetail.setCommentDetails(commentService.getCommentDetailsByArticleId(articleDetail.getId()));
        }
    }

    @Override
    public List<ArticleDetail> getArticleDetailsByUserId(long userId, int pageNum, int pageSize){
//        PageHelper.startPage(pageNum,pageSize);
        List<ArticleDetail> articleDetails = articleDao.getArticleDetailsByUserId(userId,(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getArticleDetailsByCurrentUserId(int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getArticleDetailsByUserId(userService.getCurrentUserBaseInfoByJWT().getId(),(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getUserLikedArticleDetails(int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getUserLikedArticleDetails(userService.getCurrentUserBaseInfoByJWT().getId(),(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public List<ArticleDetail> getUserBookmarkedArticleDetails(int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getUserBookmarkedArticleDetails(userService.getCurrentUserBaseInfoByJWT().getId(),(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentDetail(articleDetails);
        return  articleDetails;
    }

    @Override
    public  List<ArticleDetail> getBrowserHistory(int pageNum, int pageSize){
        List<ArticleDetail> articleDetails = articleDao.getBrowserHistory(userService.getCurrentUserBaseInfoByJWT().getId(),(pageNum - 1) * pageSize,pageSize);
        fillLikeCommentDetail(articleDetails);
        return  articleDetails;
    }
}
