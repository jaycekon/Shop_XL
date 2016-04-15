package com.Shop.Service;

import com.Shop.Dao.AreaDao;
import com.Shop.Model.Area;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
@Service
public class AddressService {
    @Autowired
    AreaDao areaDao;

    public void addArea(Area area){
        areaDao.save(area);
    }

    public void updateArea(Area area){
        areaDao.update(area);
    }

    public Area findAreaByAreaName(String name){
        return areaDao.findAreaByAreaName(name);
    }

    public List<Area> findTopArea(){
        return areaDao.findTopArea();
    }

    public List<Area> findAllArea(){
        return areaDao.findAll();
    }

    public List<Area> findAllAreaByAreaId(int area_id){
        return areaDao.findAllByAreaId(area_id);
    }
}
