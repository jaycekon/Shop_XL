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
    private int Area_Id;
    private int Role_Id;
    private float recordPrices;
    private int dumpingCount;


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArea_Id() {
        return Area_Id;
    }

    public void setArea_Id(int area_Id) {
        Area_Id = area_Id;
    }

    public int getRole_Id() {
        return Role_Id;
    }

    public void setRole_Id(int role_Id) {
        Role_Id = role_Id;
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
}
