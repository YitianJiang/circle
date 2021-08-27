package com.acircle.circle.service.impl;

import com.acircle.circle.dao.LikeDao;
import com.acircle.circle.dto.LikeDetail;
import com.acircle.circle.mapper.LikeMapper;
import com.acircle.circle.model.Like;
import com.acircle.circle.model.LikeExample;
import com.acircle.circle.service.LikeService;
import com.acircle.circle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    private UserService userService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private LikeDao likeDao;

    @Override
    public long create(Like like){
        like.setUserId(userService.getCurrentUserBaseInfoByJWT().getId());
        likeMapper.insertSelective(like);
        return like.getId();
    }

    @Override
    public List<LikeDetail> getLikeDetailsByArticleId(long articleId, int pageNum, int pageSize){
        List<LikeDetail> likeDetailsByArticleId = likeDao.getLikeDetailsByArticleId(articleId,(pageNum - 1) * pageSize,pageSize);
        //即便查出来的记录数为空，mybatis还是会填充一个null到集合里，此时要去掉null
        if(likeDetailsByArticleId.size() == 1 && likeDetailsByArticleId.get(0) == null){
            likeDetailsByArticleId.remove(0);
        }
        return likeDetailsByArticleId;
    }

    @Override
    public long delete(long id){
        LikeExample likeExample = new LikeExample();
        likeExample.createCriteria().andUserIdEqualTo(userService.getCurrentUserBaseInfoByJWT().getId()).andIdEqualTo(id);
        return likeMapper.deleteByExample(likeExample);
    }
}
