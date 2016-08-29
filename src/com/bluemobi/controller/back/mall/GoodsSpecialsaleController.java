package com.bluemobi.controller.back.mall;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.bluemobi.po.goods.GoodsClassify;
import com.bluemobi.po.goods.GoodsSpecialsale;
import com.bluemobi.po.trend.TrendCountry;
import com.bluemobi.service.goods.GoodsClassifyService;
import com.bluemobi.service.goods.GoodsSpecialsaleService;
import com.bluemobi.service.trend.TrendCountryService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.util.CommonStringUtils;



/**
 * 【今日特卖分类表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:50
 * 
 */
@Controller
@RequestMapping({"goodsSpecialsale","saleClassify"})
public class GoodsSpecialsaleController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecialsaleController.class);
    
    @Autowired
    private GoodsSpecialsaleService goodsSpecialsaleService;
    @Autowired
    private GoodsClassifyService goodsClassifyService;
    @Autowired
    private TrendCountryService trendCountryService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【今日特卖分类表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model,String type,HttpSession session) {
        // 加载公共数据
        initIndex(model);
        if (!StringUtils.isBlank(type)) {
        	session.setAttribute("classify_type", type);
		}
        return "mall/specialsale.index";
    }
    
    /**
     * 分页查询【今日特卖分类表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(HttpSession session,String key, int pageSize, int pageIndex) {
    	
    	String status = (String) session.getAttribute("classify_type");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("key", key);
        Page<Map<String, Object>> page = goodsSpecialsaleService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【今日特卖分类表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Long specialsaleId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("specialsaleId", specialsaleId); 
        model.addAttribute("goodsSpecialsale", goodsSpecialsaleService.selectObject(map));
        return "mall/specialsale.detail";
    }
    
    /**
     * 进入新增【今日特卖分类表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model,HttpSession session) {
        // 加载公共数据
        initIndex(model);
        String type = (String) session.getAttribute("classify_type");
        if (CommonStringUtils.STATUS_00.equals(type)) {
        	List<GoodsClassify> goodsClassifys = goodsClassifyService.getGoodsClassifys(null);
        	model.addAttribute("categoryArr", goodsClassifys);
			
		}else{
			List<TrendCountry> trendCountrys = trendCountryService.getTrendCountrys(null);
        	model.addAttribute("countryArr", trendCountrys);
		}
        return "mall/specialsale.edit";
    }
    
    /**
     * 新增【今日特卖分类表】数据
     * @param goodsSpecialsale
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addGoodsSpecialsale(@ModelAttribute GoodsSpecialsale goodsSpecialsale, 
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	
        	Map<String, Object> uploadResultMap = null;
        	goodsSpecialsale.setCreateBy(getUsername());//创建人
        	goodsSpecialsale.setCtime(new Date());//创建时间
        	
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.GOODS_CONTENT_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						goodsSpecialsale.setFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
            goodsSpecialsaleService.insert(goodsSpecialsale);
            
            LOGGER.info("用户【{}】添加今日特卖分类表数据【{}】成功", new Object[] { this.getUserid(), goodsSpecialsale } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加今日特卖分类表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsSpecialsale, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【今日特卖分类表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long specialsaleId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("specialsaleId", specialsaleId); 
        model.addAttribute("goodsSpecialsale", goodsSpecialsaleService.selectObject(map));
        
        List<GoodsClassify> goodsClassifys = goodsClassifyService.getGoodsClassifys(null);
        model.addAttribute("categoryArr", goodsClassifys);
        
        return "mall/specialsale.edit";
    }
    
    /**
     * 修改【今日特卖分类表】数据
     * @param goodsSpecialsale
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editGoodsSpecialsale(@ModelAttribute GoodsSpecialsale goodsSpecialsale, 
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	Map<String, Object> uploadResultMap = null;
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.GOODS_CONTENT_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						goodsSpecialsale.setFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
            goodsSpecialsaleService.update(goodsSpecialsale);
            LOGGER.info("用户【{}】修改今日特卖分类表数据【{}】成功", new Object[] { this.getUserid(), goodsSpecialsale } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改今日特卖分类表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsSpecialsale, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【今日特卖分类表】数据
     * @param specialsaleId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-19 13:09:50
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteGoodsSpecialsale(Integer specialsaleId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("specialsaleId", specialsaleId); 
            goodsSpecialsaleService.delete(map);
            LOGGER.info("用户【{}】删除今日特卖分类表数据【{}】成功", new Object[] { this.getUserid(), specialsaleId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除今日特卖分类表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), specialsaleId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
