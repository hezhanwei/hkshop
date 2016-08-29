package com.bluemobi.service.goods;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.appcore.page.Page;
import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.goods.GoodsContent;
import com.bluemobi.to.goods.GoodsContentAndSkuTO;
import com.bluemobi.to.goods.GoodsContentTO;

/**
 * 【 商品详细内容，包括商品详情、meta 相关字段等】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-02 10:14:35
 * 
 */
public interface GoodsContentService extends MybatisBaseService {

    /**
     * 将商品放入回收站
     * 
     * @auther zhangzheng
     * @date 2015-10-22 下午4:07:46
     * @param paramMap
     * @return
     */
    int deleteNotTrueByIds(Map<String, Object> paramMap);

    /**
     * 批量删除
     * 
     * @param paramMap
     *            传入的map对象的值为id的String[]数组
     * @return
     */
    int deleteByIds(Map<String, Object> paramMap);

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
     * 查询商品和商品sku信息
     * 
     * @auther zhangzheng
     * @date 2015-11-19 下午2:51:45
     * @param paramMap
     * @return
     */
    List<GoodsContentAndSkuTO> selectAllContentAndSku(Map<String, Object> paramMap);

    /**
     * 新增商品信息
     * 
     * @auther zhangzheng
     * @date 2015-11-20 上午11:09:17
     * @param goodsContentTO
     * @return
     */
    Long insert(GoodsContentTO goodsContentTO, Date date, Integer userid) throws IllegalAccessException, InvocationTargetException;

    /**
     * 修改商品信息
     * 
     * @auther zhangzheng
     * @date 2015-11-20 上午11:09:50
     * @param goodsContentTO
     * @return
     */
    int update(GoodsContentTO goodsContentTO, Date date, Integer userid) throws IllegalAccessException, InvocationTargetException;

    /**
     * 获取skuid,可以根据实际业务规则修改生成方式以符合业务需求
     * 
     * @auther zhangzheng
     * @date 2015-11-3 下午4:46:11
     * @return
     */
    String getSku();
    
    /**
     * 获取用户收藏的商品列表
     * @param contentIds
     * @return
     */
    List<GoodsContent> selectGoodsListByContentids(List<Integer> contentIds);

    Integer updateCollectionNum(Integer contentId);
    
    /**
     * 按店铺名、商品种类、品牌，商品名称进行搜索 
     * @data 2016年8月16日 下午2:29:50
     * @param map
     * @return
     */
    Page globalSearchGoods(Map<String, Object> map,int pageIndex,int pageSize);
}
