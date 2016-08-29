package mybatis.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bluemobi.dao.cas.CasUserDao;
import com.bluemobi.dao.goods.GoodsClassifyDao;
import com.bluemobi.dao.goods.GoodsContentDao;
import com.bluemobi.dao.goods.GoodsRecommendDao;
import com.bluemobi.dao.groupon.GrouponGrabDao;
import com.bluemobi.dao.store.StoreContentDao;
import com.bluemobi.po.goods.GoodsClassify;

/**
 * dao测试
 * <p>Title:DaoTest </p>
 * <p>Description: </p> 
 * <p>Company: </p> 
 * @author hezhanwei
 * @date 2016年8月15日 上午10:45:47
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-main-single.xml")
public class DaoTest {

	@Autowired
	private CasUserDao casUserDao;
	
	@Autowired
	private StoreContentDao storeContentDao;
	
	@Autowired
	private GoodsContentDao goodsContentDao;
	@Autowired
	private GoodsClassifyDao goodsClassifyDao;
	@Autowired
	private GoodsRecommendDao goodsRecommendDao;
	
	@Autowired
	private GrouponGrabDao grouponGrabDao;
	
	private Map<String, Object> map = new HashMap<>();
	@Test
	public void test01(){
		System.out.println(storeContentDao);
		map.put("storeId", 2);
		
		Map<String, Object> selectMapStoreInfo = storeContentDao.selectMapStoreInfo(map);
		
		Set<Entry<String, Object>> entrySet = selectMapStoreInfo.entrySet();
		
		for (Entry<String, Object> entry : entrySet) {
			String key = entry.getKey();
			Object value = entry.getValue();
			
			System.out.println(key+"-->"+value);
		}
		
		System.out.println(entrySet);
	}
	
	@Test
	public void testGlableSearch(){
		
//		LIMIT #{offset},#{rows}keyword
		
		map.put("keyword", "连衣裙");
		map.put("offset", 0);
		map.put("rows", 15);
//		long s = System.currentTimeMillis();
//		List<Map<String, Object>> list = goodsContentDao.globalSearchGoods(map);
//		System.out.println(System.currentTimeMillis()-s);
//		System.out.println(list.size());
		Integer count = goodsContentDao.globalSearchGoodsCount(map);
		System.out.println(count+"----");
	}
	
	@Test
	public void test03(){
		List<GoodsClassify> goodsClassifys = goodsClassifyDao.getGoodsClassifys(null);
		System.out.println(goodsClassifys.size());
	}
	
	@Test
	public void homeTest(){
		List<String> types = new ArrayList<>();
		types.add("33");
		types.add("44");
		List<Map<String, Object>> homeGoodsRecommend = goodsRecommendDao.getHomeGoodsRecommend(types);
		System.out.println(homeGoodsRecommend.size());
		
		Map<String, Object> map = homeGoodsRecommend.get(0);
//		for(String key:map.keySet()){
//			System.out.println("key:"+key+"---"+"value: "+map.get(key));
//		}
		
//		for(Map<String,Object> map : homeGoodsRecommend){
//			for (Map.Entry<String, Object> entry : map.entrySet()) {
//			    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//			}
//		}
		
	}
	
	@Test
	public void getSnapUpList(){
		
		List<Map<String, Object>> snapUpGoods = grouponGrabDao.getSnapUpGoods(null);
		System.out.println(snapUpGoods.size());
		
	}
}
