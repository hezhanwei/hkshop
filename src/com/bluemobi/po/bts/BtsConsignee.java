package com.bluemobi.po.bts;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【】持久化对象 数据库表：bts_consignee
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-17 17:47:52
 * 
 */
public class BtsConsignee extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 
    private Long consigneeId;
    // 详细地址
    private String address;
    // 创建时间
    private Date ctime;
    // 
    private String email;
    // 是否为默认地址。1：是；0：否；
    private Byte isDefault;
    // 
    private String mobile;
    // 修改时间
    private Date mtime;
    // 收货人姓名
    private String name;
    // 地区 ID，只记录最后一级即可
    private Integer regionId;
    // 固定电话
    private String tel;
    // 用户主键 ID
    private Long userid;
    // 邮编
    private String zipcode;
    // 收货人状态(0 :停用  1:使用)
    private Byte status;

    /** 获取  属性 */
    public Long getConsigneeId() {
        return consigneeId;
    }

    /** 设置  属性 */
    public void setConsigneeId(Long consigneeId) {
        this.consigneeId = consigneeId;
    }

    /** 获取 详细地址 属性 */
    public String getAddress() {
        return address;
    }

    /** 设置 详细地址 属性 */
    public void setAddress(String address) {
        this.address = address;
    }

    /** 获取 创建时间 属性 */
    public Date getCtime() {
        return ctime;
    }

    /** 设置 创建时间 属性 */
    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    /** 获取  属性 */
    public String getEmail() {
        return email;
    }

    /** 设置  属性 */
    public void setEmail(String email) {
        this.email = email;
    }

    /** 获取 是否为默认地址。1：是；0：否； 属性 */
    public Byte getIsDefault() {
        return isDefault;
    }

    /** 设置 是否为默认地址。1：是；0：否； 属性 */
    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    /** 获取  属性 */
    public String getMobile() {
        return mobile;
    }

    /** 设置  属性 */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /** 获取 修改时间 属性 */
    public Date getMtime() {
        return mtime;
    }

    /** 设置 修改时间 属性 */
    public void setMtime(Date mtime) {
        this.mtime = mtime;
    }

    /** 获取 收货人姓名 属性 */
    public String getName() {
        return name;
    }

    /** 设置 收货人姓名 属性 */
    public void setName(String name) {
        this.name = name;
    }

    /** 获取 地区 ID，只记录最后一级即可 属性 */
    public Integer getRegionId() {
        return regionId;
    }

    /** 设置 地区 ID，只记录最后一级即可 属性 */
    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    /** 获取 固定电话 属性 */
    public String getTel() {
        return tel;
    }

    /** 设置 固定电话 属性 */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /** 获取 用户主键 ID 属性 */
    public Long getUserid() {
        return userid;
    }

    /** 设置 用户主键 ID 属性 */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /** 获取 邮编 属性 */
    public String getZipcode() {
        return zipcode;
    }

    /** 设置 邮编 属性 */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    /** 获取 收货人状态(0 :停用  1:使用) 属性 */
    public Byte getStatus() {
        return status;
    }

    /** 设置 收货人状态(0 :停用  1:使用) 属性 */
    public void setStatus(Byte status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BtsConsignee");
        sb.append("{consigneeId=").append(consigneeId);
        sb.append(", address=").append(address);
        sb.append(", ctime=").append(ctime);
        sb.append(", email=").append(email);
        sb.append(", isDefault=").append(isDefault);
        sb.append(", mobile=").append(mobile);
        sb.append(", mtime=").append(mtime);
        sb.append(", name=").append(name);
        sb.append(", regionId=").append(regionId);
        sb.append(", tel=").append(tel);
        sb.append(", userid=").append(userid);
        sb.append(", zipcode=").append(zipcode);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BtsConsignee) {
            BtsConsignee btsConsignee = (BtsConsignee) obj;
            if (this.getConsigneeId().equals(btsConsignee.getConsigneeId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getConsigneeId();
        return pkStr.hashCode();
    }

}