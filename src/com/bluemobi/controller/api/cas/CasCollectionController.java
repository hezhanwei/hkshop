package com.bluemobi.controller.api.cas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.cas.CasCollection;
import com.bluemobi.po.goods.GoodsContent;
import com.bluemobi.po.store.StoreContent;
import com.bluemobi.service.cas.CasCollectionService;
import com.bluemobi.service.goods.GoodsContentService;
import com.bluemobi.service.store.StoreContentService;
import com.bluemobi.to.ResultTO;



/**
 * 【收藏表】控制器
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 16:07:12
 * 
 */
@Controller
@RequestMapping("api/casCollection")
public class CasCollectionController extends AbstractWebController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CasCollectionController.class);
    
    @Autowired
    private CasCollectionService casCollectionService;
    
    @Autowired
    private StoreContentService storeContentService;
    @Autowired
    private GoodsContentService goodsContentService;
    

    /**
     * 将请求参数中的字符串转换成日期格式
     * @param request
     * @param binder
     * @return string
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//请求参数中的日期字符串格式
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }
    
    /**
     * 进入【收藏表】主页
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "cas/collection.index";
    }
    
    /**
     * 分页查询【收藏表】
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @return Page<Map<String,Object>>
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Page<Map<String, Object>> page(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> page = casCollectionService.page(map,pageIndex, pageSize);
        return page;
    }
    
    /**
     * 查询【收藏表】详情
     * @param model
     * @return string                                                                                                                         
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String detail(Model model, Integer collectionid) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("collectionid", collectionid); 
        model.addAttribute("casCollection", casCollectionService.selectObject(map));
        return "cas/collection.detail";
    }
    
    /**
     * 进入新增【收藏表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        // 加载公共数据
        initIndex(model);
        return "cas/collection.edit";
    }
    
    /**
     * 新增【收藏表】数据
     * @param casCollection
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO addCasCollection(@ModelAttribute CasCollection casCollection, BindingResult result) {
        try {
            casCollectionService.insert(casCollection);
            LOGGER.info("用户【{}】添加收藏表数据【{}】成功", new Object[] { this.getUserid(), casCollection } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】添加收藏表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), casCollection, e });
            return ResultTO.newFailResultTO("添加失败", null);
        }
        return ResultTO.newSuccessResultTO("添加成功", null);
    }
    
    /**
     * 进入修改【收藏表】页面
     * @param model
     * @return string
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "edit", method = RequestMethod.GET)
    public String edit(Model model, Integer collectionid) {
        // 加载公共数据
        initIndex(model);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("collectionid", collectionid); 
        model.addAttribute("casCollection", casCollectionService.selectObject(map));
        return "cas/collection.edit";
    }
    
    /**
     * 修改【收藏表】数据
     * @param casCollection
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO editCasCollection(@ModelAttribute CasCollection casCollection, BindingResult result) {
        try {
            casCollectionService.update(casCollection);
            LOGGER.info("用户【{}】修改收藏表数据【{}】成功", new Object[] { this.getUserid(), casCollection } );
        } catch (Exception e) {
            LOGGER.error("用户【{}】修改收藏表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), casCollection, e });
            return ResultTO.newFailResultTO("更新失败", null);
        }
        return ResultTO.newSuccessResultTO("更新成功", null);
    }
    
    /**
     * 删除【收藏表】数据
     * @param collectionid
     * @return ResultTO
     * @author AutoCode
     * @date 2016-08-11 16:07:12
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO deleteCasCollection(Integer collectionid) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        try {
            map.put("collectionid", collectionid); 
            casCollectionService.delete(map);
            LOGGER.info("用户【{}】删除收藏表数据【{}】成功", new Object[] { this.getUserid(), collectionid });
        } catch (Exception e) {
            LOGGER.error("用户【{}】删除收藏表数据【{}】失败 Exception:【{}】", new Object[] { this.getUserid(), collectionid, e });
            return ResultTO.newFailResultTO("删除失败", null);
        }
        return ResultTO.newSuccessResultTO("删除成功", null);
    }
    
    /**
     * 获取收藏列表
     * @param collectionType
     * @return
     */
    @RequestMapping(value = "getCasCollection", method = RequestMethod.GET)
    @ResponseBody
	public ResultTO getCasCollection(Integer collectionType){
		Map<String, Integer> map = new HashMap<>();
		map.put("userid", getUserid());
		map.put("collectionType", collectionType);
		List<CasCollection> casCollections = casCollectionService.selectObjectList(map);//查询收藏表
		
		List<Integer> ids = new ArrayList<>();
		
		if (casCollections != null && casCollections.size() > 0){
			for (CasCollection ics : casCollections) {
				ids.add(ics.getStoreorgoodsId());
			}
			if (collectionType==0) {//查询收藏店铺列表
				List<StoreContent> storeContents = storeContentService.selectGoodsListByContentids(ids);
				return ResultTO.newSuccessResultTO("店铺列表", storeContents);
			}else{//查询收藏商品列表
				List<GoodsContent> goodsContents= goodsContentService.selectGoodsListByContentids(ids);
				return ResultTO.newSuccessResultTO("商品列表", goodsContents);
			}
		}
		return ResultTO.newSuccessResultTO("暂无收藏", null);
	}
    
    /**
     * 添加收藏
     * @param collectionType
     * @param storeorgoodsId
     */
    @ResponseBody
    @RequestMapping(value="addCasCollection", method = RequestMethod.GET)
    public void addCasCollection(@RequestParam("type")Integer collectionType,
    		@RequestParam("id")Integer storeorgoodsId){
    	
    	CasCollection casCollection = new CasCollection();
    	casCollection.setCollectionType((byte)collectionType.intValue());
    	casCollection.setCtime(new Date());
    	casCollection.setMtime(new Date());
    	casCollection.setStoreorgoodsId(storeorgoodsId);
    	casCollection.setUserid((long)getUserid());
    	int result = casCollectionService.insert(casCollection);//添加
    	if (result>0) {
    		if (collectionType==0) {
    			storeContentService.updateStoreCollectionNum(storeorgoodsId);//更新店铺收藏次数
			}else{
				goodsContentService.updateCollectionNum(storeorgoodsId);//更新商家收藏次数
			}
		}
    	
    }
    
}
