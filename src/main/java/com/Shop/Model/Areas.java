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
    private Date date;                  //注册时间
    private float totalCommission;                //全部佣金
    private float exitCommission;             //剩余佣金
    private float waitCommission;             //待结算佣金

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

    public float getWaitCommission() {
        return waitCommission;
    }

    public void setWaitCommission(float waitCommission) {
        this.waitCommission = waitCommission;
    }
}
