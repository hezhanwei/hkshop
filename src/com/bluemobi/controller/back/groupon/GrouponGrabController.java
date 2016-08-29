package com.bluemobi.controller.back.groupon;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appcore.page.Page;
import com.bluemobi.controller.AbstractWebController;
import com.bluemobi.po.groupon.GrouponGrab;
import com.bluemobi.service.groupon.GrouponCategoryService;
import com.bluemobi.service.groupon.GrouponGrabService;
import com.bluemobi.to.ResultTO;

/**
 * 抢购活动管理模块 控制器
 * 
 * @ClassName GrouponGrabController
 * @author liuyt
 * @date 2015-10-23 下午2:50:51
 * @version
 */
@Controller
@RequestMapping("grouponGrab")
public class GrouponGrabController extends AbstractWebController {

    @Autowired
    private GrouponGrabService grouponGrabService;

    @Autowired
    private GrouponCategoryService grouponCategoryService;

    @InitBinder
    protected void initBinder(HttpServletRequest request,
            ServletRequestDataBinder binder) throws Exception {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        CustomDateEditor editor = new CustomDateEditor(df, false);
        binder.registerCustomEditor(Date.class, editor);
    }

    /**
     * 初始化列表页
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:53:25
     * @param model
     * @param request
     * @return
     * @version
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(Model model) {
        // 加载公共数据
        initIndex(model);
        return "groupon/grab.index";
    }

    /**
     * 分页获取抢购活动列表
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:52:23
     * @param key
     * @param pageSize
     * @param pageIndex
     * @return
     * @version
     */
    @RequestMapping(value = "page", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getPage(String key, int pageSize, int pageIndex) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("key", key);
        Page<Map<String, Object>> pages = grouponGrabService.page(map,
                pageIndex, pageSize);

        Map<String, Object> mapResult = new HashMap<String, Object>();
        mapResult.put("data", pages.getData());
        mapResult.put("count", pages.getCount());
        return mapResult;
    }

    /**
     * 查看抢购活动详情
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:52:47
     * @param model
     * @param grabId
     * @return
     * @version
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public String getDetail(Model model, int grabId) {
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("bulkId", grabId);
        model.addAttribute("grab", grouponGrabService.selectObject(map));
        model.addAttribute("categorys",
                grouponCategoryService.selectObjectList(null));
        // 加载公共数据
        initIndex(model);
        return "groupon/grab.detail";
    }

    /**
     * 初始化添加页面
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:53:18
     * @param model
     * @param req
     * @return
     * @version
     */
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String grouponGrabAdd(Model model) {
        // 加载公共数据
        initIndex(model);
        model.addAttribute("categorys",
                grouponCategoryService.selectObjectList(null));
        return "groupon/grab.edit";
    }

    /**
     * 初始化修改页面
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:53:53
     * @param model
     * @param grabId
     * @param req
     * @return
     * @version
     */
    @RequestMapping( value = "edit", method = RequestMethod.GET)
    public String grouponGrabEdit(Model model, int grabId,
            HttpServletRequest req) {
        // 加载公共数据
        initIndex(model);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("bulkId", grabId);
        model.addAttribute("grab", grouponGrabService.selectObject(map));
        model.addAttribute("categorys",
                grouponCategoryService.selectObjectList(null));
        return "groupon/grab.edit";

    }

    /**
     * 保存操作
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:54:44
     * @param grab
     * @param categoryIds
     * @return
     * @version
     */
    @RequestMapping(value = {"add", "edit"}, method = RequestMethod.POST)
    @ResponseBody
    public ResultTO saveGrouponBulk(GrouponGrab grab, Integer[] categoryIds) {
        return grouponGrabService.saveGrouponGrab(grab, categoryIds);
    }

    /**
     * 删除活动(只能删除未开始的活动)
     * 
     * @author liuyt
     * @date 2015-10-23 下午2:55:15
     * @param grabId
     * @return
     * @version
     */
    @RequestMapping( value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO delGrouponBulk(Integer grabId) {
        return grouponGrabService.deleteGrab(grabId);
    }

    
}
