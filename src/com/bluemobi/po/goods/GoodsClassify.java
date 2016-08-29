package com.bluemobi.po.goods;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【商品分类表】持久化对象 数据库表：goods_classify
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-23 18:06:29
 * 
 */
public class GoodsClassify extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 分类ID
    private Long classifyId;
    // 分类名称
    private String classifyName;
    // 排序
    private Integer classifySort;
    // 类型： 11：平台添加，00：商家申请
    private String type;
    // 是否删除。11：未删除，00：已删除
    private String isDelete;
    // 创建时间
    private Date ctime;

    /** 获取 分类ID 属性 */
    public Long getClassifyId() {
        return classifyId;
    }

    /** 设置 分类ID 属性 */
    public void setClassifyId(Long classifyId) {
        this.classifyId = classifyId;
    }

    /** 获取 分类名称 属性 */
    public String getClassifyName() {
        return classifyName;
    }

    /** 设置 分类名称 属性 */
    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    /** 获取 排序 属性 */
    public Integer getClassifySort() {
        return classifySort;
    }

    /** 设置 排序 属性 */
    public void setClassifySort(Integer classifySort) {
        this.classifySort = classifySort;
    }

    /** 获取 类型： 11：平台添加，00：商家申请 属性 */
    public String getType() {
        return type;
    }

    /** 设置 类型： 11：平台添加，00：商家申请 属性 */
    public void setType(String type) {
        this.type = type;
    }

    /** 获取 是否删除。11：未删除，00：已删除 属性 */
    public String getIsDelete() {
        return isDelete;
    }

    /** 设置 是否删除。11：未删除，00：已删除 属性 */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    /** 获取 创建时间 属性 */
    public Date getCtime() {
        return ctime;
    }

    /** 设置 创建时间 属性 */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GoodsClassify");
        sb.append("{classifyId=").append(classifyId);
        sb.append(", classifyName=").append(classifyName);
        sb.append(", classifySort=").append(classifySort);
        sb.append(", type=").append(type);
        sb.append(", isDelete=").append(isDelete);
        sb.append(", ctime=").append(ctime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GoodsClassify) {
            GoodsClassify goodsClassify = (GoodsClassify) obj;
            if (this.getClassifyId().equals(goodsClassify.getClassifyId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getClassifyId();
        return pkStr.hashCode();
    }

}