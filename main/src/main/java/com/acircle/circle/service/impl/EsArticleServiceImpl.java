package com.acircle.circle.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.acircle.circle.dto.ArticleDetail;
import com.acircle.circle.dto.EsArticle;
import com.acircle.circle.repository.EsArticleRepository;
import com.acircle.circle.service.ArticleService;
import com.acircle.circle.service.EsArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class EsArticleServiceImpl implements EsArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsArticleServiceImpl.class);
    @Autowired
    private EsArticleRepository esArticleRepository;
    @Autowired
    private ArticleService articleService;

    @Override
    public List<ArticleDetail> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<EsArticle>  esArticles = esArticleRepository.findByText(keyword, pageable);
        List<ArticleDetail> articleDetails = new ArrayList<>();
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        esArticles.getContent().forEach((esArticle) -> {
            System.out.println(esArticle);
            ArticleDetail articleDetail = new ArticleDetail();
            BeanUtil.copyProperties(esArticle,articleDetail);
            articleDetails.add(articleDetail);
        });
        articleService.fillLikeCommentUserDetail(articleDetails);
        return articleDetails;
    }

}
