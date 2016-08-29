package com.bluemobi.serviceimpl.goods;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.goods.GoodsBrandDao;
import com.bluemobi.service.goods.GoodsBrandService;

/**
 * 【商品品牌表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-22 15:18:56
 * 
 */
@Service(value = "goodsBrandService")
public class GoodsBrandServiceImpl extends MybatisBaseServiceImpl implements GoodsBrandService {

    @Autowired
    private GoodsBrandDao goodsBrandDao;
    
    @Override
    public MyBatisBaseDao getDao() {
        return goodsBrandDao;
    }

    public int deleteByIds(Map<String,Object> parameter) {
        return goodsBrandDao.deleteByIds(parameter);
    }
}
