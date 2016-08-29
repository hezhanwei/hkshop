package com.bluemobi.controller.back.mall;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.goods.GoodsClassify;
import com.bluemobi.service.goods.GoodsClassifyService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.util.CommonStringUtils;



/**
 * 【商品分类表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-23 18:06:29
 * 
 */
@Controller
@RequestMapping("goodsClassify")
public class GoodsClassifyController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsClassifyController.class);
    
    @Autowired
    private GoodsClassifyService goodsClassifyService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【商品分类表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "mall/classify.index";
    }
    
    /**
     * 分页查询【商品分类表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = goodsClassifyService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【商品分类表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Long classifyId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("classifyId", classifyId); 
        model.addAttribute("goodsClassify", goodsClassifyService.selectObject(map));
        return "mall/classify.detail";
    }
    
    /**
     * 进入新增【商品分类表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        List<GoodsClassify> goodsClassifys = goodsClassifyService.getGoodsClassifys(null);
        model.addAttribute("categoryArr", goodsClassifys);
        return "mall/classify.edit";
    }
    /**
     * 新增【商品分类表】数据
     * @param goodsClassify
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addGoodsClassify(@ModelAttribute GoodsClassify goodsClassify, BindingResult result) {
        try {
        	goodsClassify.setCtime(new Date());
        	goodsClassify.setType(CommonStringUtils.STATUS_11);
        	goodsClassify.setIsDelete(CommonStringUtils.STATUS_11);
            goodsClassifyService.insert(goodsClassify);
            LOGGER.info("用户【{}】添加商品分类表数据【{}】成功", new Object[] { this.getUserid(), goodsClassify } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加商品分类表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsClassify, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【商品分类表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long classifyId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("classifyId", classifyId); 
        model.addAttribute("goodsClassify", goodsClassifyService.selectObject(map));
        return "mall/classify.edit";
    }
    
    /**
     * 修改【商品分类表】数据
     * @param goodsClassify
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editGoodsClassify(@ModelAttribute GoodsClassify goodsClassify, BindingResult result) {
        try {
            goodsClassifyService.update(goodsClassify);
            LOGGER.info("用户【{}】修改商品分类表数据【{}】成功", new Object[] { this.getUserid(), goodsClassify } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改商品分类表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsClassify, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【商品分类表】数据
     * @param classifyId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-23 18:06:29
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteGoodsClassify(Integer classifyId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("classifyId", classifyId); 
            goodsClassifyService.delete(map);
            LOGGER.info("用户【{}】删除商品分类表数据【{}】成功", new Object[] { this.getUserid(), classifyId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除商品分类表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), classifyId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
