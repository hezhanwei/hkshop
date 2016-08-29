package com.bluemobi.serviceimpl.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.goods.GoodsSpecialsaleDao;
import com.bluemobi.po.goods.GoodsSpecialsale;
import com.bluemobi.service.goods.GoodsSpecialsaleService;

/**
 * 【今日特卖分类表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:50
 * 
 */
@Service(value = "goodsSpecialsaleService")
public class GoodsSpecialsaleServiceImpl extends MybatisBaseServiceImpl implements GoodsSpecialsaleService {

    @Autowired
    private GoodsSpecialsaleDao goodsSpecialsaleDao;

    @Override
    public MyBatisBaseDao getDao() {
        return goodsSpecialsaleDao;
    }

	@Override
	public List<Map<String, Object>> getCountryClassify(GoodsSpecialsale gss) {
		return goodsSpecialsaleDao.getCountryClassify(gss);
	}

}
