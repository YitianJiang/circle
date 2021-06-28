package com.acircle.circle.service.impl;

import com.acircle.circle.dao.FollowDao;
import com.acircle.circle.dto.FollowDetail;
import com.acircle.circle.mapper.FollowMapper;
import com.acircle.circle.mapper.UserMapper;
import com.acircle.circle.model.Follow;
import com.acircle.circle.model.FollowExample;
import com.acircle.circle.model.User;
import com.acircle.circle.service.FollowService;
import com.acircle.circle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    private  FollowMapper followMapper;
    @Autowired
    private FollowDao followDao;

    @Override
    public long create(Follow follow){
        followMapper.insertSelective(follow);
        return follow.getId();
    }

    @Override
    public long delete(long id){
        return followMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<FollowDetail> getPeopleIFollow(long userId){
        return followDao.getPeopleIFollow(userId);
    }

    @Override
    public List<FollowDetail> getMyFans(long userId){
        return  followDao.getMyFans(userId);
    }

}
