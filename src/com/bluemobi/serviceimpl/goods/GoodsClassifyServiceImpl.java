package com.bluemobi.serviceimpl.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.goods.GoodsClassifyDao;
import com.bluemobi.po.goods.GoodsClassify;
import com.bluemobi.service.goods.GoodsClassifyService;

/**
 * 【商品分类表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-23 18:06:29
 * 
 */
@Service(value = "goodsClassifyService")
public class GoodsClassifyServiceImpl extends MybatisBaseServiceImpl implements GoodsClassifyService {

    @Autowired
    private GoodsClassifyDao goodsClassifyDao;

    @Override
    public MyBatisBaseDao getDao() {
        return goodsClassifyDao;
    }

	@Override
	public List<GoodsClassify> getGoodsClassifys(GoodsClassify goodsClassify) {
		return goodsClassifyDao.getGoodsClassifys(goodsClassify);
	}

}
