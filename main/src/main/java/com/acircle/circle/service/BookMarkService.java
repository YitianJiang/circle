package com.acircle.circle.service;

import com.acircle.circle.model.Bookmark;
import org.springframework.transaction.annotation.Transactional;

public interface BookMarkService {
    @Transactional
    long create(Bookmark bookmark);

    long delete(long id);
}
