package com.bluemobi.controller.api.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.po.goods.GoodsClassify;
import com.bluemobi.po.goods.GoodsRecommend;
import com.bluemobi.po.goods.GoodsSpecialsale;
import com.bluemobi.po.groupon.GrouponGrab;
import com.bluemobi.service.goods.GoodsClassifyService;
import com.bluemobi.service.goods.GoodsContentService;
import com.bluemobi.service.goods.GoodsRecommendNavigationService;
import com.bluemobi.service.goods.GoodsRecommendService;
import com.bluemobi.service.goods.GoodsSpecialsaleService;
import com.bluemobi.service.groupon.GrouponGrabService;
import com.bluemobi.service.store.StoreContentService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.util.CommonStringUtils;

/**
 * 首页
 * <p>Title:IndexController </p>
 * <p>Description: </p>
 * <p>Company: </p> 
 * @author hezhanwei
 * @date 2016年8月15日 下午1:50:20
 */
@Controller
@RequestMapping("api/home")
public class HomeController extends AbstractAPIController{
	
	@Autowired
	private StoreContentService storeContentService;
	@Autowired
	private GoodsContentService goodsContentService;
	@Autowired
	private GoodsRecommendService goodsRecommendService;
	@Autowired
    private GoodsRecommendNavigationService goodsRecommendNavigationService;
	@Autowired
	private GoodsSpecialsaleService goodsSpecialsaleService;
	
	@Autowired
    private GrouponGrabService grouponGrabService;
	
	/**
	 * 首页
	 * @data 2016年8月25日 下午5:44:52
	 * @return
	 */
	@RequestMapping(value="homeGoodsRecommend",method=RequestMethod.POST)
	@ResponseBody
	public ResultTO homeGoodsRecommend(){
		
		List<String> types = new ArrayList<>();
		types.add(CommonStringUtils.STATUS_33);//热卖商品
		types.add(CommonStringUtils.STATUS_44);//首页banner
		Map<String, List<Map<String, Object>>> homeGoodsRecommend = goodsRecommendService.getHomeGoodsRecommend(types);
		
		//导航 ： 今日推荐  今日特卖  每日好店
		List<Map<String, Object>> grnList = goodsRecommendNavigationService.getGoodsRecommendNavigation(null);
		
		//全球盛宴
		GoodsSpecialsale goodsSpecialsale = new GoodsSpecialsale();
		goodsSpecialsale.setStatus(CommonStringUtils.STATUS_11);
		List<Map<String, Object>> goodsClassifys = goodsSpecialsaleService.getCountryClassify(goodsSpecialsale);
		
		//限时抢购
		GrouponGrab grouponGrab = new GrouponGrab();
		List<Map<String, Object>> snapUpGoods = grouponGrabService.getSnapUpGoods(null);
		
		homeGoodsRecommend.put("grnList", grnList);
		homeGoodsRecommend.put("goodsClassifys", goodsClassifys);
		homeGoodsRecommend.put("snapUpGoods",snapUpGoods);
		
       return ResultTO.newSuccessResultTO("首页banner,推荐导航,全球盛宴,热卖商品", homeGoodsRecommend);
	}
	
}
