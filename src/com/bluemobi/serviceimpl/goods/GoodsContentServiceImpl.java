package com.bluemobi.serviceimpl.goods;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.page.Page;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.appcore.util.IDUtil;
import com.bluemobi.dao.goods.GoodsContentDao;
import com.bluemobi.dao.goods.GoodsContentSkuDao;
import com.bluemobi.dao.goods.GoodsPropertyDao;
import com.bluemobi.po.goods.GoodsContent;
import com.bluemobi.po.goods.GoodsContentSku;
import com.bluemobi.po.goods.GoodsProperty;
import com.bluemobi.service.goods.GoodsContentService;
import com.bluemobi.to.goods.GoodsContentAndSkuTO;
import com.bluemobi.to.goods.GoodsContentTO;

/**
 * 【 商品详细内容，包括商品详情、meta 相关字段等】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-11-02 10:14:35
 * 
 */
@Service(value = "goodsContentService")
public class GoodsContentServiceImpl extends MybatisBaseServiceImpl implements GoodsContentService {

    @Autowired
    private GoodsContentDao goodsContentDao;
    @Autowired
    private GoodsContentSkuDao goodsContentSkuDao;
    @Autowired
    private GoodsPropertyDao goodsPropertyDao;

    @Override
    public MyBatisBaseDao getDao() {
        return goodsContentDao;
    }

    @Override
    public int deleteNotTrueByIds(Map<String, Object> paramMap) {
        return goodsContentDao.deleteNotTrueByIds(paramMap);
    }

    @Override
    public int deleteByIds(Map<String, Object> paramMap) {
        return goodsContentDao.deleteByIds(paramMap);
    }

    @Override
    public Page<Map<String, Object>> pageApi(Map<String, Object> paramMap, Integer pageIndex, Integer pageSize) {
        return goodsContentDao.pageApi(paramMap, pageIndex, pageSize);
    }

    @Override
    public List<GoodsContent> selectObjectListByIdAndParentId(Map<String, Object> paramMap) {
        return goodsContentDao.selectObjectListByIdAndParentId(paramMap);
    }

    @Override
    public Page<Map<String, Object>> pageGoodsContent(Map<String, Object> paramMap, Integer pageIndex, Integer pageSize) {
        return goodsContentDao.pageGoodsContent(paramMap, pageIndex, pageSize);
    }

    @Override
    public List<GoodsContentAndSkuTO> selectAllContentAndSku(Map<String, Object> paramMap) {
        return goodsContentDao.selectAllContentAndSku(paramMap);
    }

    @Override
    public Long insert(GoodsContentTO goodsContentTO, Date date, Integer userid) throws IllegalAccessException, InvocationTargetException {
        GoodsContent goodsContent = new GoodsContent();

        copyAndAddGoodsContentProperty(goodsContentTO, date, goodsContent, userid);
        goodsContentDao.insert(goodsContent);

        addSkuAndProperty(goodsContentTO, date, goodsContent);
        return goodsContent.getContentId();
    }

    @Override
    public int update(GoodsContentTO goodsContentTO, Date date, Integer userid) throws IllegalAccessException, InvocationTargetException {
        Map<String, Object> param = new HashMap<String, Object>();
        GoodsContent goodsContent = new GoodsContent();

        copyAndAddGoodsContentProperty(goodsContentTO, date, goodsContent, userid);
        goodsContentDao.update(goodsContent);

        // 删除原来关联的sku和属性
        param.put("contentId", goodsContent.getContentId());
        goodsPropertyDao.deleteByGoodsContentId(param);
        goodsContentSkuDao.deleteByGoodsContentId(param);

        addSkuAndProperty(goodsContentTO, date, goodsContent);

        return 0;
    }

    /**
     * 添加商品sku和属性信息
     * 
     * @auther zhangzheng
     * @date 2015-11-24 下午1:20:54
     * @param goodsContentTO
     *            页面提交的商品to对象
     * @param date
     * @param goodsContent
     *            新增或修改的商品对象
     */
    private void addSkuAndProperty(GoodsContentTO goodsContentTO, Date date, GoodsContent goodsContent) {
        GoodsContentSku goodsContentSku = null;
        GoodsProperty goodsProperty = null;
        StringBuffer sb = null;
        StringBuffer attachmentid = new StringBuffer();
        if (goodsContentTO.getPropertyId() != null) {
            Integer[] stock = goodsContentTO.getStock();
            String[] weight = goodsContentTO.getWeight();
            String[] length = goodsContentTO.getLength();
            String[] wide = goodsContentTO.getWide();
            String[] height = goodsContentTO.getHeight();
            BigDecimal[] price = goodsContentTO.getPrice();
            BigDecimal[] priceMarket = goodsContentTO.getPriceMarket();
            BigDecimal[] priceCost = goodsContentTO.getPriceCost();
            Integer[] propertyId = goodsContentTO.getPropertyId();
            Integer[] propertyValueId = goodsContentTO.getPropertyValueId();
            Integer[] propertyId2 = goodsContentTO.getPropertyId2();
            Integer[] propertyValueId2 = goodsContentTO.getPropertyValueId2();
            Integer[] propertyId3 = goodsContentTO.getPropertyId3();
            Integer[] propertyValueId3 = goodsContentTO.getPropertyValueId3();
            String[] properties = goodsContentTO.getProperty();
            Integer[] attachmentids = goodsContentTO.getAttachmentid();

            // 拼装商品上传图片的id
            if (attachmentids != null && attachmentids.length > 0) {
                for (int i = 0; i < attachmentids.length; i++) {
                    attachmentid.append(attachmentids[i] + ",");
                }
                attachmentid.deleteCharAt(attachmentid.length() - 1);
            }

            // 拼装价格属性id和属性值id
            List<GoodsProperty> goodsProperties = new ArrayList<GoodsProperty>();
            if (propertyId != null && propertyId.length > 0) {
                addProperties(goodsContent, propertyId, propertyValueId, goodsProperties);
            }

            if (propertyId2 != null && propertyId2.length > 0) {
                addProperties(goodsContent, propertyId2, propertyValueId2, goodsProperties);
            }

            if (propertyId3 != null && propertyId3.length > 0) {
                addProperties(goodsContent, propertyId3, propertyValueId3, goodsProperties);
            }

            if (properties != null && properties.length > 0) {
                for (int i = 0; i < properties.length; i++) {
                    String[] pro = properties[i].split("_");
                    goodsProperty = new GoodsProperty();
                    goodsProperty.setContentId(goodsContent.getContentId());
                    goodsProperty.setPropertyId(Integer.valueOf(pro[0]));
                    goodsProperty.setPropertyValueId(Integer.valueOf(pro[1]));
                    goodsProperties.add(goodsProperty);
                }
            }

            if (goodsProperties != null && !goodsProperties.isEmpty()) {
                goodsPropertyDao.insertGoodsProperties(goodsProperties);
            }

            for (int i = 0; i < price.length; i++) {
                sb = new StringBuffer();
                goodsContentSku = new GoodsContentSku();
                if (propertyId != null) {
                    sb.append(propertyId[i] + "_" + propertyValueId[i] + ",");
                }
                if (propertyId2 != null) {
                    sb.append(propertyId2[i] + "_" + propertyValueId2[i] + ",");
                }
                if (propertyId3 != null) {
                    sb.append(propertyId3[i] + "_" + propertyValueId3[i] + ",");
                }
                if (sb != null && sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                goodsContentSku.setAttachmentids(attachmentid.toString());
                goodsContentSku.setContentId(goodsContent.getContentId());
                goodsContentSku.setStock(stock[i]);
                goodsContentSku.setProperty(sb.toString());
                goodsContentSku.setPrice(price[i]);
                goodsContentSku.setPriceCost(priceCost[i]);
                goodsContentSku.setPriceMarket(priceMarket[i]);
                goodsContentSku.setWeight(weight[i]);
                goodsContentSku.setLength(length[i]);
                goodsContentSku.setWide(wide[i]);
                goodsContentSku.setHeight(height[i]);
                goodsContentSku.setIsDel(Byte.valueOf("0"));
                goodsContentSku.setIsShelf(Byte.valueOf("1"));
                goodsContentSku.setCtime(date);
                goodsContentSku.setMtime(date);
                goodsContentSku.setSku(getSku());

                // 可以修改为批量新增
                goodsContentSkuDao.insert(goodsContentSku);
            }
        }
    }

    /**
     * 为商品类赋值
     * 
     * @auther zhangzheng
     * @date 2015-11-3 下午2:47:47
     * @param goodsContentTO
     * @param date
     * @param goodsContent
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void copyAndAddGoodsContentProperty(GoodsContentTO goodsContentTO, Date date, GoodsContent goodsContent, Integer userid) throws IllegalAccessException,
            InvocationTargetException {
        BeanUtils.copyProperties(goodsContent, goodsContentTO);
        goodsContent.setCtime(date);
        goodsContent.setMtime(date);
        goodsContent.setUserid(userid);
        goodsContent.setIsDel((byte) 0);
        goodsContent.setIsSpec((byte) 1);
    }

    /**
     * 将属性id和属性值id封装到GoodsProperty对象中，并添加到list数组中
     * 
     * @auther zhangzheng
     * @date 2015-11-17 下午6:29:17
     * @param goodsContent
     *            新增的商品对象
     * @param propertiesId
     *            属性id数组
     * @param propertiesValueId
     *            属性值id数组
     * @param goodsProperties
     *            封装GoodsProperty对象的数组
     */
    private void addProperties(GoodsContent goodsContent, Integer[] propertiesId, Integer[] propertiesValueId, List<GoodsProperty> goodsProperties) {
        GoodsProperty goodsProperty;
        for (int i = 0; i < propertiesId.length; i++) {
            goodsProperty = new GoodsProperty();
            goodsProperty.setContentId(goodsContent.getContentId());
            goodsProperty.setPropertyId(propertiesId[i]);
            goodsProperty.setPropertyValueId(propertiesValueId[i]);
            goodsProperties.add(goodsProperty);
        }
    }

    /**
     * 获取skuid,可以根据实际业务规则修改生成方式以符合业务需求
     * 
     * @auther zhangzheng
     * @date 2015-11-3 下午4:46:11
     * @return
     */
    public String getSku() {
        Long l = IDUtil.getId();
        return l.toString();
    }

	@Override
	public List<GoodsContent> selectGoodsListByContentids(List<Integer> contentIds) {
		return goodsContentDao.selectGoodsListByContentids(contentIds);
	}

	@Override
	public Integer updateCollectionNum(Integer contentId) {
		return goodsContentDao.updateCollectionNum(contentId);
	}

	@Override
	public Page globalSearchGoods(Map<String, Object> map, int pageIndex,int pageSize) {
		
	 	int count = goodsContentDao.globalSearchGoodsCount(map);
	    int offset = (pageIndex - 1) * pageSize;
	    map.put("offset", Integer.valueOf(offset));
	    map.put("rows", Integer.valueOf(pageSize));
	    Page page = new Page(pageIndex, pageSize, count, goodsContentDao.globalSearchGoods(map));
		
		return page;
	}

}
