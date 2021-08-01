package com.acircle.circle.dao;

import com.acircle.circle.dto.FollowDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FollowDao {
    List<FollowDetail> getPeopleIFollow(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
    long getFansNum(long userId);
    List<FollowDetail> getMyFans(@Param("userId") long userId, @Param("offSet") int offSet, @Param("limit") int limit);
}

