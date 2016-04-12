package com.Shop.Model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class OrderProduct {
    private int id;
    private int count;
    private float prices;
    private String name;
    private int good_id;
    private String describes;
    private String image;
    private Cart cart;
    private Orders orders;
    private int stauts;
    private float areaProfit;
    private float roleProfit;

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
}
