package com.acircle.circle.service.impl;

import com.acircle.circle.common.service.RedisService;
import com.acircle.circle.dao.FollowDao;
import com.acircle.circle.dto.FollowDetail;
import com.acircle.circle.mapper.FollowMapper;
import com.acircle.circle.model.Follow;
import com.acircle.circle.model.FollowExample;
import com.acircle.circle.service.FollowService;
import com.acircle.circle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService{
    @Autowired
    private UserService userService;
    @Autowired
    private  FollowMapper followMapper;
    @Autowired
    private FollowDao followDao;
    @Autowired
    private RedisService redisService;

    @Override
    public long create(Follow follow){
        follow.setFromUserId(userService.getCurrentUserBaseInfoByJWT().getId());
        followMapper.insertSelective(follow);
        return follow.getId();
    }

    @Override
    public long delete(long id){
        FollowExample followExample = new FollowExample();
        followExample.createCriteria().andFromUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId()).andIdEqualTo(id);
        return followMapper.deleteByExample(followExample);
    }

    @Override
    public List<FollowDetail> getPeopleIFollow(int pageNum, int pageSize){
        List<FollowDetail> followDetails = followDao.getPeopleIFollow(userService.getCurrentUserBaseInfoByJWT().getId(),pageNum * pageSize,pageSize);
        for(int i = 0; i < followDetails.size(); i++){
            followDetails.get(i).getToUser().setFansNum(getFansNum(followDetails.get(i).getToUser().getId()));
        }
        return followDetails;
    }

    @Override
    public long getFansNum(long userId){
        Boolean hasKey = redisService.hasKey("fansNum:" + userId);
        if(hasKey){
            return Long.parseLong((String)redisService.get("fansNum:" + userId));
        }else {
            Long fansNum = followDao.getFansNum(userId);
            redisService.set("fansNum:" + userId,fansNum.toString());
            return fansNum;
        }
    }

    @Override
    public List<FollowDetail> getMyFans(int pageNum, int pageSize) {
        return  followDao.getMyFans(userService.getCurrentUserBaseInfoByJWT().getId(),pageNum * pageSize,pageSize);
    }

}
