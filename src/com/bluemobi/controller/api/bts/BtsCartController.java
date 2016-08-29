package com.bluemobi.controller.api.bts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.po.bts.BtsCart;
import com.bluemobi.service.bts.BtsCartService;
import com.bluemobi.to.ResultTO;


/**
 * 购物车控制器
 * @Description
 * @author haojian 309444359@qq.com
 * @date 2015-10-14 上午11:11:35 
 *
 */
@Controller
@RequestMapping("api/cart")
public class BtsCartController extends AbstractAPIController{
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BtsCartController.class);
    
    @Autowired
    private BtsCartService btsCartService;
    
    /**
     * 购物车添加商品
     * @author haojian
     * @date 2015-10-14 上午11:30:29 
     * @param request
     * @param skuId 商品skuid
     * @return
     * @return ResultTO
     */
    @RequestMapping(value = "addGoodsToCart" , method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addGoodsToCart(Long skuId) {
        
        int userid = this.getUserid();
        
        BtsCart btsCart = btsCartService.addGoodsToCart(userid, skuId);
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userid ", btsCart.getUserid());
        map.put("skuId ", btsCart.getSkuId());
        map.put("quantity ", btsCart.getQuantity());
        
        LOGGER.info("用户【{}】添加商品【{}】到购物车，购物车信息【{}】", new Object[]{userid, skuId, btsCart.toString()} );
        
        return ResultTO.newSuccessResultTO("添加购物车成功", map);
        
    }
    
    /**
     * 获取购物车信息
     * @author haojian
     * @date 2015-10-14 下午4:55:21 
     * @param request
     * @return
     * @return ResultTO
     */
    @RequestMapping(value = "getBtsCartList" , method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getBtsCartList() {
        
        int userid = this.getUserid();
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userid", userid);
        List<Map<String, Object>> list = btsCartService.selectMapList(map);
        
        LOGGER.info("用户【{}】的【{}】", new Object[]{userid, list} );
        
        return ResultTO.newSuccessResultTO("获取购物车信息成功", list);
        
    }
    
    /**
     * 删除购物车商品
     * @author haojian
     * @date 2015-10-14 下午5:26:02 
     * @param request
     * @param cartIds
     * @return
     * @return ResultTO
     */
    @RequestMapping(value = "deleteBtsCartByIds" , method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteBtsCartByIds(String cartIds) {
        
        int userid = this.getUserid();
        
        btsCartService.deleteBtsCartByIds(userid, cartIds);
        
        LOGGER.info("用户【{}】的购物车信息【{}】删除成功", new Object[]{userid, cartIds} );
        
        return ResultTO.newSuccessResultTO("删除购物车信息成功", cartIds);
        
    }
    
    /**
     * 修改购物车
     * @author haojian
     * @date 2015-10-14 下午5:34:18 
     * @param request
     * @param cartId
     * @param quantity
     * @return
     * @return ResultTO
     */
    @RequestMapping(value = "updateBtsCart" , method = RequestMethod.POST)
    @ResponseBody
    public ResultTO updateBtsCart(int cartId, int quantity) {
        
        int userid = this.getUserid();
        
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userid", userid);
        map.put("cartId", cartId);
        map.put("quantity", quantity);
        btsCartService.update(map);
        
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("cartId", cartId);
        Map<String,Object> resultMap = btsCartService.selectMap(map2);
        
        LOGGER.info("用户【{}】的购物车信息【{}】修改成功，修改后为【{}】", new Object[]{userid, cartId, resultMap} );
        
        return ResultTO.newSuccessResultTO("修改购物车信息成功", resultMap);
        
    }
    


}
