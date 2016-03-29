package com.Shop.Dao;


import com.Shop.Model.Profit;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
public class ProfitDao extends BaseDao{
    public Profit findById(){
        return super.hibernateTemplate.get(Profit.class,1);
    }

    public void save(Profit profit){
        super.hibernateTemplate.save(profit);
    }

    public void update(Profit profit){
        super.hibernateTemplate.update(profit);
    }


}
