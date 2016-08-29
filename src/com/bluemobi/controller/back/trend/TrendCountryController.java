package com.bluemobi.controller.back.trend;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.bluemobi.po.trend.TrendCountry;
import com.bluemobi.service.trend.TrendCountryService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.util.CommonStringUtils;



/**
 * 【国家表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 14:50:30
 * 
 */
@Controller
@RequestMapping("trendCountry")
public class TrendCountryController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(TrendCountryController.class);
    
    @Autowired
    private TrendCountryService trendCountryService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【国家表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "trend/country.index";
    }
    
    /**
     * 分页查询【国家表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = trendCountryService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【国家表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Integer countryId) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("countryId", countryId); 
        model.addAttribute("trendCountry", trendCountryService.selectObject(map));
        return "trend/country.detail";
    }
    
    /**
     * 进入新增【国家表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        return "trend/country.edit";
    }
    
    /**
     * 新增【国家表】数据
     * @param trendCountry
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addTrendCountry(@ModelAttribute TrendCountry trendCountry, 
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	trendCountry.setType(CommonStringUtils.STATUS_11);
        	trendCountry.setCtime(new Date());
        	trendCountry.setIsDelete(CommonStringUtils.STATUS_11);
        	Map<String, Object> uploadResultMap = null;
        	
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.CATEGORY_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						trendCountry.setFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
            trendCountryService.insert(trendCountry);
            LOGGER.info("用户【{}】添加国家表数据【{}】成功", new Object[] { this.getUserid(), trendCountry } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加国家表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), trendCountry, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【国家表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Integer countryId) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("countryId", countryId); 
        model.addAttribute("trendCountry", trendCountryService.selectObject(map));
        return "trend/country.edit";
    }
    
    /**
     * 修改【国家表】数据
     * @param trendCountry
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editTrendCountry(@ModelAttribute TrendCountry trendCountry,
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	
        	Map<String, Object> uploadResultMap = null;
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.CATEGORY_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						trendCountry.setFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
            trendCountryService.update(trendCountry);
            LOGGER.info("用户【{}】修改国家表数据【{}】成功", new Object[] { this.getUserid(), trendCountry } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改国家表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), trendCountry, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【国家表】数据
     * @param countryId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-24 14:50:30
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteTrendCountry(Integer countryId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("countryId", countryId); 
            trendCountryService.delete(map);
            LOGGER.info("用户【{}】删除国家表数据【{}】成功", new Object[] { this.getUserid(), countryId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除国家表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), countryId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
