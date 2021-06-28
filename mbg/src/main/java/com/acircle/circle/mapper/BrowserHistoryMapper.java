package com.acircle.circle.mapper;

import com.acircle.circle.model.BrowserHistory;
import com.acircle.circle.model.BrowserHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrowserHistoryMapper {
    long countByExample(BrowserHistoryExample example);

    int deleteByExample(BrowserHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BrowserHistory record);

    int insertSelective(BrowserHistory record);

    List<BrowserHistory> selectByExample(BrowserHistoryExample example);

    BrowserHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BrowserHistory record, @Param("example") BrowserHistoryExample example);

    int updateByExample(@Param("record") BrowserHistory record, @Param("example") BrowserHistoryExample example);

    int updateByPrimaryKeySelective(BrowserHistory record);

    int updateByPrimaryKey(BrowserHistory record);
}