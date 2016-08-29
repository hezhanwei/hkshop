package com.bluemobi.serviceimpl.goods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.goods.GoodsContentDao;
import com.bluemobi.dao.goods.GoodsRecommendDao;
import com.bluemobi.po.goods.GoodsRecommend;
import com.bluemobi.service.goods.GoodsRecommendService;
import com.bluemobi.to.goods.GoodsContentAndSkuTO;
import com.bluemobi.util.CommonStringUtils;

/**
 * 【推荐表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:49
 * 
 */
@Service(value = "goodsRecommendService")
public class GoodsRecommendServiceImpl extends MybatisBaseServiceImpl implements GoodsRecommendService {

	@Autowired
	private GoodsRecommendDao goodsRecommendDao;
	@Autowired
	private GoodsContentDao goodsContentDao;

	@Override
	public MyBatisBaseDao getDao() {
		return goodsRecommendDao;
	}

	@Override
	public int insertGoodsRecommend(GoodsRecommend goodsRecommend) {
		
		Map<String, Object> paramMap = new HashMap<>();
		GoodsContentAndSkuTO gcs = null;
		if (StringUtils.isBlank(goodsRecommend.getRecommendType())) {
			return 0;
		}
	
		//根据商品sku查询商品详情
		paramMap.put("sku",goodsRecommend.getGoodsSku());
		List<GoodsContentAndSkuTO> selectAllContentAndSku = goodsContentDao.selectAllContentAndSku(paramMap);
		if (selectAllContentAndSku!=null && !selectAllContentAndSku.isEmpty()) {
			gcs = selectAllContentAndSku.get(0);
			
			goodsRecommend.setGoodsName(gcs.getName());
			goodsRecommend.setGoodsPrice(gcs.getPrice());
			goodsRecommend.setGoodsMemo(gcs.getMemo());
			goodsRecommend.setSalesVolume(gcs.getSalesVolume());
		}
		return goodsRecommendDao.insert(goodsRecommend);
	}

	@Override
	public Map<String, List<Map<String, Object>>> getHomeGoodsRecommend(List<String> types) {
		
		Map<String, List<Map<String, Object>>> resultMap = new HashMap<>();
		List<Map<String, Object>> goodsOnsaleList = new ArrayList<>();
		List<Map<String, Object>> homeRecommendList = new ArrayList<>();
		
		List<Map<String, Object>> homeGoodsRecommend = goodsRecommendDao.getHomeGoodsRecommend(types);
		if (homeGoodsRecommend!=null && !homeGoodsRecommend.isEmpty()) {
			for (Map<String, Object> map : homeGoodsRecommend) {
				for(String key:map.keySet()){
					if (CommonStringUtils.RECOMMEND_TYPE.equals(key) && CommonStringUtils.STATUS_33.equals(map.get(key))) {
						goodsOnsaleList.add(map);//热卖商品
					}else if(CommonStringUtils.RECOMMEND_TYPE.equals(key) && CommonStringUtils.STATUS_44.equals(map.get(key))){
						homeRecommendList.add(map);//首页banner
					}
				}
			}
		}
		resultMap.put("goodsOnsaleMap", goodsOnsaleList);
		resultMap.put("homeRecommendMap", homeRecommendList);
		
		return resultMap;
	}

}
