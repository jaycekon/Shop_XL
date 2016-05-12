package com.Shop.Service;

import com.Shop.Dao.*;
import com.Shop.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    @Autowired
    private RolesDao rolesDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private LogisticDao logisticDao;

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

    public Areas findAreasById(int id){
        return areasDao.findById(id);
    }
    public Areas findAreasByFlag(Long flag){
        return areasDao.findByFlag(flag);
    }

    public Areas findAreasByOpenId(String openId){
        return areasDao.findByOpenId(openId);
    }

    public Roles findROlesById(int id){
        return rolesDao.findById(id);
    }

    public Roles findRolesByOpenId(String openId){
        return rolesDao.findByOpenId(openId);
    }

    public User findUseByOpenId(String openId){
        return userDao.findByOpenId(openId);
    }

    public boolean checkOpenId(String openId){
        if(findAreasByOpenId(openId)!=null){
            return false;
        }
        if(findRolesByOpenId(openId)!=null){
            return false;
        }
        if(findUseByOpenId(openId)!=null){
            return false;
        }
        return true;

    }

    public Logistic findLogisticByName(String name){
        return logisticDao.findLogisticByName(name);
    }

    public List<Logistic> listLogistic(){
        return logisticDao.findAll();
    }
}
