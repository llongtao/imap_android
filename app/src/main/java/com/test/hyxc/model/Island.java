package com.test.hyxc.model;


import java.io.Serializable;

public class Island  implements Serializable {
    private Integer is_id;
    private  String is_name;
    private String is_describe;
    private Integer is_city_id;
    private String is_city_name;
    private Integer is_area_id;
    private String is_img;
    private String is_logo;
    private Integer is_parent_id;
    private String is_parent_name;
    private  Integer is_parent_type;
    private String is_longitude;
    private String is_latitude;
    private Integer is_category_id;
    private String  is_category;
    //一级目录 体育、美术
    private Integer is_category_parent_id;
    private  String is_category_parent;
    private Integer is_status;
    private Integer is_open;
    private Integer is_join_allow;
    private Integer is_ask_allow;
    private Integer is_people_current;
    private Integer is_people_limit;
    private Integer is_level;
    private Integer is_work_count;
    private Integer is_liked_count;
    private  Integer is_follow_count;
    private Time is_create_time;
    //private Timestamp is_create_time;
    private Integer is_create_user;
    private Integer is_owner;
    private Integer is_im_id;
    //额外信息

    public Integer getIs_open(){return  is_open;}
    public void setIs_open(Integer is_open) {this.is_open=is_open;}
    public Integer getIs_id() {
        return is_id;
    }

    public String getIs_name() {
        return is_name;
    }

    public String getIs_img() {
        return is_img;
    }

    public Integer getIs_parent_id() {
        return is_parent_id;
    }

    public String getIs_longitude() {
        return is_longitude;
    }

    public String getIs_latitude() {
        return is_latitude;
    }

    public String getIs_category() {
        return is_category;
    }

    public Integer getIs_status() {
        return is_status;
    }

    public Integer getIs_people_current() {
        return is_people_current;
    }

    public Integer getIs_people_limit() {
        return is_people_limit;
    }

    public Integer getIs_level() {
        return is_level;
    }

    public Time getIs_create_time() {
        return is_create_time;
    }

    public Integer getIs_create_user() {
        return is_create_user;
    }

    public Integer getIs_owner() {
        return is_owner;
    }

    public void setIs_id(Integer is_id) {
        this.is_id = is_id;
    }

    public void setIs_name(String is_name) {
        this.is_name = is_name;
    }

    public void setIs_img(String is_img) {
        this.is_img = is_img;
    }

    public void setIs_parent_id(Integer is_parent_id) {
        this.is_parent_id = is_parent_id;
    }

    public void setIs_longitude(String is_longitude) {
        this.is_longitude = is_longitude;
    }

    public void setIs_latitude(String is_latitude) {
        this.is_latitude = is_latitude;
    }

    public void setIs_category(String is_category) {
        this.is_category = is_category;
    }

    public void setIs_status(Integer is_status) {
        this.is_status = is_status;
    }

    public void setIs_people_current(Integer is_people_current) {
        this.is_people_current = is_people_current;
    }

    public void setIs_people_limit(Integer is_people_limit) {
        this.is_people_limit = is_people_limit;
    }

    public void setIs_level(Integer is_level) {
        this.is_level = is_level;
    }

    public void setIs_create_time( Time is_create_time) {
        this.is_create_time = is_create_time;
    }

    public void setIs_create_user(Integer is_create_user) {
        this.is_create_user = is_create_user;
    }

    public void setIs_owner(Integer is_owner) {
        this.is_owner = is_owner;
    }
     public String getIs_describe(){return  is_describe; }
     public void setIs_describe(String is_describe){this.is_describe=is_describe;}
    public void setIs_category_parent(String is_category_parent) {
        this.is_category_parent = is_category_parent;
    }

    public String getIs_category_parent() {
        return is_category_parent;
    }

    public String getIs_parent_name() {
        return is_parent_name;
    }

    public Integer getIs_parent_type() {
        return is_parent_type;
    }

    public void setIs_parent_name(String is_parent_name) {
        this.is_parent_name = is_parent_name;
    }

    public void setIs_parent_type(Integer is_parent_type) {
        this.is_parent_type = is_parent_type;
    }

    public void setIs_city_name(String is_city_name) {
        this.is_city_name = is_city_name;
    }

    public String getIs_city_name() {
        return is_city_name;
    }

    public Integer getIs_join_allow() {
        return is_join_allow;
    }

    public void setIs_join_allow(Integer is_join_allow) {
        this.is_join_allow = is_join_allow;
    }

    public void setIs_logo(String is_logo) {
        this.is_logo = is_logo;
    }

    public String getIs_logo() {
        return is_logo;
    }

    public Integer getIs_category_parent_id() {
        return is_category_parent_id;
    }

    public void setIs_category_parent_id(Integer is_category_parent_id) {
        this.is_category_parent_id = is_category_parent_id;
    }

    public Integer getIs_category_id() {
        return is_category_id;
    }

    public void setIs_category_id(Integer is_category_id) {
        this.is_category_id = is_category_id;
    }

    public Integer getIs_city_id() {
        return is_city_id;
    }

    public Integer getIs_area_id() {
        return is_area_id;
    }

    public void setIs_city_id(Integer is_city_id) {
        this.is_city_id = is_city_id;
    }

    public void setIs_area_id(Integer is_area_id) {
        this.is_area_id = is_area_id;
    }

    public Integer getIs_liked_count() {
        return is_liked_count;
    }

    public void setIs_liked_count(Integer is_liked_count) {
        this.is_liked_count = is_liked_count;
    }

    public Integer getIs_im_id() {
        return is_im_id;
    }

    public void setIs_im_id(Integer is_im_id) {
        this.is_im_id = is_im_id;
    }

    public Integer getIs_ask_allow() {
        return is_ask_allow;
    }

    public void setIs_ask_allow(Integer is_ask_allow) {
        this.is_ask_allow = is_ask_allow;
    }

    public Integer getIs_work_count() {
        return is_work_count;
    }

    public void setIs_work_count(Integer is_work_count) {
        this.is_work_count = is_work_count;
    }

    public void setIs_follow_count(Integer is_follow_count) {
        this.is_follow_count = is_follow_count;
    }

    public Integer getIs_follow_count() {
        return is_follow_count;
    }
}
