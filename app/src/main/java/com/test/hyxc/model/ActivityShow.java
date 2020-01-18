package com.test.hyxc.model;

import com.mp4parser.iso14496.part15.TierBitRateBox;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by WWW on 2019/1/22.
 */

public class ActivityShow implements Serializable {
     private Integer act_id;
     private String act_title;
     private String act_text;
     private Integer act_linkman;
     private Integer act_pubman;
     //发布人昵称
     private String act_pubman_nickname;
     private Integer act_island;
     private String act_island_name;
     private String act_island_logo;
     private Integer act_island_university;
     private String  act_university_name;
     private String act_city;
     private String act_district;
     private Integer act_people_limit;
     private Integer act_people_count;
     private Integer act_state;
     private Time act_require_time;
     private Time act_time;
     private Time act_end_time;
     private String act_address;
     private double act_longitude;
     private double act_latitude;
     private int current_state;
     private Time create_time;
     //是否已经报名 0:未报名 1：报名了
     private Integer sign = 0;
    ///////用于显示的额外信息
     private int end=0;
     //图片资源列表
     private List<ActivityResource> resourcesList;

    public Integer getAct_id() {
        return act_id;
    }

    public String getAct_title() {
        return act_title;
    }

    public String getAct_text() {
        return act_text;
    }

    public Integer getAct_linkman() {
        return act_linkman;
    }

    public Integer getAct_pubman() {
        return act_pubman;
    }

    public Integer getAct_island() {
        return act_island;
    }

    public Integer getAct_island_university() {
        return act_island_university;
    }

    public String getAct_city() {
        return act_city;
    }

    public String getAct_district() {
        return act_district;
    }

    public Integer getAct_people_limit() {
        return act_people_limit;
    }

    public Integer getAct_people_count() {
        return act_people_count;
    }

    public Integer getAct_state() {
        return act_state;
    }

    public String getAct_address() {
        return act_address;
    }

    public double getAct_longitude() {
        return act_longitude;
    }

    public double getAct_latitude() {
        return act_latitude;
    }

    public void setAct_id(Integer act_id) {
        this.act_id = act_id;
    }

    public void setAct_title(String act_title) {
        this.act_title = act_title;
    }

    public void setAct_text(String act_text) {
        this.act_text = act_text;
    }

    public void setAct_linkman(Integer act_linkman) {
        this.act_linkman = act_linkman;
    }

    public void setAct_pubman(Integer act_pubman) {
        this.act_pubman = act_pubman;
    }

    public void setAct_island(Integer act_island) {
        this.act_island = act_island;
    }

    public void setAct_island_university(Integer act_island_university) {
        this.act_island_university = act_island_university;
    }

    public void setAct_city(String act_city) {
        this.act_city = act_city;
    }

    public void setAct_district(String act_district) {
        this.act_district = act_district;
    }

    public void setAct_people_limit(Integer act_people_limit) {
        this.act_people_limit = act_people_limit;
    }

    public void setAct_people_count(Integer act_people_count) {
        this.act_people_count = act_people_count;
    }

    public void setAct_state(Integer act_state) {
        this.act_state = act_state;
    }


    public void setAct_address(String act_address) {
        this.act_address = act_address;
    }

    public void setAct_longitude(double act_longitude) {
        this.act_longitude = act_longitude;
    }

    public void setAct_latitude(double act_latitude) {
        this.act_latitude = act_latitude;
    }

    public List<ActivityResource> getResourcesList() {
        return resourcesList;
    }

    public void setResourcesList(List<ActivityResource> resourcesList) {
        this.resourcesList = resourcesList;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Time getAct_require_time() {
        return act_require_time;
    }

    public Time getAct_time() {
        return act_time;
    }

    public Time getCreate_time() {
        return create_time;
    }

    public void setAct_require_time(Time act_require_time) {
        this.act_require_time = act_require_time;
    }

    public void setAct_time(Time act_time) {
        this.act_time = act_time;
    }

    public String getAct_pubman_nickname() {
        return act_pubman_nickname;
    }

    public void setAct_pubman_nickname(String act_pubman_nickname) {
        this.act_pubman_nickname = act_pubman_nickname;
    }

    public void setAct_end_time(Time act_end_time) {
        this.act_end_time = act_end_time;
    }

    public Time getAct_end_time() {
        return act_end_time;
    }

    public void setCreate_time(Time create_time) {
        this.create_time = create_time;
    }

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public String getAct_island_name() {
        return act_island_name;
    }

    public String getAct_island_logo() {
        return act_island_logo;
    }

    public void setAct_island_name(String act_island_name) {
        this.act_island_name = act_island_name;
    }

    public void setAct_island_logo(String act_island_logo) {
        this.act_island_logo = act_island_logo;
    }

    public String getAct_university_name() {
        return act_university_name;
    }

    public void setAct_university_name(String act_university_name) {
        this.act_university_name = act_university_name;
    }

    public int getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(int current_state) {
        this.current_state = current_state;
    }
}
