package com.acircle.circle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.acircle.circle.common.service.RedisService;
import com.acircle.circle.dto.ArticleDetail;
import com.acircle.circle.dto.EsArticle;
import com.acircle.circle.repository.EsArticleRepository;
import com.acircle.circle.service.ArticleService;
import com.acircle.circle.service.EsArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class EsArticleServiceImpl implements EsArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsArticleServiceImpl.class);
    @Value("${redis.key.searchHotSpots}")
    private String REDIS_KEY_SEARCH_HOT_SPOTS;
    @Autowired
    private EsArticleRepository esArticleRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private RedisService redisService;

    @Override
    public List<ArticleDetail> search(String keyword, Integer pageNum, Integer pageSize) {
        if (keyword == "") return  null;
        if(!setSearchKeyword(keyword)) LOGGER.warn("fail to set search keyword to cache");
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<EsArticle>  esArticles = esArticleRepository.findByText(keyword, pageable);
        List<ArticleDetail> articleDetails = new ArrayList<>();
        esArticles.getContent().forEach((esArticle) -> {
            ArticleDetail articleDetail = new ArticleDetail();
            BeanUtil.copyProperties(esArticle,articleDetail);
            articleDetails.add(articleDetail);
        });
        articleService.fillLikeCommentUserDetail(articleDetails);
        return articleDetails;
    }

    public Boolean setSearchKeyword(String keyword) {
        Double score = redisService.zScore(REDIS_KEY_SEARCH_HOT_SPOTS,keyword);
        if (score == null) return redisService.zSetAdd(REDIS_KEY_SEARCH_HOT_SPOTS,keyword,1);
        else return redisService.zSetAdd(REDIS_KEY_SEARCH_HOT_SPOTS,keyword,score.longValue() + 1);
    }

    @Override
    public Set<Object> getSearchHotSpots() {
        return redisService.zSetReverseRange(REDIS_KEY_SEARCH_HOT_SPOTS,0,9);
    }
}
