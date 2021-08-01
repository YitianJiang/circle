package com.acircle.circle.service.impl;

import com.acircle.circle.mapper.BookmarkMapper;
import com.acircle.circle.model.Bookmark;
import com.acircle.circle.model.BookmarkExample;
import com.acircle.circle.service.BookMarkService;
import com.acircle.circle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookMarkServiceImpl implements BookMarkService{
    @Autowired
    private UserService userService;
    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Override
    public long create(Bookmark bookmark){
        bookmark.setUserId(userService.getCurrentUserBaseInfoByJWT().getId());
        bookmarkMapper.insertSelective(bookmark);
        return bookmark.getId();
    }

    @Override
    public long delete(long id){
        BookmarkExample bookmarkExample = new BookmarkExample();
        bookmarkExample.createCriteria().andIdEqualTo(id).andUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId());
        return bookmarkMapper.deleteByExample(bookmarkExample);
    }

}
