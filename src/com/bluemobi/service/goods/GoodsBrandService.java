package com.bluemobi.service.goods;

import java.util.Map;

import com.appcore.service.MybatisBaseService;

/**
 * 【商品品牌表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-22 15:18:56
 * 
 */
public interface GoodsBrandService extends MybatisBaseService {

    /**
     * 批量删除品牌
     * 
     * @param paramMap
     *            传入的map对象的值为id的String[]数组
     * @return
     */
    int deleteByIds(Map<String, Object> paramMap);

}
