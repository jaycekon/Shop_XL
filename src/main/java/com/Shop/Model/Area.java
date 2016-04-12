package com.Shop.Model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
@Entity
@Table
public class Area {
    private int id;
    private String name;
    private Area area;

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
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
}
