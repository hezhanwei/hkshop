package com.bluemobi.serviceimpl.comment;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.comment.CommentContentDao;
import com.bluemobi.po.comment.CommentContent;
import com.bluemobi.service.comment.CommentContentService;
import com.bluemobi.to.comment.AddGoodsCommentTO;

/**
 * 【】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-21 14:55:28
 * 
 */
@Service(value = "commentContentService")
public class CommentContentServiceImpl extends MybatisBaseServiceImpl implements CommentContentService {

    @Autowired
    private CommentContentDao commentContentDao;

    @Override
    public MyBatisBaseDao getDao() {
        return commentContentDao;
    }

    @Override
    public void deleteLogic(String id) {
        String[] strs = id.split(",");
        //循环修改状态(是否标记为删除。1：是；0：否；)6
        for (int i = 0; i < strs.length; i++) {
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("id", Integer.parseInt(strs[i]));
            CommentContent commentContent = commentContentDao.selectObject(reqMap);
            commentContent.setIsDel((byte)1);
            commentContentDao.update(commentContent);
        }
        
    }

    public List<CommentContent> getCommentContentList(int categoryId, int toId) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("categoryId", categoryId);
        reqMap.put("toId", toId);
        List<CommentContent> commentContentList = commentContentDao.getCommentContentList(reqMap);
        return commentContentList;
    }

    @Override
    public void saveCommentContentToApi(AddGoodsCommentTO addGoodsCommentTO) {
        //1，数据重组
        CommentContent commentContent = new CommentContent();
        
        commentContent.setCategoryId(addGoodsCommentTO.getCategoryId());
        commentContent.setContent(addGoodsCommentTO.getContent());
        commentContent.setCtime(new Date());
        commentContent.setMtime(new Date());
        commentContent.setRankBase(addGoodsCommentTO.getRankBase().byteValue());
        commentContent.setRankLogistics(addGoodsCommentTO.getRankLogistics().byteValue());
        commentContent.setRankSpeed(addGoodsCommentTO.getRankSpeed().byteValue());
        commentContent.setUserid(addGoodsCommentTO.getUserId());
        commentContent.setToId(addGoodsCommentTO.getToId());
        commentContent.setPid(addGoodsCommentTO.getPid());
        commentContent.setTitle(addGoodsCommentTO.getTitle());
        commentContent.setToOrderItemId(addGoodsCommentTO.getToOrderItemId());
        commentContent.setToStoreId(addGoodsCommentTO.getToStoreId());
        
        //2，持久化对象
        commentContentDao.insert(commentContent);
        
    }

}
