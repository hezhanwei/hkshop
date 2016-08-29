package com.bluemobi.dao.store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.store.StoreContent;

/**
 * 【已签约商户表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-09-15 17:24:48
 * 
 */
public interface StoreContentDao extends MyBatisBaseDao {

    /**
     * 查询商铺详情
     * 
     * @auther zhangzheng
     * @date 2015-10-26 上午11:18:41
     * @param parameter
     * @return
     */
    Map<String, Object> selectMapStoreInfo(Map<String, Object> map);
    
    /**
     * 获取用户收藏的店铺列表
     * @param storeIds
     * @return
     */
    List<StoreContent> selectGoodsListByContentids(@Param("storeIds")List<Integer> storeIds);

    /**
     * 更新店铺收藏次数
     * @data 2016年8月12日 下午2:43:29
     * @param storeId
     * @return
     */
    Integer updateStoreCollectionNum(Integer storeId);
    
    /**
     * 移动端查看店铺列表
     * @data 2016年8月12日 下午2:44:30
     * @return
     */
    List<StoreContent> getStoreList(Map<String, Object> map);
    
    /**
     * 移动端查看店铺列表总数
     * @data 2016年8月12日 下午3:05:52
     * @return
     */
    int getStoreListCount();
}
