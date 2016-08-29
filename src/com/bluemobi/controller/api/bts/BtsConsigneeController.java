package com.bluemobi.controller.api.bts;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bluemobi.constant.BaseConstant;
import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.po.bts.BtsConsignee;
import com.bluemobi.service.bts.BtsConsigneeService;
import com.bluemobi.service.trend.TrendRegionService;
import com.bluemobi.to.ResultTO;

/**
 * 收货人信息控制器 -- API接口
 * 
 * @ClassName BtsConsigneeController
 * @author liuyt
 * @date 2015-11-17 下午5:02:25
 * @version
 */
@Controller
@RequestMapping("api/consignee")
public class BtsConsigneeController extends AbstractAPIController {

    @Autowired
    private BtsConsigneeService btsConsigneeService;
    @Autowired
    private TrendRegionService trendRegionService;

    /**
     * 创建收货人信息
     * 
     * @author liuyt
     * @date 2015-11-17 下午5:27:14
     * @param consignee
     * @return
     * @version
     */
    public ResultTO createConsignee(BtsConsignee consignee) {
        ResultTO resultTO = validRegion(consignee.getRegionId());

        if(resultTO.getStatus() == BaseConstant.STATUS_FAILURE) {
            return resultTO;   
        }
        
        consignee.setUserid((long) getUserid());
        consignee.setCtime(Calendar.getInstance().getTime());
        int ret = btsConsigneeService.insert(consignee);
        return buildOperationResult(ret);
    }

    
    /**
     * 删除收货人信息
     * 
     * @author liuyt
     * @date 2015-11-17 下午5:27:43
     * @param consigneeId
     * @return
     * @version
     */
    public ResultTO deleteConsignee(Long consigneeId) {
        ResultTO resultTO = verifyConsignee(consigneeId);
        if(resultTO.getStatus() == BaseConstant.STATUS_FAILURE) {
            return resultTO;
        }
        
        BtsConsignee consignee = (BtsConsignee) resultTO.getData();
        consignee.setStatus((byte) 1);
        int ret = btsConsigneeService.update(consignee);
        return buildOperationResult(ret);
    }

    /**
     * 修改收货人信息
     * 
     * @author liuyt
     * @date 2015-11-17 下午5:28:14
     * @param consignee
     * @return
     * @version
     */
    public ResultTO updateConsignee(BtsConsignee consignee) {
        Long consigneeId = (consignee == null || consignee.getConsigneeId() == null) ? null :  consignee.getConsigneeId();
        
        ResultTO resultTO = verifyConsignee(consigneeId);
        
        if(resultTO.getData() == null) {
            return resultTO;
        }
        
        resultTO = validRegion(consignee.getRegionId());
        if(resultTO.getStatus() == BaseConstant.STATUS_FAILURE) {
            return resultTO;   
        }
        
        consignee.setMtime(Calendar.getInstance().getTime());
        int ret = btsConsigneeService.update(consignee);
        return buildOperationResult(ret);
    }
    
    /**
     * 校验地区信息有效性
     * @author liuyt
     * @date 2015-11-18 上午10:20:54
     * @param regionId
     * @return
     * @version
     */
    private ResultTO validRegion(Integer regionId) {
        if (regionId == null || regionId == 0) {
            return ResultTO.newFailResultTO("地区信息参数不正确", null);
        }

        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("regionId", regionId);
        Map<String, Object> regionMap = trendRegionService.selectMap(parameter);
        if (regionMap == null || regionMap.isEmpty()) {
            return ResultTO.newFailResultTO("地区信息不存在", null);
        }
        return ResultTO.newSuccessResultTO(null);
    }
    
    /**
     * 校验收货人信息有效性
     * @author liuyt
     * @date 2015-11-18 上午10:23:26
     * @param consigneeId
     * @return
     * @version 
     */
    private ResultTO verifyConsignee(Long consigneeId) {
        if (consigneeId == null || consigneeId == 0) {
            return ResultTO.newFailResultTO("收货人信息为空", null);
        }
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("consigneeId", consigneeId);
        BtsConsignee consignee = btsConsigneeService.selectObject(parameter);
        if (consignee == null) {
            return ResultTO.newFailResultTO("收货人信息不存在", null);
        }
        
        if(consignee.getUserid().longValue() != (long) getUserid()) {
            return ResultTO.newFailResultTO("收货人信息不属于当前用户", null);
        }
        
        if (consignee.getStatus().intValue() == 0) {
            return ResultTO.newSuccessResultTO("收货人信息已经被删除", null);
        }
        return ResultTO.newSuccessResultTO(consignee);
    }
    
    /**
     * 构建操作的返回结果
     * @author liuyt
     * @date 2015-11-18 上午10:48:25
     * @param ret
     * @version 
     * @return TODO
     */
    private ResultTO buildOperationResult(int ret) {
        if (ret != 1) {
            return ResultTO.newFailResultTO("系统异常", null);
        } else {
            return ResultTO.newSuccessResultTO("操作成功", null);
        }
    }


}
