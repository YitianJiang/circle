package com.acircle.circle.service.impl;

import com.acircle.circle.mapper.LikeMapper;
import com.acircle.circle.model.Like;
import com.acircle.circle.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private LikeMapper likeMapper;

    @Override
    public long create(Like like){
        likeMapper.insertSelective(like);
        return like.getId();
    }

    @Override
    public long delete(long id){
        return likeMapper.deleteByPrimaryKey(id);
    }
}
