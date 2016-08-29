package com.bluemobi.po.cas;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【收藏表】持久化对象 数据库表：cas_collection
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-11 16:07:12
 * 
 */
public class CasCollection extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 收藏表id
    private Integer collectionid;
    // 用户id
    private Long userid;
    // 收藏类型 0 ：店铺  1：商品
    private Byte collectionType;
    // 与收藏类型对应的id
    private Integer storeorgoodsId;
    // 创建时间
    private Date ctime;
    // 修改时间
    private Date mtime;

    /** 获取 收藏表id 属性 */
    public Integer getCollectionid() {
        return collectionid;
    }

    /** 设置 收藏表id 属性 */
    public void setCollectionid(Integer collectionid) {
        this.collectionid = collectionid;
    }

    /** 获取 用户id 属性 */
    public Long getUserid() {
        return userid;
    }

    /** 设置 用户id 属性 */
    public void setUserid(Long userid) {
        this.userid = userid;
    }

    /** 获取 收藏类型 0 ：店铺  1：商品 属性 */
    public Byte getCollectionType() {
        return collectionType;
    }

    /** 设置 收藏类型 0 ：店铺  1：商品 属性 */
    public void setCollectionType(Byte collectionType) {
        this.collectionType = collectionType;
    }

    /** 获取 与收藏类型对应的id 属性 */
    public Integer getStoreorgoodsId() {
        return storeorgoodsId;
    }

    /** 设置 与收藏类型对应的id 属性 */
    public void setStoreorgoodsId(Integer storeorgoodsId) {
        this.storeorgoodsId = storeorgoodsId;
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("CasCollection");
        sb.append("{collectionid=").append(collectionid);
        sb.append(", userid=").append(userid);
        sb.append(", collectionType=").append(collectionType);
        sb.append(", storeorgoodsId=").append(storeorgoodsId);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CasCollection) {
            CasCollection casCollection = (CasCollection) obj;
            if (this.getCollectionid().equals(casCollection.getCollectionid())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getCollectionid();
        return pkStr.hashCode();
    }

}