package com.Shop.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/3/25 0025.
 */
@Entity
@Table
public class Profit {
    private int id;
    private int Area_count;   //大区比率
    private int Role_count;        //角色比率
    private float recordPrices;         //认证费用
    private int dumpingCount;           //赠送倾销币


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public float getRecordPrices() {
        return recordPrices;
    }

    public void setRecordPrices(float recordPrices) {
        this.recordPrices = recordPrices;
    }

    public int getDumpingCount() {
        return dumpingCount;
    }

    public void setDumpingCount(int dumpingCount) {
        this.dumpingCount = dumpingCount;
    }

    public int getArea_count() {
        return Area_count;
    }

    public void setArea_count(int area_count) {
        Area_count = area_count;
    }

    public int getRole_count() {
        return Role_count;
    }

    public void setRole_count(int role_count) {
        Role_count = role_count;
    }
}
