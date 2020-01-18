package com.test.hyxc.model;

import java.io.Serializable;

public class CCRegion  implements Serializable {
    private int id;
    private String name;
    private int parent_id;
    private int level;
    private String city_code;
    private String zip_code;
    private  float lng;
    private float lat;
    private String pinyin;
    private int state;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getParent_id() {
        return parent_id;
    }
    public int getLevel() {
        return level;
    }

    public String getCity_code() {
        return city_code;
    }

    public String getZip_code() {
        return zip_code;
    }

    public float getLng() {
        return lng;
    }

    public float getLat() {
        return lat;
    }

    public String getPinyin() {
        return pinyin;
    }

    public int getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public void setState(int state) {
        this.state = state;
    }
}
