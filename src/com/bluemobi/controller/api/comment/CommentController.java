package com.bluemobi.controller.api.comment;

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
import com.bluemobi.po.comment.CommentCategory;
import com.bluemobi.po.comment.CommentContent;
import com.bluemobi.service.comment.CommentCategoryService;
import com.bluemobi.service.comment.CommentContentService;
import com.bluemobi.to.ResultTO;
import com.bluemobi.to.comment.AddGoodsCommentTO;


/**
 * 评论模块 控制器
 * @author heweiwen
 * 2015-10-22 上午11:31:39
 */
@Controller
@RequestMapping("api/comment")
public class CommentController extends AbstractAPIController {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentContentService commentContentService;
    @Autowired
    private CommentCategoryService commentCategoryService;

	/**
	 * 获取评论分类
	 * @author HeWeiwen
	 * 2015-10-22
	 * @return
	 */
    @RequestMapping(value = "getCategory", method = RequestMethod.POST)
    @ResponseBody
	public ResultTO getCategory() {
		Map<String,Object> reqMap = new HashMap<String, Object>();
		List<CommentCategory> resultData = commentCategoryService.selectObjectList(reqMap);
		if (resultData.size() < 0 ) {
			return ResultTO.newFailResultTO("查询不到数据", null);
		}
		return ResultTO.newSuccessResultTO("获得评论成功", resultData);
	}
	 
	/**
	 * 获取评论
	 * @author HeWeiwen
	 * 2015-10-22
	 * @param categoryId : 评论分类 ID
     * @param toId : 评论所针对的 ID
	 * @return
	 */
    @RequestMapping(value = "getListGoods", method = RequestMethod.POST)
    @ResponseBody
	public ResultTO getListGoods(Integer categoryId,Integer toId){
		LOGGER.info("请求参数【{}】", "("+categoryId+"),("+toId+")");
		if (categoryId == null && toId == null) {
			return ResultTO.newFailResultTO("查询参数为空", null);
		}
		//校验数据
		validataToGetListGoods(categoryId,toId);
		
		List<CommentContent> resultData = commentContentService.getCommentContentList(categoryId,toId);
		if (resultData.size() < 0) {
			return ResultTO.newFailResultTO("查询不到数据", null);
		}
		return ResultTO.newSuccessResultTO("获得评论成功", resultData);
	}



	/**
	 * 添加商品评论
	 * @author AutoCode E-mail:309444359@qq.com
	 * @date 2015-05-16 16:55:05 
	 * @param to
	 * @return Object
	 */
    @RequestMapping(value = "addGoods", method = RequestMethod.POST)
    @ResponseBody
	public ResultTO addGoods(AddGoodsCommentTO addGoodsCommentTO){
		LOGGER.info("添加商品评论,请求参数【{}】", addGoodsCommentTO);
		if (addGoodsCommentTO == null) {
			return ResultTO.newFailResultTO("查询参数为空", null);
		}
		//校验数据
		validataToAddGoods(addGoodsCommentTO);
		//获得userid并set对象
		addGoodsCommentTO.setUserId(this.getUserid());
		try {
			commentContentService.saveCommentContentToApi(addGoodsCommentTO);
		} catch (Exception e) {
			LOGGER.error("Exception:"+e);
			return ResultTO.newFailResultTO("添加失败", null);
		}
		return ResultTO.newSuccessResultTO("添加成功", addGoodsCommentTO);
		
	}
	
	/**
	 * 校验添加商品评论数据
	 * @author HeWeiwen
	 * 2015-10-22
	 * @param to
	 * @return
	 */
	public Object validataToAddGoods(AddGoodsCommentTO to){
		if (to.getCategoryId() == null) {
			return ResultTO.newFailResultTO("参数 CategoryId未提供", null);
		}
		if (to.getToId() == null) {
			return ResultTO.newFailResultTO("参数 ToId未提供", null);
        }
        
    	if (to.getToOrderItemId() == null) {
            return ResultTO.newFailResultTO("参数 ToOrderItemId未提供", null);
        }
        
    	if (to.getToStoreId() == null) {
            return ResultTO.newFailResultTO("参数 ToStoreId未提供", null);
        }
        
    	if (to.getContent() == null) {
            return ResultTO.newFailResultTO("参数 content未提供", null);
        }
        
		return ResultTO.newSuccessResultTO("", null);
	}

	/**
	 * 校验获取评论数据
	 * @author HeWeiwen
	 * 2015-10-22
	 * @param categoryId : 评论分类 ID
	 * @param toId : 评论所针对的 ID
	 * @return
	 */
	public Object validataToGetListGoods(Integer categoryId,Integer toId){
		if (categoryId == null) {
			return ResultTO.newFailResultTO("参数 categoryId未提供", null);
		}
		if (toId == null) {
			return ResultTO.newFailResultTO("参数 toId未提供", null);
        }
		return ResultTO.newSuccessResultTO("", null);
	}

}
