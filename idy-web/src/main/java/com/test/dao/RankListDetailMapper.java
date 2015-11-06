package com.test.dao;

import java.util.List;

import com.test.vo.RankListDetail;

/**
 * Dao接口：排行榜榜单管理
 * 
 */
public interface RankListDetailMapper {

    /**
     * 分页获取排行榜榜单详细信息
     * 
     * @param amap 查询条件
     * @return 排行榜榜单详细信息
     */
    
    List<RankListDetail> getRankList(RankListDetail rank);

    /**
     * 获取总记录数
     * 
     * @param amap 查询条件
     * @return 总记录数
     */
    int getRankListCount(RankListDetail rank);
    
    /**
     * 创建新书榜
     */
    int createNewBookRankList();

}
