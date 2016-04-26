package com.Shop.Model;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/4/25 0025.
 */
@Entity
@Table
public class Logistic {
    private String logis_comp_id;
    private String logis_comp_name;

    @Id
    public String getLogis_comp_id() {
        return logis_comp_id;
    }

    public void setLogis_comp_id(String logis_comp_id) {
        this.logis_comp_id = logis_comp_id;
    }

    public String getLogis_comp_name() {
        return logis_comp_name;
    }

    public void setLogis_comp_name(String logis_comp_name) {
        this.logis_comp_name = logis_comp_name;
    }
}
