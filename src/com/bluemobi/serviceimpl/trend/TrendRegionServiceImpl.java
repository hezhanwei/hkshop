package com.bluemobi.serviceimpl.trend;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.appcore.dao.MyBatisBaseDao;
import com.appcore.service.impl.MybatisBaseServiceImpl;
import com.appcore.util.StringUtil;
import com.bluemobi.dao.trend.TrendRegionDao;
import com.bluemobi.po.trend.TrendRegion;
import com.bluemobi.service.trend.TrendRegionService;

/**
 * 【】 服务类 实现类
 * @author AutoCode 309444359@qq.com
 * @date 2015-07-17 11:22:42
 *
 */
@Service(value="trendRegionService")
public class TrendRegionServiceImpl extends MybatisBaseServiceImpl implements TrendRegionService{
    
    @Autowired
    private TrendRegionDao trendRegionDao;
    
    @Override
    public MyBatisBaseDao getDao() {
        return trendRegionDao;
    }

    public void insertRegion(TrendRegion region) {
        //1、添加非空数据
        if (region.getPid()!=0) {
            region.setPack("mainland");
        }else {
            region.setPack("");
        }
        //2、保存对象，并通过数据库生成id
        this.trendRegionDao.insert(region);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("hid", region.getHid());
        map.put("pack", region.getPack());
        map.put("pid", region.getPid());
        map.put("regionName", region.getRegionName());
        map.put("sortOrder", region.getSortOrder());
        TrendRegion regions = (TrendRegion) trendRegionDao.selectObjectList(map).get(0);
        //3、根据当前id和父类hid，拼接自己的hid
        String hid = "";
        if(region.getPid()!=0){
            Map<String, Object> reqMap = new HashMap<String, Object>();
            reqMap.put("regionId", regions.getRegionId());
            TrendRegion fj = this.trendRegionDao.selectObject(reqMap);
            hid = fj.getHid() + ":" + StringUtil.lPad( regions.getRegionId()+"" , 6, "0");
        }else{
            hid = "0:" + StringUtil.lPad( regions.getRegionId()+"" , 6, "0");
        }
        
        int grade = hid.split(":").length - 1;
        
        regions.setHid(hid);
        regions.setStatus((byte)1);
        regions.setGrade(grade);
        trendRegionDao.update(regions);
        
    }

    public void updateRegion(TrendRegion region) {
        Map<String, Object> reqMap = new HashMap<String, Object>();
        reqMap.put("regionId", region.getRegionId());
        TrendRegion regionRes = trendRegionDao.selectObject(reqMap);
        
        regionRes.setRegionName(region.getRegionName());
        regionRes.setSortOrder(region.getSortOrder());
        
        trendRegionDao.update(regionRes);
        
    }
    
    

}


