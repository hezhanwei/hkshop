package com.bluemobi.controller.api.store;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.appcore.page.Page;
import com.appcore.util.TimeUtil;
import com.bluemobi.constant.StoreConstant;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.store.StoreContent;
import com.bluemobi.service.coupon.CouponCategoryService;
import com.bluemobi.service.coupon.CouponRelationService;
import com.bluemobi.service.goods.GoodsCategoryService;
import com.bluemobi.service.goods.GoodsContentService;
import com.bluemobi.service.store.StoreContentService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.store.StoreAndUserTO;

/**
 * 
 * <p>Title:StoreContentController </p>
 * <p>Description: 店铺管理</p>
 * <p>Company: </p> 
 * @author hezhanwei
 * @date 2016年8月12日 上午11:15:45
 */
@Controller
@RequestMapping("api/store")
public class StoreController extends AbstractWebController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreContentService storeContentService;
    @Autowired
    private CouponCategoryService couponCategoryService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private CouponRelationService couponRelationService;
    @Autowired
    private GoodsContentService goodsContentService;
    
    /**
     * 移动端获取店铺列表
     * @data 2016年8月12日 下午2:47:06
     * @return
     */
    @RequestMapping("/getStoreList")
    @ResponseBody
    public Page<Map<String, Object>> getStoreList(int pageSize, int pageIndex){
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("pageIndex", pageIndex);
    	map.put("pageSize", pageSize);
    	Page<Map<String, Object>> pages = storeContentService.getStoreList(map,pageIndex,pageSize);
    	return pages;
    }
    
    /**
     * 店铺简介
     * @data 2016年8月12日 下午3:30:19
     */
    @RequestMapping("/getStoreIntroduction")
    @ResponseBody
    public Map<String, Object> getStoreIntroduction(long storeId){
    	
    	  Map<String, Object> map = new HashMap<String, Object>();
          map.put("storeId", storeId);
          Map<String, Object> store = storeContentService.selectMapStoreInfo(map);
          if (store!=null && store.size()>0) {
			return store;
		}
          return null;
    }
    
    /**
     * 店铺详情
     * @data 2016年8月12日 下午4:19:00
     */
    @RequestMapping("/getStoreDetail")
    @ResponseBody
    public String getStoreDetail(Model model, long storeId,int pageSize, int pageIndex){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	//1. 店铺展示
        map.put("storeId", storeId);
        Map<String, Object> store = storeContentService.selectMapStoreInfo(map);
        model.addAttribute("store", store);
    	
    	//2. 优惠券(？与店铺关联？)
        map = new HashMap<String, Object>();
        Page<Map<String, Object>> couponCategorys = couponCategoryService.page(map,pageIndex, pageSize);
        model.addAttribute("couponCategorys", couponCategorys);
        
    	//3. 商品分类
        Page<Map<String, Object>> goodsCategorys = goodsCategoryService.page(map, pageIndex, pageSize);
        model.addAttribute("goodsCategorys", goodsCategorys);
    	
    	//4. 商品展示
    	Page<Map<String, Object>> storeContents = storeContentService.getStoreList(map,pageIndex,pageSize);
    	model.addAttribute("storeContents", storeContents);
    	
    	return "";
    }
    
    /**
     * 店铺商品分类
     * @data 2016年8月12日 下午4:53:42
     */
    @RequestMapping("/getStoreGoodsCategory")
    @ResponseBody
    public String getStoreGoodsCategory(Model model, long storeId,int pageSize, int pageIndex){
    	Map<String, Object> map = new HashMap<String, Object>();
    	//3. 商品分类
    	map.put("storeId", storeId);
        Page<Map<String, Object>> goodsCategorys = goodsCategoryService.page(map, pageIndex, pageSize);
        model.addAttribute("categorys", goodsCategorys);
		return "";
    }
    
}
