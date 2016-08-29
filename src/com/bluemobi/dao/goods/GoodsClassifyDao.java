package com.bluemobi.dao.goods;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.goods.GoodsClassify;

/**
 * 【商品分类表】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-23 18:06:29
 * 
 */
public interface GoodsClassifyDao extends MyBatisBaseDao {

	List<GoodsClassify> getGoodsClassifys(GoodsClassify goodsClassify);
}
