package com.bluemobi.po.goods;

import java.math.BigDecimal;
import java.util.Date;

import com.appcore.model.AbstractObject;

/**
 * 【推荐表】持久化对象 数据库表：goods_recommend
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2016-08-23 14:44:42
 * 
 */
public class GoodsRecommend extends AbstractObject {

    public static final long serialVersionUID = 1L;

    // 推荐ID
    private Long recommendId;
    // 商品sku
    private String goodsSku;
    // 商品名称
    private String goodsName;
    // 商品销售价格
    private BigDecimal goodsPrice;
    // 商品简介
    private String goodsMemo;
    // 广告链接
    private String urlLink;
    // 销售量
    private Integer salesVolume;
    // 商品图片路径
    private String filepath;
    // 推荐类型： 00主推 11次推 22特卖 33热卖商品  44首页轮播
    private String recommendType;
    // 创建人
    private String createBy;
    // 
    private Date ctime;
    // 开始时间
    private Date beginTime;
    // 截止时间
    private Date endTime;

    /** 获取 推荐ID 属性 */
    public Long getRecommendId() {
        return recommendId;
    }

    /** 设置 推荐ID 属性 */
    public void setRecommendId(Long recommendId) {
        this.recommendId = recommendId;
    }

    /** 获取 商品sku 属性 */
    public String getGoodsSku() {
        return goodsSku;
    }

    /** 设置 商品sku 属性 */
    public void setGoodsSku(String goodsSku) {
        this.goodsSku = goodsSku;
    }

    /** 获取 商品名称 属性 */
    public String getGoodsName() {
        return goodsName;
    }

    /** 设置 商品名称 属性 */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /** 获取 商品销售价格 属性 */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /** 设置 商品销售价格 属性 */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /** 获取 商品简介 属性 */
    public String getGoodsMemo() {
        return goodsMemo;
    }

    /** 设置 商品简介 属性 */
    public void setGoodsMemo(String goodsMemo) {
        this.goodsMemo = goodsMemo;
    }

    /** 获取 广告链接 属性 */
    public String getUrlLink() {
        return urlLink;
    }

    /** 设置 广告链接 属性 */
    public void setUrlLink(String urlLink) {
        this.urlLink = urlLink;
    }

    /** 获取 销售量 属性 */
    public Integer getSalesVolume() {
        return salesVolume;
    }

    /** 设置 销售量 属性 */
    public void setSalesVolume(Integer salesVolume) {
        this.salesVolume = salesVolume;
    }

    /** 获取 商品图片路径 属性 */
    public String getFilepath() {
        return filepath;
    }

    /** 设置 商品图片路径 属性 */
    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    /** 获取 推荐类型： 00主推 11次推 22特卖 33热卖商品  44首页轮播 属性 */
    public String getRecommendType() {
        return recommendType;
    }

    /** 设置 推荐类型： 00主推 11次推 22特卖 33热卖商品  44首页轮播 属性 */
    public void setRecommendType(String recommendType) {
        this.recommendType = recommendType;
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

    /** 获取 截止时间 属性 */
    public Date getEndTime() {
        return endTime;
    }

    /** 设置 截止时间 属性 */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("GoodsRecommend");
        sb.append("{recommendId=").append(recommendId);
        sb.append(", goodsSku=").append(goodsSku);
        sb.append(", goodsName=").append(goodsName);
        sb.append(", goodsPrice=").append(goodsPrice);
        sb.append(", goodsMemo=").append(goodsMemo);
        sb.append(", urlLink=").append(urlLink);
        sb.append(", salesVolume=").append(salesVolume);
        sb.append(", filepath=").append(filepath);
        sb.append(", recommendType=").append(recommendType);
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
        if (obj instanceof GoodsRecommend) {
            GoodsRecommend goodsRecommend = (GoodsRecommend) obj;
            if (this.getRecommendId().equals(goodsRecommend.getRecommendId())) {
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