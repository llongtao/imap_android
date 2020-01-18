package com.test.hyxc.model;

import java.io.Serializable;

/**
 * Created by Mac on 2019/4/20.
 */

public class FriendDetail implements Serializable {
    private  Integer user_id1;
    private String user_name1;
    private  Integer  user_id2;
    private  String user_name2;
    private Integer friend_state;
    /********用户信息 userInfo 表 对应的是 user_id2=user_id*****/
    /********用户信息 userInfo 表 对应的是 user_id2=user_id*****/
    private int user_id;
    private String user_random_name;
    private  String user_name;
    private String user_phone;
    private String user_pwd;
    private String user_nickname;
    private String user_headimg;
    private String user_cover_img;
    private  String user_signature;
    private String user_constell;
    private int user_level;
    //发布作品数
    private int user_work_count;
    //用户在平台被点赞总数
    private int user_liked_count;
    private String user_gender;
    private String user_birth_year;
    private String user_birth_month;
    private String user_birth_day;
    private  String user_wx_nickname;
    private String user_wx_headimg;
    private  String user_country;
    private String user_province;
    private  String user_city;
    private  String user_area;
    private  String user_address;
    private Integer user_uni;
    private String uni_name; //大学名称
    private int user_island_limit;
    private  String user_island;
    //用户关注的海岛 注意和user_island的区别
    private String user_island_follow;

    public Integer getUser_id1() {
        return user_id1;
    }

    public String getUser_name1() {
        return user_name1;
    }

    public Integer getUser_id2() {
        return user_id2;
    }

    public String getUser_name2() {
        return user_name2;
    }

    public Integer getFriend_state() {
        return friend_state;
    }

    public void setUser_id1(Integer user_id1) {
        this.user_id1 = user_id1;
    }

    public void setUser_name1(String user_name1) {
        this.user_name1 = user_name1;
    }

    public void setUser_id2(Integer user_id2) {
        this.user_id2 = user_id2;
    }

    public void setUser_name2(String user_name2) {
        this.user_name2 = user_name2;
    }

    public void setFriend_state(Integer friend_state) {
        this.friend_state = friend_state;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getUser_random_name() {
        return user_random_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_headimg() {
        return user_headimg;
    }

    public String getUser_cover_img() {
        return user_cover_img;
    }

    public String getUser_signature() {
        return user_signature;
    }

    public String getUser_constell() {
        return user_constell;
    }

    public int getUser_level() {
        return user_level;
    }

    public int getUser_work_count() {
        return user_work_count;
    }

    public int getUser_liked_count() {
        return user_liked_count;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public String getUser_birth_year() {
        return user_birth_year;
    }

    public String getUser_birth_month() {
        return user_birth_month;
    }

    public String getUser_birth_day() {
        return user_birth_day;
    }

    public String getUser_wx_nickname() {
        return user_wx_nickname;
    }

    public String getUser_wx_headimg() {
        return user_wx_headimg;
    }

    public String getUser_country() {
        return user_country;
    }

    public String getUser_province() {
        return user_province;
    }

    public String getUser_city() {
        return user_city;
    }

    public String getUser_area() {
        return user_area;
    }

    public String getUser_address() {
        return user_address;
    }

    public Integer getUser_uni() {
        return user_uni;
    }

    public String getUni_name() {
        return uni_name;
    }

    public int getUser_island_limit() {
        return user_island_limit;
    }

    public String getUser_island() {
        return user_island;
    }

    public String getUser_island_follow() {
        return user_island_follow;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_random_name(String user_random_name) {
        this.user_random_name = user_random_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public void setUser_headimg(String user_headimg) {
        this.user_headimg = user_headimg;
    }

    public void setUser_cover_img(String user_cover_img) {
        this.user_cover_img = user_cover_img;
    }

    public void setUser_signature(String user_signature) {
        this.user_signature = user_signature;
    }

    public void setUser_constell(String user_constell) {
        this.user_constell = user_constell;
    }

    public void setUser_level(int user_level) {
        this.user_level = user_level;
    }

    public void setUser_work_count(int user_work_count) {
        this.user_work_count = user_work_count;
    }

    public void setUser_liked_count(int user_liked_count) {
        this.user_liked_count = user_liked_count;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public void setUser_birth_year(String user_birth_year) {
        this.user_birth_year = user_birth_year;
    }

    public void setUser_birth_month(String user_birth_month) {
        this.user_birth_month = user_birth_month;
    }

    public void setUser_birth_day(String user_birth_day) {
        this.user_birth_day = user_birth_day;
    }

    public void setUser_wx_nickname(String user_wx_nickname) {
        this.user_wx_nickname = user_wx_nickname;
    }

    public void setUser_wx_headimg(String user_wx_headimg) {
        this.user_wx_headimg = user_wx_headimg;
    }

    public void setUser_country(String user_country) {
        this.user_country = user_country;
    }

    public void setUser_province(String user_province) {
        this.user_province = user_province;
    }

    public void setUser_city(String user_city) {
        this.user_city = user_city;
    }

    public void setUser_area(String user_area) {
        this.user_area = user_area;
    }

    public void setUser_address(String user_address) {
        this.user_address = user_address;
    }

    public void setUser_uni(Integer user_uni) {
        this.user_uni = user_uni;
    }

    public void setUni_name(String uni_name) {
        this.uni_name = uni_name;
    }

    public void setUser_island_limit(int user_island_limit) {
        this.user_island_limit = user_island_limit;
    }

    public void setUser_island(String user_island) {
        this.user_island = user_island;
    }

    public void setUser_island_follow(String user_island_follow) {
        this.user_island_follow = user_island_follow;
    }
}
