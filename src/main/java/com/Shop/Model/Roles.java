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
    private String OpenId;
    private Areas areas;
    private Date date;
    private int totalCommission;
    private int exitCommission;

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

    public int getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(int totalCommission) {
        this.totalCommission = totalCommission;
    }

    public int getExitCommission() {
        return exitCommission;
    }

    public void setExitCommission(int exitCommission) {
        this.exitCommission = exitCommission;
    }
}
