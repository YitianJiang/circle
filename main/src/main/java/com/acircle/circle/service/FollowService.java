package com.acircle.circle.service;

import com.acircle.circle.dto.FollowDetail;
import com.acircle.circle.model.Follow;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FollowService {
    @Transactional
    long create(Follow follow);

    long delete(long id);

    List<FollowDetail> getPeopleIFollow(int pageNum, int pageSize);

    long getFansNum(long userId);

    List<FollowDetail> getMyFans(int pageNum, int pageSize);

}
