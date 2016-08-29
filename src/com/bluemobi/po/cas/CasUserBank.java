package com.bluemobi.po.cas;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【银行卡信息表】持久化对象 数据库表：cas_user_bank
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 14:25:41
 * 
 */
public class CasUserBank extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 银行表id
    private Integer bankid;
    // 用户ID
    private Long userid;
    // 银行卡号
    private String bankNo;
    // 银行卡名称
    private String bankName;
    // 银行支行、分行信息
    private String bankBranch;
    // 银行卡类型
    private String bankType;
    // 状态：00成功,11冻结，22启用
    private String status;
    // 创建时间
    private Date ctime;
    // 修改时间
    private Date mtime;
    // 修改人
    private String updateBy;
    // 修改说明，修改银行卡或者身份证
    private String desc;
    
    //用户名
    private String username;
    //用户手机号
    private String phone; 

    
    
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/** 获取 银行表id 属性 */
    public Integer getBankid() {
        return bankid;
    }

    /** 设置 银行表id 属性 */
    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }

    /** 获取 用户ID 属性 */
    public Long getUserid() {
        return userid;
    }

    /** 设置 用户ID 属性 */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /** 获取 银行卡号 属性 */
    public String getBankNo() {
        return bankNo;
    }

    /** 设置 银行卡号 属性 */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    /** 获取 银行卡名称 属性 */
    public String getBankName() {
        return bankName;
    }

    /** 设置 银行卡名称 属性 */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /** 获取 银行支行、分行信息 属性 */
    public String getBankBranch() {
        return bankBranch;
    }

    /** 设置 银行支行、分行信息 属性 */
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    /** 获取 银行卡类型 属性 */
    public String getBankType() {
        return bankType;
    }

    /** 设置 银行卡类型 属性 */
    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    /** 获取 状态：00成功,11冻结，22启用 属性 */
    public String getStatus() {
        return status;
    }

    /** 设置 状态：00成功,11冻结，22启用 属性 */
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

    /** 获取 修改说明，修改银行卡或者身份证 属性 */
    public String getDesc() {
        return desc;
    }

    /** 设置 修改说明，修改银行卡或者身份证 属性 */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CasUserBank");
        sb.append("{bankid=").append(bankid);
        sb.append(", userid=").append(userid);
        sb.append(", bankNo=").append(bankNo);
        sb.append(", bankName=").append(bankName);
        sb.append(", bankBranch=").append(bankBranch);
        sb.append(", bankType=").append(bankType);
        sb.append(", status=").append(status);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", desc=").append(desc);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CasUserBank) {
            CasUserBank casUserBank = (CasUserBank) obj;
            if (this.getBankid().equals(casUserBank.getBankid())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getBankid();
        return pkStr.hashCode();
    }

}