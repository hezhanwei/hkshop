package com.bluemobi.dao.goods;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.goods.GoodsSpecialsale;

/**
 * 【今日特卖分类表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:50
 * 
 */
public interface GoodsSpecialsaleDao extends MyBatisBaseDao {

	/**
	 * 获取国家分类
	 * @data 2016年8月25日 下午5:58:27
	 * @param gss
	 * @return
	 */
	List<Map<String, Object>> getCountryClassify(GoodsSpecialsale gss);
}
