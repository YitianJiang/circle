package com.acircle.circle.dao;

import com.acircle.circle.dto.FollowDetail;

import java.util.List;

public interface FollowDao {
    List<FollowDetail> getPeopleIFollow(long userId);
    long getFansNum(long userId);
    List<FollowDetail> getMyFans(long userId);
}
