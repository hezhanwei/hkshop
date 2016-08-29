package com.bluemobi.po.cms;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【首页banner表】持久化对象 数据库表：cms_banner
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-08 17:19:10
 * 
 */
public class CmsBanner extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 首页banner表ID
    private Integer bannerId;
    // 名称
    private String bannerName;
    // 跳转地址
    private String forwardUrl;
    // 关联trend_attachment表
    private Integer attachmentid;
    // 状态：00启用，11禁用
    private String status;
    // 终端类型：00PC，11手机
    private String terminalType;
    // 排序使用
    private Integer bannerSort;
    // 上架时间
    private Date startTime;
    // 下架时间
    private Date endTime;

    /** 获取 首页banner表ID 属性 */
    public Integer getBannerId() {
        return bannerId;
    }

    /** 设置 首页banner表ID 属性 */
    public void setBannerId(Integer bannerId) {
        this.bannerId = bannerId;
    }

    /** 获取 名称 属性 */
    public String getBannerName() {
        return bannerName;
    }

    /** 设置 名称 属性 */
    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

    /** 获取 跳转地址 属性 */
    public String getForwardUrl() {
        return forwardUrl;
    }

    /** 设置 跳转地址 属性 */
    public void setForwardUrl(String forwardUrl) {
        this.forwardUrl = forwardUrl;
    }

    /** 获取 关联trend_attachment表 属性 */
    public Integer getAttachmentid() {
        return attachmentid;
    }

    /** 设置 关联trend_attachment表 属性 */
    public void setAttachmentid(Integer attachmentid) {
        this.attachmentid = attachmentid;
    }

    /** 获取 状态：00启用，11禁用 属性 */
    public String getStatus() {
        return status;
    }

    /** 设置 状态：00启用，11禁用 属性 */
    public void setStatus(String status) {
        this.status = status;
    }

    /** 获取 终端类型：00PC，11手机 属性 */
    public String getTerminalType() {
        return terminalType;
    }

    /** 设置 终端类型：00PC，11手机 属性 */
    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    /** 获取 排序使用 属性 */
    public Integer getBannerSort() {
        return bannerSort;
    }

    /** 设置 排序使用 属性 */
    public void setBannerSort(Integer bannerSort) {
        this.bannerSort = bannerSort;
    }

    /** 获取 上架时间 属性 */
    public Date getStartTime() {
        return startTime;
    }

    /** 设置 上架时间 属性 */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /** 获取 下架时间 属性 */
    public Date getEndTime() {
        return endTime;
    }

    /** 设置 下架时间 属性 */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CmsBanner");
        sb.append("{bannerId=").append(bannerId);
        sb.append(", bannerName=").append(bannerName);
        sb.append(", forwardUrl=").append(forwardUrl);
        sb.append(", attachmentid=").append(attachmentid);
        sb.append(", status=").append(status);
        sb.append(", terminalType=").append(terminalType);
        sb.append(", bannerSort=").append(bannerSort);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CmsBanner) {
            CmsBanner cmsBanner = (CmsBanner) obj;
            if (this.getBannerId().equals(cmsBanner.getBannerId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getBannerId();
        return pkStr.hashCode();
    }

}