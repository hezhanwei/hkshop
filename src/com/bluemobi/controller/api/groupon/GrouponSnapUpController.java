package com.bluemobi.controller.api.groupon;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.service.groupon.GrouponGrabService;
import com.bluemobi.to.ResultTO;

/**
 * 
 * <p>Title:GrouponSnapUpController </p>
 * <p>Description: 抢购</p>
 * <p>Company: </p> 
 * @author hezhanwei
 * @date 2016年8月26日 下午1:12:21
 */
@RequestMapping("api/GrouponSnapUp")
@Controller
public class GrouponSnapUpController extends AbstractAPIController{
	
	@Autowired
    private GrouponGrabService grouponGrabService;
	
	/**
	 * 获取抢购列表
	 * @data 2016年8月26日 下午1:28:31
	 * @return
	 */
	@RequestMapping(value="getSnapUpGoods",method=RequestMethod.POST)
	@ResponseBody
	public ResultTO getSnapUpGoods(){
		List<Map<String, Object>> snapUpGoods = grouponGrabService.getSnapUpGoods(null);
		
		if (snapUpGoods!=null && !snapUpGoods.isEmpty()) {
			return ResultTO.newSuccessResultTO("抢购列表", snapUpGoods);
		}
		
		return ResultTO.newSuccessResultTO("暂无抢购列表", null);
	}
}