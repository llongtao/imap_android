package com.test.hyxc.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Created by Mac on 2019/8/5.
 */

public class IslandResident implements Serializable {
    private  int res_island;
    private String res_island_name;
    private Integer res_ask_user;
    private  int res_user;
    private String res_user_name;
    private Time res_join_time;
    private int res_user_level;
    private int res_user_state;
    private String res_apply_message;
    private int res_privilege;

    public int getRes_island() {
        return res_island;
    }

    public String getRes_island_name() {
        return res_island_name;
    }

    public Integer getRes_ask_user() {
        return res_ask_user;
    }

    public int getRes_user() {
        return res_user;
    }

    public String getRes_user_name() {
        return res_user_name;
    }


    public int getRes_user_level() {
        return res_user_level;
    }

    public int getRes_user_state() {
        return res_user_state;
    }

    public String getRes_apply_message() {
        return res_apply_message;
    }

    public int getRes_privilege() {
        return res_privilege;
    }

    public void setRes_island(int res_island) {
        this.res_island = res_island;
    }

    public void setRes_island_name(String res_island_name) {
        this.res_island_name = res_island_name;
    }

    public void setRes_ask_user(Integer res_ask_user) {
        this.res_ask_user = res_ask_user;
    }

    public void setRes_user(int res_user) {
        this.res_user = res_user;
    }

    public void setRes_user_name(String res_user_name) {
        this.res_user_name = res_user_name;
    }


    public void setRes_user_level(int res_user_level) {
        this.res_user_level = res_user_level;
    }

    public void setRes_user_state(int res_user_state) {
        this.res_user_state = res_user_state;
    }

    public void setRes_apply_message(String res_apply_message) {
        this.res_apply_message = res_apply_message;
    }

    public void setRes_privilege(int res_privilege) {
        this.res_privilege = res_privilege;
    }

    public Time getRes_join_time() {
        return res_join_time;
    }

    public void setRes_join_time(Time res_join_time) {
        this.res_join_time = res_join_time;
    }
}
