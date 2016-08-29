package com.bluemobi.dao.bts;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.bts.BtsCart;

/**
 * 【购物车】 数据访问对象 接口
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-14 11:01:20
 *
 */
public interface BtsCartDao extends MyBatisBaseDao{    
	
    void deleteBtsCartByIds(Map<String, Object> map);
    
    List<BtsCart> selectBtsCartByIds(Map<String, Object> parameter);


}


