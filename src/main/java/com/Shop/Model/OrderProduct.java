package com.Shop.Model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class OrderProduct {
    private int id;
    private int count;          //订单项数量
    private float prices;           //订单项价格
    private String uuid;        //订单项唯一标示
    private String name;
    private int good_id;
    private String describes;       //商品描述
    private String image;           //商品描述
    private Cart cart;
    private Orders orders;
    private int wholeSaleCount;        //起批量
    private int maxCount;               //订单最大数量
    private float pv;                       //订单项PV
    private int stauts;                     //订单状态  1.申请退款，2.同意退款
    private int exitStatus;                 //订单退货状态 1.申请退货，2.同意退货，3.买家发货，4.平台退货
    private float areaProfit;
    private float roleProfit;
    private ExitOrders exitOrders;          //退货订单


    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getPrices() {
        return prices;
    }

    public void setPrices(float prices) {
        this.prices = prices;
    }


    @ManyToOne
    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }


    @ManyToOne
    public Orders getOrders() {
        return orders;
    }

    public void setOrders(Orders orders) {
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getAreaProfit() {
        return areaProfit;
    }

    public void setAreaProfit(float areaProfit) {
        this.areaProfit = areaProfit;
    }

    public float getRoleProfit() {
        return roleProfit;
    }

    public void setRoleProfit(float roleProfit) {
        this.roleProfit = roleProfit;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    public int getStauts() {
        return stauts;
    }

    public void setStauts(int stauts) {
        this.stauts = stauts;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    @OneToOne
    public ExitOrders getExitOrders() {
        return exitOrders;
    }

    public void setExitOrders(ExitOrders exitOrders) {
        this.exitOrders = exitOrders;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getWholeSaleCount() {
        return wholeSaleCount;
    }

    public void setWholeSaleCount(int wholeSaleCount) {
        this.wholeSaleCount = wholeSaleCount;
    }

    public float getPv() {
        return pv;
    }

    public void setPv(float pv) {
        this.pv = pv;
    }
}
