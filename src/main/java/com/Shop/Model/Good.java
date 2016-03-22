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
    private String name;
    private int num;
    private String describes;
    private Timestamp subTime;
    private Timestamp downTime;
    private int status;
    private String firm;
    private float wholesalePrices;
    private float dumpingPrices;
    private float productPrices;
    private float wathcPrices;
    private int wholesaleCount;

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

    public float getWathcPrices() {
        return wathcPrices;
    }

    public void setWathcPrices(float wathcPrices) {
        this.wathcPrices = wathcPrices;
    }

    public int getWholesaleCount() {
        return wholesaleCount;
    }

    public void setWholesaleCount(int wholesaleCount) {
        this.wholesaleCount = wholesaleCount;
    }
}
