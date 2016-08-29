package com.bluemobi.service.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.goods.GoodsRecommend;

/**
 * 【推荐表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:49
 * 
 */
public interface GoodsRecommendService extends MybatisBaseService {

	/**
	 * 添加商品推荐设置
	 * @data 2016年8月23日 下午1:42:33
	 * @param goodsRecommend
	 * @return
	 */
	int insertGoodsRecommend(GoodsRecommend goodsRecommend);
	
	/**
	 * 获取首页轮播，热卖商品
	 * @data 2016年8月25日 下午4:16:09
	 * @param types 类型
	 * @return
	 */
	Map<String, List<Map<String, Object>>> getHomeGoodsRecommend(@Param("types") List<String> types);
}
