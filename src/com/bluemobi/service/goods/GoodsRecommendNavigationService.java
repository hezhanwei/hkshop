package com.bluemobi.service.goods;

import java.util.List;
import java.util.Map;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.goods.GoodsRecommendNavigation;

/**
 * 【推荐导航表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 11:21:10
 * 
 */
public interface GoodsRecommendNavigationService extends MybatisBaseService {

	/**
	 * 查询推荐导航
	 * @data 2016年8月25日 下午5:51:18
	 * @param grn
	 * @return
	 */
	List<Map<String, Object>> getGoodsRecommendNavigation(GoodsRecommendNavigation grn);
}
