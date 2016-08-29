package com.bluemobi.dao.groupon;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.groupon.GrouponGrab;

/**
 * 【抢购表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-20 16:51:18
 * 
 */
public interface GrouponGrabDao extends MyBatisBaseDao {

    List<GrouponGrab> selectForCheckTime(Map<String, Object> parameter);
    
    GrouponGrab selectBySku(Map<String, Object> parameter);
    
    /**
     * 获取抢购列表
     * @data 2016年8月26日 下午1:23:03
     * @param grouponGrab
     * @return
     */
    List<Map<String, Object>> getSnapUpGoods(GrouponGrab grouponGrab);
}
