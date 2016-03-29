package com.Shop.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class Areas {
    private int id;
    private int OpenId;
    private String name;

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

    public int getOpenId() {
        return OpenId;
    }

    public void setOpenId(int openId) {
        OpenId = openId;
    }
}
