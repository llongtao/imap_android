package com.test.hyxc.model;

import java.io.Serializable;

public class City implements Serializable {
    private int city_id;
    private String city_name;
    private int province_id;

    public int getCity_id() {
        return city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }
}
