package com.bluemobi.po.store;

import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【每日好店】持久化对象 数据库表：store_recommend
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-24 17:16:56
 * 
 */
public class StoreRecommend extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // id
    private Long recommendId;
    // 店铺id
    private Long storeId;
    // 店铺名称
    private String storeName;
    // 排序
    private Integer recommendSort;
    // 推荐状态。00：已推荐，11：已发布，22：取消推荐
    private String recommendStatus;
    // 创建时间
    private Date ctime;
    // 修改时间
    private Date mtime;

    /** 获取 id 属性 */
    public Long getRecommendId() {
        return recommendId;
    }

    /** 设置 id 属性 */
    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    /** 获取 店铺id 属性 */
    public Long getStoreId() {
        return storeId;
    }

    /** 设置 店铺id 属性 */
    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    /** 获取 店铺名称 属性 */
    public String getStoreName() {
        return storeName;
    }

    /** 设置 店铺名称 属性 */
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /** 获取 排序 属性 */
    public Integer getRecommendSort() {
        return recommendSort;
    }

    /** 设置 排序 属性 */
    public void setRecommendSort(Integer recommendSort) {
        this.recommendSort = recommendSort;
    }

    /** 获取 推荐状态。00：已推荐，11：已发布，22：取消推荐 属性 */
    public String getRecommendStatus() {
        return recommendStatus;
    }

    /** 设置 推荐状态。00：已推荐，11：已发布，22：取消推荐 属性 */
    public void setRecommendStatus(String recommendStatus) {
        this.recommendStatus = recommendStatus;
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
        sb.append("StoreRecommend");
        sb.append("{recommendId=").append(recommendId);
        sb.append(", storeId=").append(storeId);
        sb.append(", storeName=").append(storeName);
        sb.append(", recommendSort=").append(recommendSort);
        sb.append(", recommendStatus=").append(recommendStatus);
        sb.append(", ctime=").append(ctime);
        sb.append(", mtime=").append(mtime);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof StoreRecommend) {
            StoreRecommend storeRecommend = (StoreRecommend) obj;
            if (this.getRecommendId().equals(storeRecommend.getRecommendId())) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String pkStr = "" + this.getRecommendId();
        return pkStr.hashCode();
    }

}