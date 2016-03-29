package com.Shop.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class Good {
    private int id;
    private String name;  //商品名
    private int num;        //商品库存
    private String describes;       //商品描述备注
    private Timestamp subTime;      //上架时间
    private Timestamp downTime;         //下架时间
    private int status;                 //商品状态 1.上架 0.下架
    private String firm;                    //商品厂家
    private float pv;                       //pv值
    private float wholesalePrices;          //促销价
    private float dumpingPrices;                //倾销价
    private float productPrices;                //商品零售价
    private float wPrices;                  //倾销币查看价格
    private int wholesaleCount;                 //起批量
    private int saleCount;                      //销量

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public Timestamp getSubTime() {
        return subTime;
    }

    public void setSubTime(Timestamp subTime) {
        this.subTime = subTime;
    }

    public Timestamp getDownTime() {
        return downTime;
    }

    public void setDownTime(Timestamp downTime) {
        this.downTime = downTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFirm() {
        return firm;
    }

    public void setFirm(String firm) {
        this.firm = firm;
    }

    public float getWholesalePrices() {
        return wholesalePrices;
    }

    public void setWholesalePrices(float wholesalePrices) {
        this.wholesalePrices = wholesalePrices;
    }

    public float getDumpingPrices() {
        return dumpingPrices;
    }

    public void setDumpingPrices(float dumpingPrices) {
        this.dumpingPrices = dumpingPrices;
    }

    public float getProductPrices() {
        return productPrices;
    }

    public void setProductPrices(float productPrices) {
        this.productPrices = productPrices;
    }

    public int getWholesaleCount() {
        return wholesaleCount;
    }

    public void setWholesaleCount(int wholesaleCount) {
        this.wholesaleCount = wholesaleCount;
    }

    public float getPv() {
        return pv;
    }

    public void setPv(float pv) {
        this.pv = pv;
    }


    public int getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    public float getwPrices() {
        return wPrices;
    }

    public void setwPrices(float wPrices) {
        this.wPrices = wPrices;
    }
}
