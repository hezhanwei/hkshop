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

import com.alibaba.dubbo.remoting.exchange.Request;
import com.appcore.page.Page;
import com.bluemobi.constant.BaseConstant;
import com.bluemobi.constant.GoodsConstant;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.goods.GoodsClassify;
import com.bluemobi.po.goods.GoodsRecommend;
import com.bluemobi.po.trend.TrendAttachment;
import com.bluemobi.service.goods.GoodsClassifyService;
import com.bluemobi.service.goods.GoodsRecommendService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.util.CommonStringUtils;



/**
 * 【推荐表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-19 13:09:49
 * 
 */
@Controller
@RequestMapping({
	"goodsRecommend",
	"goodsOnsale",
	"goodsSecondRec",
	"goodsFirstRec",
	"secondSaleRec"
})
public class GoodsRecommendController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsRecommendController.class);
    
    @Autowired
	private HttpServletRequest request;
    
    @Autowired
    private GoodsRecommendService goodsRecommendService;
    @Autowired
    private GoodsClassifyService goodsClassifyService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【推荐表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model,String type,HttpSession session) {
        // 加载公共数据
        initIndex(model);
        if (!StringUtils.isBlank(type)) {
        	session.setAttribute("recommend_type", type);
        	
        	if (CommonStringUtils.STATUS_44.equals(type)) {
        		return "mall/recommend.index";
			}else{
				return "mall/recommend.index.hot";
			}
		}
        
        return "mall/recommend.index";
    }
    
    /**
     * 分页查询【推荐表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex,HttpSession session) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recommendType", session.getAttribute("recommend_type"));
        map.put("key", key);
        Page<Map<String, Object>> page = goodsRecommendService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【推荐表】详情
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Long recommendId,HttpSession session) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("recommendId", recommendId); 
        model.addAttribute("goodsRecommend", goodsRecommendService.selectObject(map));
        
        if (session.getAttribute("recommend_type")!=null) {
			String type = (String)session.getAttribute("recommend_type");
			if (CommonStringUtils.STATUS_44.equals(type)) {
        		return "mall/recommend.detail";
			}else{
				return "mall/recommend.detail.hot";
			}
		}
        return "mall/recommend.detail";
    }
    
    /**
     * 进入新增【推荐表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        if (request.getSession().getAttribute("recommend_type")!=null) {
			String type = (String)request.getSession().getAttribute("recommend_type");
			if (CommonStringUtils.STATUS_44.equals(type)) {
        		return "mall/recommend.edit";
			}else{
				return "mall/recommend.edit.hot";
			}
		}
        return "mall/recommend.edit";
    }
    
    /**
     * 新增【推荐表】数据
     * @param goodsRecommend
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addGoodsRecommend(@ModelAttribute GoodsRecommend goodsRecommend,
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,HttpSession session) {
    	if (session.getAttribute("recommend_type")!=null) {
    		goodsRecommend.setRecommendType((String)session.getAttribute("recommend_type"));
		}
    	Map<String, Object> uploadResultMap = null;
    	goodsRecommend.setCreateBy(getUsername());//创建人
    	goodsRecommend.setCtime(new Date());//创建时间
    	try {
        	try {
				if (files!=null && files.length>0) {
					uploadResultMap = uploadImage(files, GoodsConstant.CATEGORY_IMAGE);//上传图片
					if ((Boolean) uploadResultMap.get("flag")) {//上传成功
						goodsRecommend.setFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
						LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
					}
				}
			} catch (Exception e) {
				LOGGER.error("用户【{}】上传图片失败。Exception:【{}】", new Object[] { this.getUserid(), e });
			}
        	
            goodsRecommendService.insertGoodsRecommend(goodsRecommend);
            
            LOGGER.info("用户【{}】添加推荐表数据【{}】成功", new Object[] { this.getUserid(), goodsRecommend } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加推荐表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsRecommend, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    
    /**
     * 进入修改【推荐表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Long recommendId) {
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("recommendId", recommendId); 
        GoodsRecommend goodsRecommend = goodsRecommendService.selectObject(map);
        // 加载公共数据
        initIndex(model);
        model.addAttribute("goodsRecommend", goodsRecommend);
        
        if (request.getSession().getAttribute("recommend_type")!=null) {
			String type = (String)request.getSession().getAttribute("recommend_type");
			if (CommonStringUtils.STATUS_44.equals(type)) {
        		return "mall/recommend.edit";
			}else{
				return "mall/recommend.edit.hot";
			}
		}
        return "mall/recommend.edit";
    }
    
    /**
     * 修改【推荐表】数据
     * @param goodsRecommend
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editGoodsRecommend(@ModelAttribute GoodsRecommend goodsRecommend, 
    		@RequestParam(value = "businessImageFile", required = false) MultipartFile[] files,
    		BindingResult result) {
        try {
        	Map<String, Object> uploadResultMap = null;
        	if (files!=null && files.length>0) {
				uploadResultMap = uploadImage(files, GoodsConstant.GOODS_CONTENT_IMAGE);//上传图片
				if ((Boolean) uploadResultMap.get("flag")) {//上传成功
					goodsRecommend.setFilepath( uploadResultMap.get("imageUrl").toString());//文件路径
					LOGGER.info("用户【{}】上传图片【{}】成功", new Object[] { this.getUserid()});
				}
			}
        	
            goodsRecommendService.update(goodsRecommend);
            LOGGER.info("用户【{}】修改推荐表数据【{}】成功", new Object[] { this.getUserid(), goodsRecommend } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改推荐表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), goodsRecommend, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【推荐表】数据
     * @param recommendId
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-19 13:09:49
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteGoodsRecommend(Integer recommendId) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("recommendId", recommendId); 
            goodsRecommendService.delete(map);
            LOGGER.info("用户【{}】删除推荐表数据【{}】成功", new Object[] { this.getUserid(), recommendId });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除推荐表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), recommendId, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
}
