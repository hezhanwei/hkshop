package com.bluemobi.controller.api.coupon;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.po.coupon.CouponCategory;
import com.bluemobi.po.coupon.CouponListing;
import com.bluemobi.service.coupon.CouponCategoryService;
import com.bluemobi.service.coupon.CouponListingService;
import com.bluemobi.to.ResultTO;

/**
 * 优惠券模块 控制器
 * @ClassName CouponController
 * @author liuyt
 * @date 2015-11-4 上午10:28:07
 * @version
 */
@Controller
@RequestMapping("api/coupon")
public class CouponController extends AbstractAPIController {

    @Autowired
    private CouponCategoryService couponCategoryService;
    @Autowired
    private CouponListingService couponListingService;

    /**
     * 用户领取优惠券
     * @author liuyt
     * @date 2015-11-4 上午10:28:14
     * @return
     * @version
     */
    @RequestMapping(value = "receiveCoupon", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO receiveCouponListing(Integer couponId) {
        if(couponId == null || couponId == 0) {
            return ResultTO.newFailResultTO("缺少couponId参数", null);
        }
        return couponListingService.createCouponList(couponId, getUserid());
    }
    
    /**
     * 分页获取可用的优惠券信息列表
     * @author liuyt
     * @date 2015-11-4 上午10:30:49
     * @return
     * @version
     */
    @RequestMapping(value = "getAvailableCoupons", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getAvailableCouponCategorys(Integer type, Integer pageSize, Integer pageIndex) {
        Page<CouponCategory> pages = couponCategoryService.getAvailableCouponCategorys(type, pageSize, pageIndex);
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("data", pages.getData());
        mapResult.put("count", pages.getCount());
        return mapResult;
    }
    
    /**
     * 分页获取指定用户的优惠券子表信息
     * @author liuyt
     * @date 2015-11-4 上午10:52:24
     * @param status
     * @param type
     * @param sort
     * @return
     * @version
     */
    @RequestMapping(value = "getMyCouponListings", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getMyCouponListings(Integer status, Integer type, Integer sort, Integer pageSize, Integer pageIndex) {
        Page<CouponListing> pages = couponListingService.getCouponListings(getUserid(), status, type, sort, pageSize, pageIndex);
        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("data", pages.getData());
        mapResult.put("count", pages.getCount());
        return mapResult;
    }

}
