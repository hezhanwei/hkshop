package com.bluemobi.serviceimpl.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.appcore.util.StringUtil;
import com.appcore.util.UUIDService;
import com.bluemobi.dao.goods.GoodsBrandCategoryDao;
import com.bluemobi.dao.goods.GoodsCategoryDao;
import com.bluemobi.po.goods.GoodsBrandCategory;
import com.bluemobi.po.goods.GoodsCategory;
import com.bluemobi.service.goods.GoodsCategoryService;

/**
 * 【商品分类表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-22 15:18:57
 * 
 */
@Service(value = "goodsCategoryService")
public class GoodsCategoryServiceImpl extends MybatisBaseServiceImpl implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryDao goodsCategoryDao;
    @Autowired
    private GoodsBrandCategoryDao goodsBrandCategoryDao;
    
    @Override
    public MyBatisBaseDao getDao() {
        return goodsCategoryDao;
    }

    public int deleteByIds(Map<String, Object> parameter) {
        return goodsCategoryDao.deleteByIds(parameter);
    }

    public List<Map<String,Object>> selectAllCategoryOrderByHid(Map<String, Object> parameter) {
        return goodsCategoryDao.selectAllCategoryOrderByHid(parameter);
    }

    public Map<String,Object> selectCountByParentIds(Map<String, Object> parameter) {
        return goodsCategoryDao.selectCountByParentIds(parameter);
    }

    public void saveGoodsCategory(GoodsCategory goodsCategory) {
        Date date = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        String uuid = UUIDService.getUUID();
        String hid = "";

        // 1、设置创建时间和修改时间
        goodsCategory.setCtime(date);
        goodsCategory.setMtime(date);
        goodsCategory.setStatus((byte) 1);
        goodsCategory.setHid(uuid);

        // 2、保存对象，并通过数据库生成id
        goodsCategoryDao.insert(goodsCategory);
        map.put("hid", uuid);
        List<GoodsCategory> categoryList = goodsCategoryDao.selectObjectList(map);
        if (categoryList != null && !categoryList.isEmpty()) {
            goodsCategory = categoryList.get(0);
        }

        hid = getHid(goodsCategory);
        goodsCategory.setHid(hid);

        // 4、修改当前对象
        goodsCategoryDao.update(goodsCategory);
    }

    public void updateGoodsCategory(GoodsCategory goodsCategory) {
        goodsCategory.setMtime(new Date());
        String hid = getHid(goodsCategory);
        goodsCategory.setHid(hid);
        goodsCategoryDao.update(goodsCategory);
    }

    /**
     * 获取商品分类的hid
     * 
     * @param goodsCategory
     * @param map
     * @return
     */
    private String getHid(GoodsCategory goodsCategory) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (goodsCategory.getParentId() != 0) {
            map.put("categoryId", goodsCategory.getParentId());
            GoodsCategory parentCategory = goodsCategoryDao.selectObject(map);
            return parentCategory.getHid() + ":" + StringUtil.lPad(goodsCategory.getCategoryId() + "", 4, "0");
        } else {
            return "0:" + StringUtil.lPad(goodsCategory.getCategoryId() + "", 4, "0");
        }
    }

    @Override
    public void updateBindBrand(String brandIds, int categoryId) {
        // 删除原来的关联数据
        Map<String, Object> map = new HashMap<String, Object>();
        List<GoodsBrandCategory> list = new ArrayList<GoodsBrandCategory>();
        GoodsBrandCategory goodsBrandCategory = new GoodsBrandCategory();
        List<GoodsBrandCategory> brandCategoryList = goodsBrandCategoryDao.selectObjectList(map);
        goodsBrandCategoryDao.deleteByCategoryList(brandCategoryList);
        // 数据分割
        String[] brands = brandIds.split(",");
        // 循环添加
        goodsBrandCategory.setCategoryId(categoryId);
        goodsBrandCategory.setSortOrder(1);
        for (int i = 0; i < brands.length; i++) {
            goodsBrandCategory.setBrandId(Integer.parseInt(brands[i]));
            list.add(goodsBrandCategory);
        }
        goodsBrandCategoryDao.insertGoodsBrandCategories(list);
    }

}
