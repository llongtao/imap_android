package com.test.hyxc.model;

import java.io.Serializable;

/**
 * Created by Mac on 2019/6/10.
 */

public class IslandCategory implements Serializable {

    private  int cate_id;
    private String cate_name;
    private String cate_img;
    private int cate_state;
    private int cate_island_count;
    private int cate_parent;

    public int getCate_id() {
        return cate_id;
    }

    public String getCate_name() {
        return cate_name;
    }

    public String getCate_img() {
        return cate_img;
    }

    public int getCate_state() {
        return cate_state;
    }

    public int getCate_island_count() {
        return cate_island_count;
    }

    public int getCate_parent() {
        return cate_parent;
    }

    public void setCate_id(int cate_id) {
        this.cate_id = cate_id;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public void setCate_img(String cate_img) {
        this.cate_img = cate_img;
    }

    public void setCate_state(int cate_state) {
        this.cate_state = cate_state;
    }

    public void setCate_island_count(int cate_island_count) {
        this.cate_island_count = cate_island_count;
    }

    public void setCate_parent(int cate_parent) {
        this.cate_parent = cate_parent;
    }
}
