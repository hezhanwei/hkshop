package com.bluemobi.service.bts;

import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.bts.BtsConsignee;

/**
 * 【】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 17:01:42
 * 
 */
public interface BtsConsigneeService extends MybatisBaseService {

    /**
     * 校验指定用户的收件人信息
     * @author liuyt
     * @date 2015-11-18 下午2:03:13
     * @param userId
     * @param consigneeId   收货人信息Id, 如果id为0,取用户的默认收货人
     * @return
     * @version
     */
    BtsConsignee getConsigneeForOrder(int userId, int consigneeId);

}
