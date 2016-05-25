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
    private float countPrices;          //倾销币价格
    private int level1;                 //1级人数
    private int level1Rate;             //1级佣金
    private int level2;                 //2级人数
    private int level2Rate;             //2级佣金
    private int level3Rate;             //2级佣金

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

    public float getCountPrices() {
        return countPrices;
    }

    public void setCountPrices(float countPrices) {
        this.countPrices = countPrices;
    }

    public int getLevel1() {
        return level1;
    }

    public void setLevel1(int level1) {
        this.level1 = level1;
    }

    public int getLevel1Rate() {
        return level1Rate;
    }

    public void setLevel1Rate(int level1Rate) {
        this.level1Rate = level1Rate;
    }

    public int getLevel2() {
        return level2;
    }

    public void setLevel2(int level2) {
        this.level2 = level2;
    }

    public int getLevel2Rate() {
        return level2Rate;
    }

    public void setLevel2Rate(int level2Rate) {
        this.level2Rate = level2Rate;
    }

    public int getLevel3Rate() {
        return level3Rate;
    }

    public void setLevel3Rate(int level3Rate) {
        this.level3Rate = level3Rate;
    }
}
