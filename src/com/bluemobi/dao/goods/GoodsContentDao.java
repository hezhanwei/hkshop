package com.bluemobi.dao.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.page.Page;
import com.bluemobi.po.goods.GoodsContent;
import com.bluemobi.to.goods.GoodsContentAndSkuTO;

/**
 * 【 商品详细内容，包括商品详情、meta 相关字段等】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-02 10:14:35
 * 
 */
public interface GoodsContentDao extends MyBatisBaseDao {

    /**
     * 将商品放入回收站
     * 
     * @auther zhangzheng
     * @date 2015-10-22 下午4:07:46
     * @param paramMap
     * @return
     */
    Integer deleteNotTrueByIds(Map<String, Object> paramMap);

    /**
     * 批量删除
     * 
     * @param paramMap
     *            传入的map对象的值为id的String[]数组
     * @return
     */
    Integer deleteByIds(Map<String, Object> paramMap);

    /**
     * 获取商品列表接口分页查询
     * 
     * @auther zhangzheng
     * @date 2015-10-22 下午5:03:59
     * @param paramMap
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<Map<String, Object>> pageApi(Map<String, Object> paramMap, Integer pageIndex, Integer pageSize);

    /**
     * 根据商品ID和商品的父ID查询商品
     * 
     * @auther zhangzheng
     * @date 2015-10-23 下午5:08:43
     * @param paramMap
     * @return 商品的list数组
     */
    List<GoodsContent> selectObjectListByIdAndParentId(Map<String, Object> paramMap);

    /**
     * 获取新增商品时用于关联的商品列表
     * 
     * @auther zhangzheng
     * @date 2015-10-22 下午5:03:59
     * @param paramMap
     * @param pageIndex
     * @param pageSize
     * @return
     */
    Page<Map<String, Object>> pageGoodsContent(Map<String, Object> paramMap, Integer pageIndex, Integer pageSize);

    /**
     * 
     * @auther zhangzheng
     * @date 2015-10-27 下午1:49:22
     * @param map
     * @return
     */
    List<GoodsContent> pageGoodsContent(Map<String, Object> map);

    /**
     * 
     * @auther zhangzheng
     * @date 2015-10-27 下午1:49:26
     * @param map
     * @return
     */
    Integer pageCountGoodsContent(Map<String, Object> map);
    
    /**
     * 查询商品和商品sku信息
     * 
     * @auther zhangzheng
     * @date 2015-11-19 下午2:51:45
     * @param paramMap
     * @return
     */
    List<GoodsContentAndSkuTO> selectAllContentAndSku(Map<String, Object> paramMap);
    
    /**
     * 获取用户收藏的商品列表
     * @param contentIds
     * @return
     */
    List<GoodsContent> selectGoodsListByContentids(@Param("contentIds")List<Integer> contentIds);
    
    Integer updateCollectionNum(Integer contentId);
    
    /**
     * 按店铺名、商品种类、品牌，商品名称进行搜索 
     * @data 2016年8月16日 下午2:29:50
     * @param map
     * @return
     */
    List<Map<String, Object>> globalSearchGoods(Map<String, Object> map);

    /**
     * 按店铺名、商品种类、品牌，商品名称进行搜索  总数
     * @data 2016年8月16日 下午2:30:38
     * @param map
     * @return
     */
    Integer globalSearchGoodsCount(Map<String, Object> map);
}
