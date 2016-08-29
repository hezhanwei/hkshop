package com.bluemobi.dao.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.goods.GoodsRecommend;

/**
 * 【推荐表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:49
 * 
 */
public interface GoodsRecommendDao extends MyBatisBaseDao {

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
	List<Map<String, Object>> getHomeGoodsRecommend(@Param("types") List<String> types);
}
