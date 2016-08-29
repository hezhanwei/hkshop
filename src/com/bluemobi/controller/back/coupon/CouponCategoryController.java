package com.bluemobi.controller.back.coupon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.coupon.CouponCategory;
import com.bluemobi.po.coupon.CouponRelation;
import com.bluemobi.service.coupon.CouponCategoryService;
import com.bluemobi.service.coupon.CouponRelationService;
import com.bluemobi.service.goods.GoodsCategoryService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.coupon.CouponCategoryQueryTO;
import com.bluemobi.to.coupon.CouponCategoryTO;
import com.bluemobi.util.BeanUtils;

/**
 * 优惠券管理模块 控制器
 * 
 * @ClassName CouponCategoryController
 * @author liuyt
 * @date 2015-10-27 下午3:44:55
 * @version
 */
@Controller
@RequestMapping("couponCategory")
public class CouponCategoryController extends AbstractWebController {

    @Autowired
    private CouponCategoryService couponCategoryService;
    @Autowired
    private GoodsCategoryService goodsCategoryService;
    @Autowired
    private CouponRelationService couponRelationService;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor editor = new CustomDateEditor(df, true);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 初始化列表页
     * 
     * @author liuyt
     * @date 2015-10-21 上午10:28:40
     * @param model
     * @param request
     * @return string
     * @version 1.0
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "coupon/category.index";
    }

    /**
     * 分页获取优惠券列表
     * 
     * @author liuyt
     * @date 2015-10-21 上午10:29:16
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @version
     * @throws Exception
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPage(CouponCategoryQueryTO query,
            int pageSize, int pageIndex) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        map = BeanUtils.beanToMap(query);
        Page<Map<String, Object>> pages = couponCategoryService.page(map,
                pageIndex, pageSize);
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("data", pages.getData());
        mapResult.put("count", pages.getCount());
        return mapResult;
    }

    /**
     * 查看优惠券详情
     * 
     * @author liuyt
     * @date 2015-10-21 上午11:09:38
     * @param model
     * @param bulkId
     * @return
     * @version
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String getDetail(Model model, String couponId,
            HttpServletRequest request) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("couponId", couponId);
        CouponCategoryTO couponTO = new CouponCategoryTO();
        couponTO.setCoupon((CouponCategory) couponCategoryService
                .selectObject(map));
        couponTO.setRelation((CouponRelation) couponRelationService
                .selectObject(map));
        model.addAttribute("couponTO", couponTO);
        map.clear();
        map.put("categoryId", couponTO.getRelation().getRelationContent());
        model.addAttribute("category", goodsCategoryService.selectObject(map));
        // 加载公共数据
        initIndex(model);
        return "coupon/category.detail";
    }

    /**
     * 初始化添加页面
     * 
     * @author liuyt
     * @date 2015-10-21 上午11:34:28
     * @param model
     * @param req
     * @return
     * @version
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String couponCategoryAdd(Model model) {
        // 加载公共数据
        initIndex(model);
        List<Map<String, Object>> categories = goodsCategoryService
                .selectAllCategoryOrderByHid(null);
        model.addAttribute("categories", categories);
        return "coupon/category.edit";
    }

    /**
     * 初始化修改页面
     * 
     * @author liuyt
     * @date 2015-10-21 上午11:35:00
     * @param model
     * @param bulkId
     * @param req
     * @return
     * @version
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String couponCategoryEdit(Model model, String couponId,
            HttpServletRequest req) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("couponId", couponId);
        CouponCategoryTO couponTO = new CouponCategoryTO();
        couponTO.setCoupon((CouponCategory) couponCategoryService
                .selectObject(map));
        couponTO.setRelation((CouponRelation) couponRelationService
                .selectObject(map));
        model.addAttribute("couponTO", couponTO);
        List<Map<String, Object>> categories = goodsCategoryService
                .selectAllCategoryOrderByHid(null);
        model.addAttribute("categories", categories);
        return "coupon/category.edit";

    }

    /**
     * 保存操作
     * 
     * @author liuyt
     * @date 2015-10-21 上午11:34:28
     * @param model
     * @param req
     * @return
     * @version
     */
    @RequestMapping(value = {"add","edit"}, method = RequestMethod.POST)
    @ResponseBody
    public ResultTO saveCouponCategory(CouponCategoryTO coupon) {
        return couponCategoryService.saveCouponCategory(coupon);
    }

    /**
     * 删除活动(只做逻辑删除)
     * 
     * @author liuyt
     * @date 2015-10-22 下午4:00:12
     * @param bulkId
     * @return
     * @version
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO delCouponCategory(Integer couponId) {
        return couponCategoryService.deleteCouponCategory(couponId);
    }

    /**
     * 更新优惠券状态
     * 
     * @author liuyt
     * @date 2015-11-3 下午5:38:44
     * @param couponId
     * @return
     * @version
     */
    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO updateCouponCategoryStatus(Integer couponId, Integer status) {
        return couponCategoryService.updateCouponCategoryStatus(couponId,
                status);
    }

}
