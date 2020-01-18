package com.test.hyxc.model;
import java.io.Serializable;

public class University  implements Serializable {
    private int uni_id;
    private String uni_name;
    private String  uni_img;
    private  int uni_area_id;
    private String uni_area_name;
    private int uni_city_id;
    private String uni_city_name;
    private int uni_city_privince;
    private String uni_city_province_name;
    /// 等级 (本科) 网站  缩写
    private String uni_level;
    private String uni_website;
    private String uni_abbreviation;
    public int getUni_id() {
        return uni_id;
    }

    public String getUni_name() {
        return uni_name;
    }

    public String getUni_img() {
        return uni_img;
    }

    public int getUni_city_id() {
        return uni_city_id;
    }

    public String getUni_city_name() {
        return uni_city_name;
    }

    public int getUni_city_privince() {
        return uni_city_privince;
    }

    public String getUni_city_province_name() {
        return uni_city_province_name;
    }

    public void setUni_id(int uni_id) {
        this.uni_id = uni_id;
    }

    public void setUni_name(String uni_name) {
        this.uni_name = uni_name;
    }

    public void setUni_img(String uni_img) {
        this.uni_img = uni_img;
    }

    public void setUni_city_id(int uni_city_id) {
        this.uni_city_id = uni_city_id;
    }

    public void setUni_city_name(String uni_city_name) {
        this.uni_city_name = uni_city_name;
    }

    public void setUni_city_privince(int uni_city_privince) {
        this.uni_city_privince = uni_city_privince;
    }

    public void setUni_city_province_name(String uni_city_province_name) {
        this.uni_city_province_name = uni_city_province_name;
    }

    public int getUni_area_id() {
        return uni_area_id;
    }

    public String getUni_area_name() {
        return uni_area_name;
    }

    public void setUni_area_id(int uni_area_id) {
        this.uni_area_id = uni_area_id;
    }

    public void setUni_area_name(String uni_area_name) {
        this.uni_area_name = uni_area_name;
    }

    public String getUni_level() {
        return uni_level;
    }

    public String getUni_website() {
        return uni_website;
    }

    public String getUni_abbreviation() {
        return uni_abbreviation;
    }

    public void setUni_level(String uni_level) {
        this.uni_level = uni_level;
    }

    public void setUni_website(String uni_website) {
        this.uni_website = uni_website;
    }

    public void setUni_abbreviation(String uni_abbreviation) {
        this.uni_abbreviation = uni_abbreviation;
    }
}
