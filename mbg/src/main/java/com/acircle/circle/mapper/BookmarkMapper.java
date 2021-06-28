package com.acircle.circle.mapper;

import com.acircle.circle.model.Bookmark;
import com.acircle.circle.model.BookmarkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BookmarkMapper {
    long countByExample(BookmarkExample example);

    int deleteByExample(BookmarkExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Bookmark record);

    int insertSelective(Bookmark record);

    List<Bookmark> selectByExample(BookmarkExample example);

    Bookmark selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Bookmark record, @Param("example") BookmarkExample example);

    int updateByExample(@Param("record") Bookmark record, @Param("example") BookmarkExample example);

    int updateByPrimaryKeySelective(Bookmark record);

    int updateByPrimaryKey(Bookmark record);
}