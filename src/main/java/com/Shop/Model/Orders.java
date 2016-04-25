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
    private int status;             //订单是否有退货退款状态
    private float prices;              //订单总额
    private Date setTime;               //下单时间
    private Date payTime;               //付款时间
    private Date sentTime;              //发货时间
    private String payType;                 //付款方式
    private String name;                //收货人
    private String address;             //收货地址
    private String phone;               //联系电话
    private int D;                      //订单状态
    private int F;                      //支付状态
    private int P;                      //配送状态
    private int T;                      //退货状态
    private int C;                      //评价状态
    private String uuid;                //订单编号
    private Roles roles;
    private float rolesProfit;
    private Areas areas;
    private float areaProfit;
    private User user;
    private float totalProfit;        //平台盈利

    public Orders(){
        super();
        this.D = 0;this.F = 0;this.P = 0;this.T = 0;this.C = 0;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getD() {
        return D;
    }

    public void setD(int d) {
        D = d;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public int getP() {
        return P;
    }

    public void setP(int p) {
        P = p;
    }

    public int getT() {
        return T;
    }

    public void setT(int t) {
        T = t;
    }

    public int getC() {
        return C;
    }

    public void setC(int c) {
        C = c;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }
}
