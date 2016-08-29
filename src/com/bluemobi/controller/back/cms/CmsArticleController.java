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
import com.bluemobi.po.cms.CmsArticle;
import com.bluemobi.service.cms.CmsArticleService;
import com.bluemobi.to.ResultTO;



/**
 * 【平台文章表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-29 14:29:33
 * 
 */
@Controller
@RequestMapping("cmsArticle")
public class CmsArticleController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsArticleController.class);
    
    @Autowired
    private CmsArticleService cmsArticleService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【平台文章表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "cms/article.index";
    }
    
    /**
     * 分页查询【平台文章表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = cmsArticleService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【平台文章表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Integer articleId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("articleId", articleId); 
        model.addAttribute("cmsArticle", cmsArticleService.selectObject(map));
        return "cms/article.detail";
    }
    
    /**
     * 进入新增【平台文章表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        return "cms/article.edit";
    }
    
    /**
     * 新增【平台文章表】数据
     * @param cmsArticle
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addCmsArticle(@ModelAttribute CmsArticle cmsArticle, BindingResult result) {
        try {
            cmsArticleService.insert(cmsArticle);
            LOGGER.info("用户【{}】添加平台文章表数据【{}】成功", new Object[] { this.getUserid(), cmsArticle } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加平台文章表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), cmsArticle, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【平台文章表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Integer articleId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("articleId", articleId); 
        model.addAttribute("cmsArticle", cmsArticleService.selectObject(map));
        return "cms/article.edit";
    }
    
    /**
     * 修改【平台文章表】数据
     * @param cmsArticle
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editCmsArticle(@ModelAttribute CmsArticle cmsArticle, BindingResult result) {
        try {
            cmsArticleService.update(cmsArticle);
            LOGGER.info("用户【{}】修改平台文章表数据【{}】成功", new Object[] { this.getUserid(), cmsArticle } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改平台文章表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), cmsArticle, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【平台文章表】数据
     * @param articleId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-29 14:29:33
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteCmsArticle(Integer articleId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("articleId", articleId); 
            cmsArticleService.delete(map);
            LOGGER.info("用户【{}】删除平台文章表数据【{}】成功", new Object[] { this.getUserid(), articleId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除平台文章表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), articleId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
