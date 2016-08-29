package com.bluemobi.dao.bts;

import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.bts.BtsConsignee;

/**
 * 【】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 17:01:42
 * 
 */
public interface BtsConsigneeDao extends MyBatisBaseDao {
    
    BtsConsignee selectForOrder(Map<String, Object> parameter);
    
}
