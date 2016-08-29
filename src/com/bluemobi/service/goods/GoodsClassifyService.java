package com.bluemobi.service.goods;

import java.util.List;
import java.util.Map;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.goods.GoodsClassify;

/**
 * 【商品分类表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-23 18:06:29
 * 
 */
public interface GoodsClassifyService extends MybatisBaseService {

	/**
	 * 获取分类信息列表
	 * @data 2016年8月23日 下午6:22:45
	 * @param goodsClassify
	 * @return
	 */
	List<GoodsClassify> getGoodsClassifys(GoodsClassify goodsClassify);
}
