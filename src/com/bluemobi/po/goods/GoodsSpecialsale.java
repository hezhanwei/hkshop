package com.bluemobi.po.goods;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【今日特卖分类表】持久化对象 数据库表：goods_specialsale
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 10:45:02
 * 
 */
public class GoodsSpecialsale extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 特卖ID
    private Long specialsaleId;
    // 分类ID
    private Long classifyId;
    // 分类名称
    private String classifyName;
    // 图片路径
    private String filepath;
    // 排序
    private Integer sort;
    // 状态类型. 00 商品分类，11 国家分类
    private String status;
    // 创建人
    private String createBy;
    // 
    private Date ctime;
    // 开始时间
    private Date beginTime;
    // 结束时间
    private Date endTime;

    /** 获取 特卖ID 属性 */
    public Long getSpecialsaleId() {
        return specialsaleId;
    }

    /** 设置 特卖ID 属性 */
    public void setSpecialsaleId(Long specialsaleId) {
        this.specialsaleId = specialsaleId;
    }

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

    /** 获取 图片路径 属性 */
    public String getFilepath() {
        return filepath;
    }

    /** 设置 图片路径 属性 */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /** 获取 排序 属性 */
    public Integer getSort() {
        return sort;
    }

    /** 设置 排序 属性 */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /** 获取 状态类型. 00 商品分类，11 国家分类 属性 */
    public String getStatus() {
        return status;
    }

    /** 设置 状态类型. 00 商品分类，11 国家分类 属性 */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 获取 创建人 属性 */
    public String getCreateBy() {
        return createBy;
    }

    /** 设置 创建人 属性 */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /** 获取  属性 */
    public Date getCtime() {
        return ctime;
    }

    /** 设置  属性 */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /** 获取 开始时间 属性 */
    public Date getBeginTime() {
        return beginTime;
    }

    /** 设置 开始时间 属性 */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /** 获取 结束时间 属性 */
    public Date getEndTime() {
        return endTime;
    }

    /** 设置 结束时间 属性 */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GoodsSpecialsale");
        sb.append("{specialsaleId=").append(specialsaleId);
        sb.append(", classifyId=").append(classifyId);
        sb.append(", classifyName=").append(classifyName);
        sb.append(", filepath=").append(filepath);
        sb.append(", sort=").append(sort);
        sb.append(", status=").append(status);
        sb.append(", createBy=").append(createBy);
        sb.append(", ctime=").append(ctime);
        sb.append(", beginTime=").append(beginTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof GoodsSpecialsale) {
            GoodsSpecialsale goodsSpecialsale = (GoodsSpecialsale) obj;
            if (this.getSpecialsaleId().equals(goodsSpecialsale.getSpecialsaleId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getSpecialsaleId();
        return pkStr.hashCode();
    }

}