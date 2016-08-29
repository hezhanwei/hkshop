package com.bluemobi.service.goods;

import java.util.List;
import java.util.Map;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.goods.GoodsCategory;

/**
 * 【商品分类表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-22 15:18:57
 * 
 */
public interface GoodsCategoryService extends MybatisBaseService {

    /**
     * 批量删除商品分类
     * 
     * @param paramMap
     *            传入的map对象的值为id的String[]数组
     * @return
     */
    int deleteByIds(Map<String, Object> paramMap);

    /**
     * 查询所有分类
     * 
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectAllCategoryOrderByHid(Map<String, Object> paramMap);

    /**
     * 查询所选id是否含有子类
     * 
     * @param paramMap
     *            传入的map对象的值为id的String[]数组
     * @return
     */
    Map<String, Object> selectCountByParentIds(Map<String, Object> paramMap);

    /**
     * 新增商品分类
     * 
     * @param goodsCategory
     */
    void saveGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 修改商品分类
     * 
     * @param goodsCategory
     */
    void updateGoodsCategory(GoodsCategory goodsCategory);

    /**
     * 修改绑定品牌
     * 
     * @param brandIds
     *            品牌id
     * @param categoryId
     *            分类id
     */
    void updateBindBrand(String brandIds, int categoryId);

}
