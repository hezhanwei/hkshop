package com.bluemobi.po.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aspectj.apache.bcel.generic.NEW;

import com.appcore.model.AbstractObject;

/**
 * 【商品分类表】持久化对象 数据库表：goods_category
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-10-22 15:18:57
 * 
 */
public class GoodsCategory extends AbstractObject {

    public static final long serialVersionUID = 1L;

    //
    private Integer categoryId;
    // 父级ID
    private Integer parentId;
    // 分类节点路径，以冒号进行分隔，比如：0:1:24
    private String hid;
    // 分类名称
    private String categoryName;
    // 广告ID
    private Integer adId;
    // 介绍
    private String description;
    // 是否启用。1：是；0：否；
    private Byte status;
    // 序号
    private Integer sortOrder;
    // 创建时间
    private Date ctime;
    // 最后一次更新时间
    private Date mtime;
    
    /**
     * 当前分类的 子集分类list
     * @author haojian
     * @date 2015-12-4 下午12:39:00 
     */
    private List<GoodsCategory> subList = new ArrayList<GoodsCategory>(); ;

    /** 获取 属性 */
    public Integer getCategoryId() {
        return categoryId;
    }

    /** 设置 属性 */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /** 获取 父级ID 属性 */
    public Integer getParentId() {
        return parentId;
    }

    /** 设置 父级ID 属性 */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /** 获取 分类节点路径，以冒号进行分隔，比如：0:1:24 属性 */
    public String getHid() {
        return hid;
    }

    /** 设置 分类节点路径，以冒号进行分隔，比如：0:1:24 属性 */
    public void setHid(String hid) {
        this.hid = hid;
    }

    /** 获取 分类名称 属性 */
    public String getCategoryName() {
        return categoryName;
    }

    /** 设置 分类名称 属性 */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /** 获取 广告ID 属性 */
    public Integer getAdId() {
        return adId;
    }

    /** 设置 广告ID 属性 */
    public void setAdId(Integer adId) {
        this.adId = adId;
    }

    /** 获取 介绍 属性 */
    public String getDescription() {
        return description;
    }

    /** 设置 介绍 属性 */
    public void setDescription(String description) {
        this.description = description;
    }

    /** 获取 是否启用。1：是；0：否； 属性 */
    public Byte getStatus() {
        return status;
    }

    /** 设置 是否启用。1：是；0：否； 属性 */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /** 获取 序号 属性 */
    public Integer getSortOrder() {
        return sortOrder;
    }

    /** 设置 序号 属性 */
    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    /** 获取 创建时间 属性 */
    public Date getCtime() {
        return ctime;
    }

    /** 设置 创建时间 属性 */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /** 获取 最后一次更新时间 属性 */
    public Date getMtime() {
        return mtime;
    }

    /** 设置 最后一次更新时间 属性 */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }
    
    public List<GoodsCategory> getSubList() {
        return subList;
    }

    public void setSubList(List<GoodsCategory> subList) {
        this.subList = subList;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GoodsCategory");
        sb.append("{categoryId=").append(categoryId);
        sb.append(", parentId=").append(parentId);
        sb.append(", hid=").append(hid);
        sb.append(", categoryName=").append(categoryName);
        sb.append(", adId=").append(adId);
        sb.append(", description=").append(description);
        sb.append(", status=").append(status);
        sb.append(", sortOrder=").append(sortOrder);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GoodsCategory) {
            GoodsCategory goodsCategory = (GoodsCategory) obj;
            if (this.getCategoryId().equals(goodsCategory.getCategoryId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getCategoryId();
        return pkStr.hashCode();
    }

}