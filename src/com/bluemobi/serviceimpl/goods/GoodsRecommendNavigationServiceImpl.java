package com.bluemobi.serviceimpl.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.goods.GoodsRecommendNavigationDao;
import com.bluemobi.po.goods.GoodsRecommendNavigation;
import com.bluemobi.service.goods.GoodsRecommendNavigationService;

/**
 * 【推荐导航表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 11:21:10
 * 
 */
@Service(value = "goodsRecommendNavigationService")
public class GoodsRecommendNavigationServiceImpl extends MybatisBaseServiceImpl implements GoodsRecommendNavigationService {

    @Autowired
    private GoodsRecommendNavigationDao goodsRecommendNavigationDao;

    @Override
    public MyBatisBaseDao getDao() {
        return goodsRecommendNavigationDao;
    }

	@Override
	public List<Map<String, Object>> getGoodsRecommendNavigation(GoodsRecommendNavigation grn) {
		return goodsRecommendNavigationDao.getGoodsRecommendNavigation(grn);
	}

}
