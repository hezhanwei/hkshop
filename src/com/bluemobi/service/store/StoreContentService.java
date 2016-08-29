package com.bluemobi.service.store;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.Store;

import com.appcore.page.Page;
import com.appcore.service.MybatisBaseService;
import com.bluemobi.po.store.StoreContent;
import com.bluemobi.to.store.StoreAndUserTO;

/**
 * 【已签约商户表】 服务类 接口
 * 
 * @author AutoCode 309444359@qq.com
 * @date 2015-09-15 17:24:48
 * 
 */
public interface StoreContentService extends MybatisBaseService {

	/**
	 * 查询商铺详情
	 * 
	 * @auther zhangzheng
	 * @date 2015-10-26 上午11:18:41
	 * @param parameter
	 * @return
	 */
	Map<String, Object> selectMapStoreInfo(Map<String, Object> parameter);

	/**
	 * 新增商铺
	 * 
	 * @auther zhangzheng
	 * @date 2015-12-1 下午2:00:01
	 * @param date
	 * @param storeAndUserTO
	 * @return
	 */
	int insert(Date date, StoreAndUserTO storeAndUserTO) throws IllegalAccessException, InvocationTargetException;

	/**
	 * 获取用户收藏的店铺列表
	 * 
	 * @param storeIds
	 * @return
	 */
	List<StoreContent> selectGoodsListByContentids(List<Integer> storeIds);

	/**
	 * 更新店铺收藏次数
	 * 
	 * @data 2016年8月12日 下午2:45:08
	 * @param storeId
	 * @return
	 */
	Integer updateStoreCollectionNum(Integer storeId);

	/**
	 * 移动端查看店铺列表
	 * 
	 * @data 2016年8月12日 下午2:44:30
	 * @return
	 */
	Page<Map<String, Object>> getStoreList(Map<String, Object> map, int pageIndex, int pageSize);
}
