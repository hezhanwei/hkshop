package com.bluemobi.controller.api.goods;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.controller.api.advert.AdvertController;
import com.bluemobi.po.goods.GoodsBrand;
import com.bluemobi.po.goods.GoodsCategory;
import com.bluemobi.po.goods.GoodsContentSku;
import com.bluemobi.service.goods.GoodsBrandService;
import com.bluemobi.service.goods.GoodsCategoryService;
import com.bluemobi.service.goods.GoodsContentService;
import com.bluemobi.service.goods.GoodsContentSkuService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.goods.GoodsContentAndSkuTO;

/**
 * 商品接口
 * 
 * @author zhangzheng
 * @date 2015-10-22
 * 
 */
@Controller
@RequestMapping("api/goods")
public class GoodsController extends AbstractAPIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertController.class);

    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private GoodsBrandService goodsBrandService;
    @Autowired
    private GoodsContentService goodsContentService;
    @Autowired
    private GoodsContentSkuService goodsContentSkuService;

    /**
     * 获取商品分类列表
     * 
     * @auther zhangzheng
     * @date 2015-10-22 上午10:34:13
     * @param parentId
     *            商品分类的父ID，如果为空查询所有一级分类
     * @return
     */
    @RequestMapping(value = "getCategory", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getCategory(Integer parentId) {
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> secondParam = new HashMap<String, Object>();
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        Integer userid = this.getUserid();
        if (parentId != null && parentId > 0) {
            param.put("parentId", parentId);
        } else {
            param.put("parentId", 0);
        }
        List<GoodsCategory> categories = goodsCategoryService.selectObjectList(param); // 根据父ID查询所有分类

        Map<String, Object> categoryMap = null;
        for (GoodsCategory category : categories) {
            categoryMap = new HashMap<String, Object>();
            secondParam.put("categoryId", category.getCategoryId());
            List<GoodsCategory> secondCategory = goodsCategoryService.selectObjectList(secondParam); // 根据查询结果的ID查询该分类下是否有子分类
            if (secondCategory != null && !secondCategory.isEmpty()) {
                Map<String, Object> secondMap = new HashMap<String, Object>();
                List<Map<String, Object>> secondList = new ArrayList<Map<String, Object>>();
                for (GoodsCategory goodsCategory : secondCategory) {
                    secondMap.put("categoryId", goodsCategory.getCategoryId());
                    secondMap.put("parentId", goodsCategory.getParentId());
                    secondMap.put("categoryName", goodsCategory.getCategoryName());
                    secondList.add(secondMap);
                }
                categoryMap.put("second", secondList);
            }
            categoryMap.put("categoryId", category.getCategoryId());
            categoryMap.put("parentId", category.getParentId());
            categoryMap.put("categoryName", category.getCategoryName());
            resultList.add(categoryMap);
        }

        LOGGER.info("用户【{}】查询商品分类列表", new Object[] { userid });
        return ResultTO.newSuccessResultTO("查询商品分类列表成功", resultList);
    }

    /**
     * 获取品牌列表
     * 
     * @auther zhangzheng
     * @date 2015-10-22 上午10:45:17
     * @param categoryId
     *            商品分类ID
     * @return
     */
    @RequestMapping(value = "getBrand", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getBrand(Integer categoryId) {
        Map<String, Integer> param = new HashMap<String, Integer>();
        Map<String, Object> brandMap = null;
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<GoodsBrand> brands = null;
        Integer userid = this.getUserid();
        String msg = "";
        boolean flag = true;
        if (categoryId != null && categoryId > 0) {
            param.put("categoryId", categoryId);
            brands = goodsBrandService.selectObjectList(param);
            if (brands != null && !brands.isEmpty()) {
                // 封装数据
                for (GoodsBrand goodsBrand : brands) {
                    brandMap = new HashMap<String, Object>();
                    brandMap.put("brandId", goodsBrand.getBrandId());
                    brandMap.put("categoryId", categoryId);
                    brandMap.put("sortOrder", goodsBrand.getSortOrder());
                    brandMap.put("brandName", goodsBrand.getBrandName());
                    resultList.add(brandMap);
                }
                msg = "查询品牌成功";
            } else {
                brandMap = new HashMap<String, Object>();
                brandMap.put("categoryId", categoryId);
                resultList.add(brandMap);
                msg = "获取商品分类出错。错误信息：查无此分类";
            }
        } else {
            msg = "参数 categoryId 未提供";
            flag = false;
        }
        LOGGER.info("用户【{}】查询品牌", new Object[] { userid });
        if (flag) {
            return ResultTO.newSuccessResultTO(msg, resultList);
        } else {
            return ResultTO.newFailResultTO(msg, null);
        }
    }

    /**
     * 获取商品列表
     * 
     * @auther zhangzheng
     * @date 2015-10-22 上午10:52:04
     * @param category
     *            分类ID 用于搜索和分类选择
     * @param key
     *            模糊搜索关键词
     * @param property
     *            属性名ID:属性值ID多个逗号分隔
     * @param brandId
     *            品牌ID
     * @param p
     *            页数
     * @param psize
     *            每页显示数目
     * @param order
     *            排列顺序 1:时间从新到旧,2:时间从旧到新,3:人气从高到低,4:价格从低到高,5:价格从高到低
     * @return
     */
    @RequestMapping(value = "getGoodsList", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getGoodsList(Integer category, String key, String property, Integer brandId, Integer p, Integer psize, Integer order) {
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer userid = this.getUserid();

        if (category != null && category > 0) {
            param.put("categoryId", category);
        }
        if (key != null && !"".equals(key)) {
            param.put("key", key);
        }

        List<Integer> propertyId = null;
        List<Integer> propertyValueId = null;
        if (property != null && !"".equals(property)) {
            propertyId = new ArrayList<Integer>();
            propertyValueId = new ArrayList<Integer>();
            String[] propertyArray = property.split(",");
            for (String str : propertyArray) {
                String[] strs = str.split(":");
                if (strs != null && strs.length > 1) {
                    propertyId.add(Integer.valueOf(strs[0]));
                    propertyValueId.add(Integer.valueOf(strs[1]));
                }
            }
        }

        if (propertyId != null && !propertyId.isEmpty()) {
            param.put("propertyIds", propertyId);
        }
        if (propertyValueId != null && !propertyValueId.isEmpty()) {
            param.put("propertyValueIds", propertyValueId);
        }

        if (brandId != null && brandId > 0) {
            param.put("brandId", brandId);
        }
        if (order != null) {
            if (order == 1) {
                param.put("order", "mtime DESC");
            } else if (order == 2) {
                param.put("order", "mtime ASC");
            } else if (order == 3) {
                param.put("order", "viewed DESC");
            } else if (order == 4) {
                param.put("order", "price ASC");
            } else if (order == 5) {
                param.put("order", "price DESC");
            }
        }

        if (p == null || p == 0) {
            p = 1;
        }
        if (psize == null || psize == 0) {
            psize = 15;
        }

        Page<Map<String, Object>> page = goodsContentService.page(param, p, psize);

        resultMap.put("totalnum", page.getCount());
        resultMap.put("currentpage", p);
        resultMap.put("totalpage", page.getCount() / psize);
        resultMap.put("properties", property);
        resultMap.put("brands", brandId);
        resultMap.put("info", page.getData());

        LOGGER.info("用户【{}】查询商品", new Object[] { userid });
        return ResultTO.newSuccessResultTO("查询商品成功", resultMap);
    }

    /**
     * 获得商品详情
     * 
     * @auther zhangzheng
     * @date 2015-10-22 上午11:34:07
     * @param contentId
     *            商品 ID
     * @param userid
     *            用户id （用于判断用户是否点赞）
     * @param p
     *            页数（商品评论分页使用）
     * @param psize
     *            每页显示条数（商品评论分页使用）
     * @return
     */
    @RequestMapping(value = "getGoodsDetail", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getGoodsDetail(Integer contentId, Integer userid, Integer p, Integer psize) {
        Map<String, Object> param = new HashMap<String, Object>();
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Integer userId = this.getUserid();
        try {
            param.put("contentId", contentId);
            GoodsContentSku goodsContentSku = goodsContentSkuService.selectObject(param);
            List<GoodsContentAndSkuTO> goodsContents = goodsContentService.selectAllContentAndSku(param);
            if (goodsContentSku != null) {
                BeanUtils.copyProperties(resultMap, goodsContentSku);
            }
            resultMap.put("bulk", getBulk());
            resultMap.put("grab", getGrab());
            resultMap.put("promotion", getPromotion());
            if (goodsContents != null && !goodsContents.isEmpty()) {
                resultMap.put("goodsContents", goodsContents);
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("IllegalAccessException:", e);
        } catch (InvocationTargetException e) {
            LOGGER.error("InvocationTargetException:", e);
        }
        LOGGER.info("用户【{}】查询商品详情", new Object[] { userId });
        return ResultTO.newSuccessResultTO("查询商品详情成功", resultMap);
    }

    /**
     * 获取团购信息
     * 
     * @auther zhangzheng
     * @date 2015-10-26 上午10:01:32
     * @return
     */
    public String getBulk() {
        return "";
    }

    /**
     * 获取抢购信息
     * 
     * @auther zhangzheng
     * @date 2015-10-26 上午10:01:56
     * @return
     */
    public String getGrab() {
        return "";
    }

    /**
     * 获取活动信息
     * 
     * @auther zhangzheng
     * @date 2015-10-26 上午10:02:09
     * @return
     */
    public String getPromotion() {
        return "";
    }

    
    /**
     * 商品全局搜索
     * @data 2016年8月16日 下午3:08:30
     * @param category
     * @param keyword 关键字
     * @param property
     * @param brandId
     * @param pageIndex 当前页 默认： 1
     * @param pageSize 页大小 默认 ：15
     * @return
     */
    @RequestMapping(value="globalSearchGoods", method=RequestMethod.POST)
    @ResponseBody
    public ResultTO globalSearchGoods(Integer category, String keyword, String property, Integer brandId,
    		int pageIndex,
    		int pageSize){
    	Map<String, Object> param = new HashMap<>();
    	
    	 if (category != null && category > 0) {
             param.put("categoryId", category);
         }
         if (StringUtils.isNotBlank(keyword)) {
             param.put("keyword", keyword);
         }
    	Page globalSearchGoods = goodsContentService.globalSearchGoods(param, pageIndex, pageSize);
    	if (globalSearchGoods!=null && !globalSearchGoods.getData().isEmpty()) {
    		return ResultTO.newSuccessResultTO("查询商品成功", globalSearchGoods);
		}
    	return ResultTO.newFailResultTO("暂无搜索结果", null);
    }
    
}