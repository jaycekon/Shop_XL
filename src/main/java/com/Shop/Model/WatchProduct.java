package com.Shop.Model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/28 0028.
 */
@Entity
@Table
public class WatchProduct {
    private int id;
    private User user;
    private Good good;

    @Id
    @GeneratedValue
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne
    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
