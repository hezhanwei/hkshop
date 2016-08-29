package com.bluemobi.controller.back.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.goods.GoodsCategory;
import com.bluemobi.po.goods.GoodsPropertyCategory;
import com.bluemobi.po.trend.TrendProperty;
import com.bluemobi.po.trend.TrendPropertyGroup;
import com.bluemobi.service.goods.GoodsBrandCategoryService;
import com.bluemobi.service.goods.GoodsCategoryService;
import com.bluemobi.service.goods.GoodsPropertyCategoryService;
import com.bluemobi.service.goods.GoodsPropertyService;
import com.bluemobi.service.trend.TrendPropertyGroupService;
import com.bluemobi.service.trend.TrendPropertyService;
import com.bluemobi.to.ResultTO;

@Controller
@RequestMapping("goodsCategory")
public class GoodsCategoryController extends AbstractWebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCategoryController.class);

    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private GoodsBrandCategoryService goodsBrandCategoryService;
    @Autowired
    private GoodsPropertyCategoryService goodsPropertyCategoryService;
    @Autowired
    private TrendPropertyService trendPropertyService;
    @Autowired
    private TrendPropertyGroupService trendPropertyGroupService;
    @Autowired
    private GoodsPropertyService goodsPropertyService;

    private static final String CATEGORIES = "categories";
    private static final String CATEGORY_ID = "categoryId";
    private static final String PROPERTY_ID = "propertyId";
    private static final String COUNT = "count";
    private static final String CATEGORY = "category";

    /**
     * 初始化商品分类管理列表页
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:22:51
     * @param model
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        LOGGER.info("用户【{}】初始化商品分类管理列表页", new Object[] { this.getUserid() });
        return "goods/category.index";
    }

    /**
     * 分页查询商品分类信息
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:22:45
     * @param key
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageIndex, int pageSize) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (key != null && !"".equals(key)) {
            map.put("key", key);
        }
        Page<Map<String, Object>> categoryPages = goodsCategoryService.page(map, pageIndex, pageSize);
        LOGGER.info("用户【{}】根据关键字【{}】页码【{}】每页最大数【{}】分页查询商品分类信息", new Object[] { this.getUserid(), key, pageIndex, pageSize });
        return categoryPages;
    }

    /**
     * 初始化商品分类新增页面
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:22:35
     * @param model
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        List<Map<String, Object>> categories = goodsCategoryService.selectAllCategoryOrderByHid(null);
        model.addAttribute(CATEGORIES, categories);
        LOGGER.info("用户【{}】初始化商品分类新增页面", new Object[] { this.getUserid() });
        return "goods/category.edit";
    }

    /**
     * 新增商品分类
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:22:30
     * @param goodsCategory
     * @return
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addGoodsCategory(@ModelAttribute GoodsCategory goodsCategory, BindingResult result) {
        try {
            goodsCategoryService.saveGoodsCategory(goodsCategory);
            LOGGER.error("用户【{}】新增商品分类【{}】成功", new Object[] { this.getUserid(), goodsCategory.getCategoryId() });
        } catch (Exception e) {
            LOGGER.error("Exception:", e);
            LOGGER.error("用户【{}】新增商品分类失败。Exception:【{}】", new Object[] { this.getUserid(), e });
            return ResultTO.newFailResultTO("保存失败", null);
        }
        return ResultTO.newSuccessResultTO("保存成功", null);
    }

    /**
     * 初始化商品分类修改页面
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:23:02
     * @param model
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, int categoryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        // 加载公共数据
        initIndex(model);
        List<Map<String, Object>> categories = goodsCategoryService.selectAllCategoryOrderByHid(null);
        model.addAttribute(CATEGORIES, categories);
        map.put(CATEGORY_ID, categoryId);
        Map<String, Object> category = goodsCategoryService.selectMap(map);
        model.addAttribute(CATEGORY, category);
        LOGGER.info("用户【{}】初始化商品分类修改页面", new Object[] { this.getUserid() });
        return "goods/category.edit";
    }

    /**
     * 修改商品分类
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:23:09
     * @param goodsCategory
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editGoodsCategory(@ModelAttribute GoodsCategory goodsCategory, BindingResult result) {
        try {
            goodsCategoryService.updateGoodsCategory(goodsCategory);
            LOGGER.error("用户【{}】修改商品分类【{}】成功", new Object[] { this.getUserid(), goodsCategory.getCategoryId() });
        } catch (Exception e) {
            LOGGER.error("Exception:", e);
            LOGGER.error("用户【{}】修改商品分类【{}】失败。Exception:【{}】", new Object[] { this.getUserid(), goodsCategory.getCategoryId(), e });
            return ResultTO.newFailResultTO("修改失败", null);
        }
        return ResultTO.newSuccessResultTO("修改成功", null);
    }

    /**
     * 批量删除商品分类
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:23:28
     * @param categoryIds
     *            要批量删除的id字符串，id间以英文逗号分割
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteGoodsCategory(String categoryIds) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String[] ids = categoryIds.split(",");
            map.put("categoryIds", ids);
            Map<String, Object> countMap = goodsCategoryService.selectCountByParentIds(map);
            long count = 0;
            if (countMap != null && !countMap.isEmpty()) {
                count = (Long) countMap.get(COUNT);
            }
            if (count < 1) {
                goodsCategoryService.deleteByIds(map);
            } else {
                LOGGER.error("用户【{}】批量删除商品分类【{}】失败，要删除的商品分类存在子分类。", new Object[] { this.getUserid(), categoryIds });
                return ResultTO.newFailResultTO("要删除的商品分类存在子分类，删除失败！", null);
            }
            LOGGER.info("用户【{}】批量删除商品分类【{}】成功", new Object[] { this.getUserid(), categoryIds });
        } catch (Exception e) {
            LOGGER.error("用户【{}】批量删除商品分类【{}】失败。Exception:【{}】", new Object[] { this.getUserid(), categoryIds, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }

    /**
     * 绑定品牌
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:24:05
     * @param model
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "bindBrand", method = RequestMethod.GET)
    public String bindBrand(Model model, String categoryId) {
        Map<String, Object> map = new HashMap<String, Object>();
        initIndex(model);
        map.put(CATEGORY_ID, categoryId);
        GoodsCategory category = goodsCategoryService.selectObject(map);
        List<Map<String, Object>> brandCategoryList = goodsBrandCategoryService.selectBrandByCategoryId(map);
        model.addAttribute(CATEGORY, category);
        model.addAttribute("brandsBinded", brandCategoryList);
        LOGGER.info("用户【{}】跳转分类【{}】绑定品牌页面", new Object[] { this.getUserid(), categoryId });
        return "goods/category.bindbrand";
    }

    /**
     * 修改绑定品牌
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:24:21
     * @param brandIds
     *            品牌id
     * @param categoryId
     *            分类id
     * @return
     */
    @RequestMapping(value = "bindBrand", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO updatebindBrand(String brandIds, int categoryId) {
        try {
            goodsBrandCategoryService.updateBindBrand(brandIds, categoryId);
            LOGGER.info("用户【{}】修改分类【{}】的绑定品牌【{}】成功", new Object[] { this.getUserid(), categoryId, brandIds });
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改分类【{}】的绑定品牌【{}】失败。Exception:【{}】", new Object[] { this.getUserid(), categoryId, brandIds, e });
            return ResultTO.newFailResultTO("修改失败", null);
        }
        return ResultTO.newSuccessResultTO("修改成功", null);
    }

    /**
     * 绑定属性
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:24:51
     * @param model
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "bindProperty", method = RequestMethod.GET)
    public String bindProperty(Model model, String categoryId) {
        initIndex(model);
        Map<String, Object> categoryMap = new HashMap<String, Object>();
        Map<String, Object> propertyCategoryMap = new HashMap<String, Object>();
        Map<String, Object> trendMap = new HashMap<String, Object>();
        Map<String, Object> trendGroupMap = new HashMap<String, Object>();

        // 获得详情对象
        categoryMap.put(CATEGORY_ID, categoryId);
        GoodsCategory category = goodsCategoryService.selectObject(categoryMap);

        // 获得相关属性关联
        List<Map<String, Object>> properties = new ArrayList<Map<String, Object>>();
        propertyCategoryMap.put(CATEGORY_ID, categoryId);
        List<GoodsPropertyCategory> pToC = goodsPropertyCategoryService.selectObjectList(propertyCategoryMap);
        if (pToC == null || pToC.isEmpty()) {
            GoodsCategory goodsCategory = new GoodsCategory();
            goodsCategory.setCategoryId(Integer.valueOf(categoryId));
            model.addAttribute(CATEGORY, goodsCategory);
            LOGGER.info("用户【{}】跳转分类【{}】绑定属性页面", new Object[] { this.getUserid(), categoryId });
            return "goods/category.bindproperty";
        }
        for (GoodsPropertyCategory g : pToC) {
            Map<String, Object> map = new HashMap<String, Object>();
            String idStr = "";
            String propertyName = "";
            if (g.getPropertyId() != 0) {
                idStr = "li_property_" + g.getPropertyId();
                trendMap.put(PROPERTY_ID, g.getPropertyId());
                TrendProperty trend = trendPropertyService.selectObject(trendMap);
                if (trend != null) {
                    propertyName = trend.getLabelName();
                }
            } else {
                idStr = "li_property_group_" + g.getPropertyGroupId();
                trendGroupMap.put("propertyGroupId", g.getPropertyGroupId());
                TrendPropertyGroup trendGroup = trendPropertyGroupService.selectObject(trendGroupMap);
                if (trendGroup != null) {
                    propertyName = trendGroup.getPropertyGroupName();
                }
            }
            map.put("dataType", g.getPropertyId() != 0 ? "p" : "g");
            map.put("idStr", idStr);
            map.put(PROPERTY_ID, g.getPropertyId() != 0 ? g.getPropertyId() : g.getPropertyGroupId());
            map.put("propertyName", propertyName);
            properties.add(map);
        }
        model.addAttribute(CATEGORY, category);
        model.addAttribute("properties", properties);
        LOGGER.info("用户【{}】跳转分类【{}】绑定属性页面", new Object[] { this.getUserid(), categoryId });
        return "goods/category.bindproperty";
    }

    /**
     * 修改绑定属性
     * 
     * @auther zhangzheng
     * @date 2015-12-3 上午10:24:59
     * @param propertyIds
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "bindProperty", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO updateBindProperty(String propertyIds, Integer categoryId) {
        try {
            goodsPropertyService.updateBindProperty(propertyIds, categoryId);
            LOGGER.info("用户【{}】修改分类【{}】的绑定属性【{}】成功", new Object[] { this.getUserid(), categoryId, propertyIds });
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改分类【{}】的绑定属性【{}】失败。Exception:【{}】", new Object[] { this.getUserid(), categoryId, propertyIds, e });
            return ResultTO.newFailResultTO("修改失败", null);
        }
        return ResultTO.newSuccessResultTO("修改失败", null);
    }

}
