package com.bluemobi.po.cms;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【平台文章表】持久化对象 数据库表：cms_article
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-29 14:29:33
 * 
 */
public class CmsArticle extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 文章ID
    private Integer articleId;
    // 作者
    private String author;
    // 标题
    private String title;
    // 标题标识
    private String titleCode;
    // 内容
    private String content;
    // 类型。00：用户端，11：商户端
    private String type;
    // 状态：00显示，11隐藏
    private String status;
    // 创建时间
    private Date ctime;
    // 创建人
    private String createBy;
    // 修改时间
    private Date mtime;
    // 修改人
    private String updateBy;

    /** 获取 文章ID 属性 */
    public Integer getArticleId() {
        return articleId;
    }

    /** 设置 文章ID 属性 */
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    /** 获取 作者 属性 */
    public String getAuthor() {
        return author;
    }

    /** 设置 作者 属性 */
    public void setAuthor(String author) {
        this.author = author;
    }

    /** 获取 标题 属性 */
    public String getTitle() {
        return title;
    }

    /** 设置 标题 属性 */
    public void setTitle(String title) {
        this.title = title;
    }

    /** 获取 标题标识 属性 */
    public String getTitleCode() {
        return titleCode;
    }

    /** 设置 标题标识 属性 */
    public void setTitleCode(String titleCode) {
        this.titleCode = titleCode;
    }

    /** 获取 内容 属性 */
    public String getContent() {
        return content;
    }

    /** 设置 内容 属性 */
    public void setContent(String content) {
        this.content = content;
    }

    /** 获取 类型。00：用户端，11：商户端 属性 */
    public String getType() {
        return type;
    }

    /** 设置 类型。00：用户端，11：商户端 属性 */
    public void setType(String type) {
        this.type = type;
    }

    /** 获取 状态：00显示，11隐藏 属性 */
    public String getStatus() {
        return status;
    }

    /** 设置 状态：00显示，11隐藏 属性 */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 获取 创建时间 属性 */
    public Date getCtime() {
        return ctime;
    }

    /** 设置 创建时间 属性 */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /** 获取 创建人 属性 */
    public String getCreateBy() {
        return createBy;
    }

    /** 设置 创建人 属性 */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /** 获取 修改时间 属性 */
    public Date getMtime() {
        return mtime;
    }

    /** 设置 修改时间 属性 */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /** 获取 修改人 属性 */
    public String getUpdateBy() {
        return updateBy;
    }

    /** 设置 修改人 属性 */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CmsArticle");
        sb.append("{articleId=").append(articleId);
        sb.append(", author=").append(author);
        sb.append(", title=").append(title);
        sb.append(", titleCode=").append(titleCode);
        sb.append(", content=").append(content);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", ctime=").append(ctime);
        sb.append(", createBy=").append(createBy);
        sb.append(", mtime=").append(mtime);
        sb.append(", updateBy=").append(updateBy);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CmsArticle) {
            CmsArticle cmsArticle = (CmsArticle) obj;
            if (this.getArticleId().equals(cmsArticle.getArticleId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getArticleId();
        return pkStr.hashCode();
    }

}