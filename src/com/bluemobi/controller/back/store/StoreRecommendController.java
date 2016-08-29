package com.bluemobi.controller.back.store;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import com.bluemobi.po.store.StoreRecommend;
import com.bluemobi.service.store.StoreRecommendService;
import com.bluemobi.to.ResultTO;



/**
 * 【每日好店】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 17:16:56
 * 
 */
@Controller
@RequestMapping("storeRecommend")
public class StoreRecommendController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreRecommendController.class);
    
    @Autowired
    private StoreRecommendService storeRecommendService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【每日好店】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "store/recommend.index";
    }
    
    /**
     * 分页查询【每日好店】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = storeRecommendService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【每日好店】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Long recommendId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("recommendId", recommendId); 
        model.addAttribute("storeRecommend", storeRecommendService.selectObject(map));
        return "store/recommend.detail";
    }
    
    /**
     * 进入新增【每日好店】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        return "store/recommend.edit";
    }
    
    /**
     * 新增【每日好店】数据
     * @param storeRecommend
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addStoreRecommend(@ModelAttribute StoreRecommend storeRecommend, BindingResult result) {
        try {
        	storeRecommend.setCtime(new Date());
        	storeRecommend.setMtime(new Date());
            storeRecommendService.insert(storeRecommend);
            LOGGER.info("用户【{}】添加每日好店数据【{}】成功", new Object[] { this.getUserid(), storeRecommend } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加每日好店数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), storeRecommend, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【每日好店】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long recommendId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recommendId", recommendId); 
        model.addAttribute("storeRecommend", storeRecommendService.selectObject(map));
        return "store/recommend.edit";
    }
    
    /**
     * 修改【每日好店】数据
     * @param storeRecommend
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editStoreRecommend(@ModelAttribute StoreRecommend storeRecommend, BindingResult result) {
        try {
            storeRecommendService.update(storeRecommend);
            LOGGER.info("用户【{}】修改每日好店数据【{}】成功", new Object[] { this.getUserid(), storeRecommend } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改每日好店数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), storeRecommend, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【每日好店】数据
     * @param recommendId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 17:16:56
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteStoreRecommend(Integer recommendId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("recommendId", recommendId); 
            storeRecommendService.delete(map);
            LOGGER.info("用户【{}】删除每日好店数据【{}】成功", new Object[] { this.getUserid(), recommendId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除每日好店数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), recommendId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
