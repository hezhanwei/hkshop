package com.bluemobi.controller.back.cms;
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
import com.bluemobi.po.cms.CmsBanner;
import com.bluemobi.service.cms.CmsBannerService;
import com.bluemobi.to.ResultTO;



/**
 * 【首页banner表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-08 17:19:10
 * 
 */
@Controller
@RequestMapping("cmsBanner")
public class CmsBannerController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsBannerController.class);
    
    @Autowired
    private CmsBannerService cmsBannerService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【首页banner表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "cms/banner.index";
    }
    
    /**
     * 分页查询【首页banner表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = cmsBannerService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【首页banner表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Integer bannerId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("bannerId", bannerId); 
        model.addAttribute("cmsBanner", cmsBannerService.selectObject(map));
        return "cms/banner.detail";
    }
    
    /**
     * 进入新增【首页banner表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        return "cms/banner.edit";
    }
    
    /**
     * 新增【首页banner表】数据
     * @param cmsBanner
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addCmsBanner(@ModelAttribute CmsBanner cmsBanner, BindingResult result) {
        try {
            cmsBannerService.insert(cmsBanner);
            LOGGER.info("用户【{}】添加首页banner表数据【{}】成功", new Object[] { this.getUserid(), cmsBanner } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加首页banner表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), cmsBanner, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【首页banner表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Integer bannerId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bannerId", bannerId); 
        model.addAttribute("cmsBanner", cmsBannerService.selectObject(map));
        return "cms/banner.edit";
    }
    
    /**
     * 修改【首页banner表】数据
     * @param cmsBanner
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editCmsBanner(@ModelAttribute CmsBanner cmsBanner, BindingResult result) {
        try {
            cmsBannerService.update(cmsBanner);
            LOGGER.info("用户【{}】修改首页banner表数据【{}】成功", new Object[] { this.getUserid(), cmsBanner } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改首页banner表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), cmsBanner, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【首页banner表】数据
     * @param bannerId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-08 17:19:10
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteCmsBanner(Integer bannerId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("bannerId", bannerId); 
            cmsBannerService.delete(map);
            LOGGER.info("用户【{}】删除首页banner表数据【{}】成功", new Object[] { this.getUserid(), bannerId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除首页banner表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), bannerId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
