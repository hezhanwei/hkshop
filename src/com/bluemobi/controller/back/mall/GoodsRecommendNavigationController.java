package com.bluemobi.controller.back.mall;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.appcore.page.Page;
import com.bluemobi.constant.GoodsConstant;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.goods.GoodsRecommendNavigation;
import com.bluemobi.service.goods.GoodsRecommendNavigationService;
import com.bluemobi.to.ResultTO;



/**
 * 【推荐导航表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 11:21:10
 * 
 */
@Controller
@RequestMapping("goodsRecommendNavigation")
public class GoodsRecommendNavigationController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRecommendNavigationController.class);
    
    @Autowired
    private GoodsRecommendNavigationService goodsRecommendNavigationService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【推荐导航表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "mall/recommendNavigation.index";
    }
    
    /**
     * 分页查询【推荐导航表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = goodsRecommendNavigationService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【推荐导航表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Long navigationId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("navigationId", navigationId); 
        model.addAttribute("goodsRecommendNavigation", goodsRecommendNavigationService.selectObject(map));
        return "goods/recommendNavigation.detail";
    }
    
    /**
     * 进入新增【推荐导航表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        return "mall/recommendNavigation.edit";
    }
    
    /**
     * 新增【推荐导航表】数据
     * @param goodsRecommendNavigation
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addGoodsRecommendNavigation(@ModelAttribute GoodsRecommendNavigation goodsRecommendNavigation,
			@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	goodsRecommendNavigation.setCtime(new Date());//创建时间
        	Map<String, Object> uploadResultMap = null;
        	
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.GOODS_CONTENT_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						goodsRecommendNavigation.setNavigationFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
        	
            goodsRecommendNavigationService.insert(goodsRecommendNavigation);
            LOGGER.info("用户【{}】添加推荐导航表数据【{}】成功", new Object[] { this.getUserid(), goodsRecommendNavigation } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加推荐导航表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsRecommendNavigation, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【推荐导航表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long navigationId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("navigationId", navigationId); 
        model.addAttribute("goodsRecommendNavigation", goodsRecommendNavigationService.selectObject(map));
        return "mall/recommendNavigation.edit";
    }
    
    /**
     * 修改【推荐导航表】数据
     * @param goodsRecommendNavigation
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editGoodsRecommendNavigation(@ModelAttribute GoodsRecommendNavigation goodsRecommendNavigation,
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	
        	Map<String, Object> uploadResultMap = null;
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.GOODS_CONTENT_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						goodsRecommendNavigation.setNavigationFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
            goodsRecommendNavigationService.update(goodsRecommendNavigation);
            LOGGER.info("用户【{}】修改推荐导航表数据【{}】成功", new Object[] { this.getUserid(), goodsRecommendNavigation } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改推荐导航表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsRecommendNavigation, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【推荐导航表】数据
     * @param navigationId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 11:21:10
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteGoodsRecommendNavigation(Integer navigationId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("navigationId", navigationId); 
            goodsRecommendNavigationService.delete(map);
            LOGGER.info("用户【{}】删除推荐导航表数据【{}】成功", new Object[] { this.getUserid(), navigationId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除推荐导航表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), navigationId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
