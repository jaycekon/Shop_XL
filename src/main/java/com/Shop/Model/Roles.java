package com.Shop.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class Roles {
    private int id;
    private String name;
    private String phone;
    private String EMail;
    private String img;
    private String OpenId;              //微信ID
    private Areas areas;                //上级大区
    private Date date;                  //关注日期
    private float totalCommission;         //总佣金
    private float exitCommission;             //剩余佣金

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

    @ManyToOne
    public Areas getAreas() {
        return areas;
    }

    public void setAreas(Areas areas) {
        this.areas = areas;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEMail() {
        return EMail;
    }

    public void setEMail(String EMail) {
        this.EMail = EMail;
    }

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public float getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(float totalCommission) {
        this.totalCommission = totalCommission;
    }

    public float getExitCommission() {
        return exitCommission;
    }

    public void setExitCommission(float exitCommission) {
        this.exitCommission = exitCommission;
    }
}
