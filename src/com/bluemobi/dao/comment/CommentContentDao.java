package com.bluemobi.dao.comment;

import java.util.List;
import java.util.Map;

import com.appcore.dao.MyBatisBaseDao;
import com.bluemobi.po.comment.CommentContent;

/**
 * 【】 数据访问对象 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-21 14:55:28
 * 
 */
public interface CommentContentDao extends MyBatisBaseDao {

    /**
     * 获得所有评论
     * @author HeWeiwen
     * 2015-10-26
     * @param map
     * @return
     */
    List<CommentContent> getCommentContentList(Map<String, Object> map);
    
    /**
     * 条件查询评论分页信息
     * @author HeWeiwen
     * 2015-10-26
     * @param map
     * @return
     */
    List<Map<String, Object>> pageCommentContent(Map<String, Object> map);

    /**
     * 分页总条数
     * @author HeWeiwen
     * 2015-10-26
     * @param map
     * @return
     */
    int pageCommentContentCount(Map<String, Object> map);
}
