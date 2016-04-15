package com.Shop.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class Areas {
    private int id;
    private String OpenId;
    private String name;
    private String img;
    private long flag;
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

    public String getOpenId() {
        return OpenId;
    }

    public void setOpenId(String openId) {
        OpenId = openId;
    }

    public long getFlag() {
        return flag;
    }

    public void setFlag(long flag) {
        this.flag = flag;
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
