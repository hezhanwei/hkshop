package com.bluemobi.service.goods;

import java.util.List;
import java.util.Map;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.goods.GoodsSpecialsale;

/**
 * 【今日特卖分类表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:50
 * 
 */
public interface GoodsSpecialsaleService extends MybatisBaseService {
	
	/**
	 * 获取国家分类
	 * @data 2016年8月25日 下午5:58:27
	 * @param gss
	 * @return
	 */
	List<Map<String, Object>> getCountryClassify(GoodsSpecialsale gss);
}
