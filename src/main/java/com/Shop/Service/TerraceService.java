package com.Shop.Service;

import com.Shop.Dao.AreasDao;
import com.Shop.Dao.ProfitDao;
import com.Shop.Dao.TerraceDao;
import com.Shop.Model.Areas;
import com.Shop.Model.Profit;
import com.Shop.Model.Terrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Service
public class TerraceService {
    @Autowired
    private TerraceDao terraceDao;
    @Autowired
    private ProfitDao profitDao;
    @Autowired
    private AreasDao areasDao;

    public void addTerrace(Terrace terrace){
        terraceDao.save(terrace);
    }

    public void updateTerrace(Terrace terrace){
        Terrace t = terraceDao.findById(terrace.getId());
        t.setPassword(terrace.getPassword());
        terraceDao.update(terrace);
    }

    public Terrace loginTerrace(Terrace terrace){
        Terrace t = terraceDao.findByName(terrace.getName());
        if(t == null){
            return null;
        }else if(!t.getPassword().equals(terrace.getPassword())){
            return null;
        }else{
            return t;
        }
    }

    public void addProfit(Profit profit){
        profitDao.save(profit);
    }

    public void updateProfit(Profit profit){
        profitDao.update(profit);
    }

    public Profit findProfit(){
        return profitDao.findById();
    }

    public Areas findAreasByFlag(Long flag){
        return areasDao.findByFlag(flag);
    }
}
