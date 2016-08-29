package com.bluemobi.po.trend;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【国家表】持久化对象 数据库表：trend_country
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 14:50:30
 * 
 */
public class TrendCountry extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 国家ID
    private Integer countryId;
    // 国家名称
    private String countryName;
    // 排序
    private Integer countrySort;
    // 图片路径
    private String filepath;
    // 类型： 11：平台添加，00：商家申请
    private String type;
    // 是否删除。11：未删除，00：已删除
    private String isDelete;
    // 创建时间
    private Date ctime;

    /** 获取 国家ID 属性 */
    public Integer getCountryId() {
        return countryId;
    }

    /** 设置 国家ID 属性 */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /** 获取 国家名称 属性 */
    public String getCountryName() {
        return countryName;
    }

    /** 设置 国家名称 属性 */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** 获取 排序 属性 */
    public Integer getCountrySort() {
        return countrySort;
    }

    /** 设置 排序 属性 */
    public void setCountrySort(Integer countrySort) {
        this.countrySort = countrySort;
    }

    /** 获取 图片路径 属性 */
    public String getFilepath() {
        return filepath;
    }

    /** 设置 图片路径 属性 */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
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
        sb.append("TrendCountry");
        sb.append("{countryId=").append(countryId);
        sb.append(", countryName=").append(countryName);
        sb.append(", countrySort=").append(countrySort);
        sb.append(", filepath=").append(filepath);
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
        if (obj instanceof TrendCountry) {
            TrendCountry trendCountry = (TrendCountry) obj;
            if (this.getCountryId().equals(trendCountry.getCountryId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getCountryId();
        return pkStr.hashCode();
    }

}