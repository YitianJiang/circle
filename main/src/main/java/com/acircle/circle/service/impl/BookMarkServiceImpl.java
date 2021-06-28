package com.acircle.circle.service.impl;

import com.acircle.circle.mapper.BookmarkMapper;
import com.acircle.circle.mapper.FollowMapper;
import com.acircle.circle.model.Bookmark;
import com.acircle.circle.model.Follow;
import com.acircle.circle.service.BookMarkService;
import com.acircle.circle.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMarkServiceImpl implements BookMarkService{
    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Override
    public long create(Bookmark bookmark){
        bookmarkMapper.insertSelective(bookmark);
        return bookmark.getId();
    }

    @Override
    public long delete(long id){
        return bookmarkMapper.deleteByPrimaryKey(id);
    }

}
