package com.bluemobi.po.goods;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【推荐导航表】持久化对象 数据库表：goods_recommend_navigation
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 11:33:37
 * 
 */
public class GoodsRecommendNavigation extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 推荐导航id
    private Long navigationId;
    // 导航图名称
    private String navigationName;
    // 图片路径
    private String navigationFilepath;
    // 跳转url
    private String urlLink;
    // 排序
    private Integer sort;
    // 是否删除。11：未删除，00：已删除
    private String isDelete;
    // 
    private Date ctime;

    /** 获取 推荐导航id 属性 */
    public Long getNavigationId() {
        return navigationId;
    }

    /** 设置 推荐导航id 属性 */
    public void setNavigationId(Long navigationId) {
        this.navigationId = navigationId;
    }

    /** 获取 导航图名称 属性 */
    public String getNavigationName() {
        return navigationName;
    }

    /** 设置 导航图名称 属性 */
    public void setNavigationName(String navigationName) {
        this.navigationName = navigationName;
    }

    /** 获取 图片路径 属性 */
    public String getNavigationFilepath() {
        return navigationFilepath;
    }

    /** 设置 图片路径 属性 */
    public void setNavigationFilepath(String navigationFilepath) {
        this.navigationFilepath = navigationFilepath;
    }

    /** 获取 跳转url 属性 */
    public String getUrlLink() {
        return urlLink;
    }

    /** 设置 跳转url 属性 */
    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    /** 获取 排序 属性 */
    public Integer getSort() {
        return sort;
    }

    /** 设置 排序 属性 */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /** 获取 是否删除。11：未删除，00：已删除 属性 */
    public String getIsDelete() {
        return isDelete;
    }

    /** 设置 是否删除。11：未删除，00：已删除 属性 */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /** 获取  属性 */
    public Date getCtime() {
        return ctime;
    }

    /** 设置  属性 */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GoodsRecommendNavigation");
        sb.append("{navigationId=").append(navigationId);
        sb.append(", navigationName=").append(navigationName);
        sb.append(", navigationFilepath=").append(navigationFilepath);
        sb.append(", urlLink=").append(urlLink);
        sb.append(", sort=").append(sort);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", ctime=").append(ctime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GoodsRecommendNavigation) {
            GoodsRecommendNavigation goodsRecommendNavigation = (GoodsRecommendNavigation) obj;
            if (this.getNavigationId().equals(goodsRecommendNavigation.getNavigationId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getNavigationId();
        return pkStr.hashCode();
    }

}