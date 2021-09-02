package com.acircle.circle.search.service.impl;

import com.acircle.circle.search.domain.EsArticle;
import com.acircle.circle.search.repository.EsArticleRepository;
import com.acircle.circle.search.service.EsArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class EsArticleServiceImpl implements EsArticleService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsArticleServiceImpl.class);
    @Autowired
    private EsArticleRepository esArticleRepository;

    @Override
    public Page<EsArticle> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esArticleRepository.findByText(keyword, pageable);
    }

}
