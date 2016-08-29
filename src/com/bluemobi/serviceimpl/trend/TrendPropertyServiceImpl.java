package com.bluemobi.serviceimpl.trend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.bluemobi.dao.trend.TrendPropertyDao;
import com.bluemobi.dao.trend.TrendPropertyValueDao;
import com.bluemobi.po.trend.TrendProperty;
import com.bluemobi.po.trend.TrendPropertyValue;
import com.bluemobi.service.trend.TrendPropertyService;
import com.bluemobi.to.trend.PropertyAndPropertyValueTO;
import com.bluemobi.to.trend.PropertyTO;
import com.bluemobi.to.trend.TrendPropertyTO;

/**
 * 【属性资源表】 服务类 实现类
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-07-30 10:42:29
 * 
 */
@Service(value = "trendPropertyService")
public class TrendPropertyServiceImpl extends MybatisBaseServiceImpl implements TrendPropertyService {

    @Autowired
    private TrendPropertyDao trendPropertyDao;
    @Autowired
    private TrendPropertyValueDao trendPropertyValueDao;

    @Override
    public MyBatisBaseDao getDao() {
        return trendPropertyDao;
    }

    public void saveTrendProperty(TrendPropertyTO trendPropertyTO) {
        TrendProperty trendProperty = new TrendProperty();

        trendProperty.setIsSpec((byte) trendPropertyTO.getIsSpec());
        trendProperty.setNote(trendPropertyTO.getNote());
        trendProperty.setLabelName(trendPropertyTO.getLabelName());
        trendProperty.setSortOrder(trendPropertyTO.getSortOrder());
        trendProperty.setStatus((byte) trendPropertyTO.getStatus());

        String[] pvals = trendPropertyTO.getpVal();
        Integer isdefault = trendPropertyTO.getpIsDefault();
        Integer[] sortOrders = trendPropertyTO.getpSortOrder();
        String[] imageUrls = trendPropertyTO.getImageUrl();

        trendPropertyDao.insert(trendProperty);

        List<TrendPropertyValue> propertyValueList = new ArrayList<TrendPropertyValue>();
        TrendPropertyValue trendPropertyValue = null;
        if (pvals != null) {
            for (int i = 0; i < pvals.length; i++) {
                trendPropertyValue = new TrendPropertyValue();
                trendPropertyValue.setPropertyValue(pvals[i]);
                if (isdefault != null && isdefault == i) {
                    trendPropertyValue.setIsDefault((byte) 1);
                } else {
                    trendPropertyValue.setIsDefault((byte) 0);
                }
                if (sortOrders != null) {
                    trendPropertyValue.setSortOrder(sortOrders[i]);
                }
                if (imageUrls != null) {
                    trendPropertyValue.setPropertyImage(imageUrls[i]);
                }
                trendPropertyValue.setStatus((byte) 1);
                trendPropertyValue.setPropertyId(trendProperty.getPropertyId());

                propertyValueList.add(trendPropertyValue);
            }
        }
        trendPropertyValueDao.insertTrendPropertyValues(propertyValueList);
    }

    public void updateTrendProperty(TrendPropertyTO trendPropertyTO) {
        TrendProperty trendProperty = new TrendProperty();

        trendProperty.setIsSpec((byte) trendPropertyTO.getIsSpec());
        trendProperty.setNote(trendPropertyTO.getNote());
        trendProperty.setLabelName(trendPropertyTO.getLabelName());
        trendProperty.setSortOrder(trendPropertyTO.getSortOrder());
        trendProperty.setStatus((byte) trendPropertyTO.getStatus());
        trendProperty.setPropertyId(trendPropertyTO.getPropertyId());

        trendPropertyDao.update(trendProperty);

        String[] pvals = trendPropertyTO.getpVal();
        Integer isdefault = trendPropertyTO.getpIsDefault();
        Integer[] propertyValueId = trendPropertyTO.getpPropertyValueId();
        Integer[] sortOrders = trendPropertyTO.getpSortOrder();
        String[] imageUrls = trendPropertyTO.getImageUrl();

        List<TrendPropertyValue> propertyValueList = new ArrayList<TrendPropertyValue>();
        TrendPropertyValue trendPropertyValue = null;
        if (pvals != null) {
            for (int i = 0; i < pvals.length; i++) {
                trendPropertyValue = new TrendPropertyValue();
                trendPropertyValue.setPropertyValue(pvals[i]);
                if (isdefault != null && isdefault == i) {
                    trendPropertyValue.setIsDefault((byte) 1);
                } else {
                    trendPropertyValue.setIsDefault((byte) 0);
                }
                if (sortOrders != null) {
                    trendPropertyValue.setSortOrder(sortOrders[i]);
                }
                if (propertyValueId != null) {
                    trendPropertyValue.setPropertyValueId(propertyValueId[i]);
                }
                if (imageUrls != null && !"".equals(imageUrls[i])) {
                    trendPropertyValue.setPropertyImage(imageUrls[i]);
                }
                trendPropertyValue.setPropertyId(trendPropertyTO.getPropertyId());
                propertyValueList.add(trendPropertyValue);
            }
        }
        trendPropertyValueDao.updateTrendPropertyValues(propertyValueList);
    }

    public void deleteTrendPropertyAndValues(String propertyids) {
        String[] strs = propertyids.split(",");
        for (int i = 0; i < strs.length; i++) {
            trendPropertyValueDao.delete(Integer.parseInt(strs[i]));

            // 非主键删除多条数据
            Map<String, Object> pValueMap = new HashMap<String, Object>();
            pValueMap.put("propertyId", Integer.parseInt(strs[i]));
            List<TrendPropertyValue> delValues = trendPropertyValueDao.selectObjectList(pValueMap);
            for (TrendPropertyValue data : delValues) {
                trendPropertyValueDao.delete(data.getPropertyValueId());
            }
        }
    }

    public Map<String, Object> selectMapPropertyAndValue(Map<String, Object> parameter) {
        return trendPropertyDao.selectMapPropertyAndValue(parameter);
    }

    @Override
    public Map<String, Object> selectPropertyByCategoryId(Map<String, Object> paramMap) throws Exception {
        List<PropertyAndPropertyValueTO> priceProperty = null;
        List<PropertyAndPropertyValueTO> otherProperty = null;
        PropertyAndPropertyValueTO valueTo = null;

        // 将价格属性和非价格属性区分
        List<PropertyAndPropertyValueTO> propertyList = trendPropertyDao.selectPropertyByCategoryId(paramMap);
        if (propertyList != null && !propertyList.isEmpty()) {
            priceProperty = new ArrayList<PropertyAndPropertyValueTO>();
            otherProperty = new ArrayList<PropertyAndPropertyValueTO>();
            for (int i = 0; i < propertyList.size(); i++) {
                valueTo = propertyList.get(i);
                if (valueTo.getIsSpec() == 1) { // 是价格属性
                    priceProperty.add(valueTo);
                } else {
                    otherProperty.add(valueTo);
                }
            }
        }

        // 封装数据结构
        List<PropertyTO> pricePropertyData = getResultDate(priceProperty);
        List<PropertyTO> otherPropertyData = getResultDate(otherProperty);

        // 封装数据结果
        Map<String, Object> properties = new Hashtable<String, Object>();
        properties.put("priceProperty", pricePropertyData);
        properties.put("otherProperty", otherPropertyData);

        return properties;
    }

    /**
     * 封装结果数据结构
     * 
     * @auther zhangzheng
     * @date 2015-11-18 下午2:28:24
     * @param list
     * @return
     * @throws Exception
     */
    private List<PropertyTO> getResultDate(List<PropertyAndPropertyValueTO> list) throws Exception {
        PropertyAndPropertyValueTO valueTo;
        List<PropertyTO> resultList = new ArrayList<PropertyTO>();
        TrendProperty property = new TrendProperty();
        TrendPropertyValue propertyValue = null;
        PropertyTO propertyTO = null;
        List<TrendPropertyValue> propertyValues = new ArrayList<TrendPropertyValue>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                valueTo = list.get(i);
                // 第一次进入循环时，为属性对象赋值
                if (i == 0) {
                    BeanUtils.copyProperties(property, valueTo);
                }
                // 循环到下一个属性时
                if (!valueTo.getPropertyId().equals(property.getPropertyId())) {
                    // 将值赋给propertyTo
                    propertyTO = new PropertyTO();
                    BeanUtils.copyProperties(propertyTO, property);
                    propertyTO.setPropertyValues(propertyValues);
                    // 创建新对象保存属性
                    property = new TrendProperty();
                    BeanUtils.copyProperties(property, valueTo);
                    // 创建新对象保存属性值
                    propertyValue = new TrendPropertyValue();
                    BeanUtils.copyProperties(propertyValue, valueTo);
                    propertyValue.setSortOrder(valueTo.getValueSortOrder());
                    propertyValue.setStatus(valueTo.getValueStatus());
                    // 创建新的list保存属性值
                    propertyValues = new ArrayList<TrendPropertyValue>();
                    propertyValues.add(propertyValue);

                    resultList.add(propertyTO);
                } else {
                    // 当和上一个属性相同时，将属性值存入list中
                    propertyValue = new TrendPropertyValue();
                    BeanUtils.copyProperties(propertyValue, valueTo);
                    propertyValue.setSortOrder(valueTo.getValueSortOrder());
                    propertyValue.setStatus(valueTo.getValueStatus());
                    propertyValues.add(propertyValue);
                }
            }
            // 为最后一个属性赋值
            propertyTO = new PropertyTO();
            BeanUtils.copyProperties(propertyTO, property);
            propertyTO.setPropertyValues(propertyValues);
            resultList.add(propertyTO);
        }
        return resultList;
    }

    @Override
    public List<PropertyTO> selectPropertyByGoodsContentId(Map<String, Object> paramMap) throws Exception {
        List<PropertyAndPropertyValueTO> list = trendPropertyDao.selectPropertyByGoodsContentId(paramMap);
        List<PropertyTO> properties = getResultDate(list);
        return properties;
    }

}
