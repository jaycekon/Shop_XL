package com.Shop.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
@Entity
@Table
public class ExitOrders {

    private int id;
    private Logistic logistic;          //物流公司
    private float prices;               //订单价格
    private String carriageCode;            //物流公司流水号
    private Date setTime;               //申请时间
    private Date approveTime;           //批准时间
    private Date sentTime;              //发货时间

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public float getPrices() {
        return prices;
    }

    public void setPrices(float prices) {
        this.prices = prices;
    }


    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public String getCarriageCode() {
        return carriageCode;
    }

    public void setCarriageCode(String carriageCode) {
        this.carriageCode = carriageCode;
    }

    @ManyToOne
    public Logistic getLogistic() {
        return logistic;
    }

    public void setLogistic(Logistic logistic) {
        this.logistic = logistic;
    }
}
