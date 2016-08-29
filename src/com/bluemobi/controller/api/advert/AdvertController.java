package com.bluemobi.controller.api.advert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluemobi.controller.AbstractAPIController;
import com.bluemobi.service.advert.AdvertBoardService;
import com.bluemobi.service.advert.AdvertContentService;
import com.bluemobi.service.advert.AdvertPageService;
import com.bluemobi.to.ResultTO;

/**
 * 广告模块接口
 * 
 * @author zhangzheng
 * @date 2015-10-15
 * 
 */
@Controller
@RequestMapping("api/advert")
public class AdvertController extends AbstractAPIController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdvertController.class);

    @Autowired
    private AdvertContentService advertContentService;
    @Autowired
    private AdvertBoardService advertBoardService;
    @Autowired
    private AdvertPageService advertPageService;

    /**
     * 跟据广告页ID和广告位ID查询所有广告信息接口
     * 
     * @auther zhangzheng
     * @date 2015-10-15 下午5:14:03
     * @param pageId
     *            广告页ID
     * @param boardId
     *            广告位ID
     * @return
     */
    @RequestMapping(value = "getAdvertContentList", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getAdvertContentList(Integer pageId, Integer boardId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userid = this.getUserid();
        if (pageId != null) {
            map.put("pageId", pageId);
        }
        if (boardId != null) {
            map.put("boardId", boardId);
        }
        List<Map<String, Object>> list = advertContentService.selectMapList(map);
        LOGGER.info("用户【{}】查询广告信息", new Object[] { userid });
        return ResultTO.newSuccessResultTO("查询广告信息成功", list);
    }

    /**
     * 跟据广告页ID查询所有广告位信息接口
     * 
     * @auther zhangzheng
     * @date 2015-10-15 下午5:14:38
     * @param pageId
     *            广告页ID
     * @return
     */
    @RequestMapping(value = "getAdvertBoardList", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getAdvertBoardList(Integer pageId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userid = this.getUserid();
        if (pageId != null) {
            map.put("pageId", pageId);
        }
        List<Map<String, Object>> list = advertBoardService.selectMapList(map);
        LOGGER.info("用户【{}】查询广告位信息", new Object[] { userid });
        return ResultTO.newSuccessResultTO("查询广告位信息成功", list);
    }

    /**
     * 查询所有广告页信息接口
     * 
     * @auther zhangzheng
     * @date 2015-10-15 下午5:10:13
     * @return
     */
    @RequestMapping(value = "getAdvertPageList", method = RequestMethod.POST)
    @ResponseBody
    public ResultTO getAdvertPageList() {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer userid = this.getUserid();
        List<Map<String, Object>> list = advertPageService.selectMapList(map);
        LOGGER.info("用户【{}】查询广告页信息", new Object[] { userid });
        return ResultTO.newSuccessResultTO("查询广告页信息成功", list);
    }

}
