package com.Shop.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class Orders {
    private int id;             //订单号
    private int number;             //商品数量
    private int status;             //订单状态
    private double prices;              //订单总额
    private Date setTime;               //下单时间
    private Date payTime;               //付款时间
    private String payType;                 //付款方式
    private int stream;                 //物流
    private int refund;                 //退款申请
    private String name;                //收货人
    private String address;             //收货地址
    private String phone;               //联系电话
    private Roles roles;
    private float rolesProfit;
    private Areas areas;
    private float areaProfit;
    private User user;



    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrices() {
        return prices;
    }

    public void setPrices(double prices) {
        this.prices = prices;
    }

    public Date getSetTime() {
        return setTime;
    }

    public void setSetTime(Date setTime) {
        this.setTime = setTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }


    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getStream() {
        return stream;
    }

    public void setStream(int stream) {
        this.stream = stream;
    }

    public int getRefund() {
        return refund;
    }

    public void setRefund(int refund) {
        this.refund = refund;
    }

    @ManyToOne
    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public float getRolesProfit() {
        return rolesProfit;
    }

    public void setRolesProfit(float rolesProfit) {
        this.rolesProfit = rolesProfit;
    }


    @ManyToOne
    public Areas getAreas() {
        return areas;
    }

    public void setAreas(Areas areas) {
        this.areas = areas;
    }

    public float getAreaProfit() {
        return areaProfit;
    }

    public void setAreaProfit(float areaProfit) {
        this.areaProfit = areaProfit;
    }


    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
