package com.test.hyxc.model;

import java.io.Serializable;

public class IslandResidentUserInfo implements Serializable {
    private int user_id;
    private String user_nickname;
    private String user_headimg;
    private String user_signature;
     //海岛信息
    private int res_island;
    private int res_user_state;
    private int res_privilege;
    //是否显示 操作 按钮
    private boolean showMore = true;

    public int getUser_id() {
        return user_id;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_headimg() {
        return user_headimg;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public void setUser_headimg(String user_headimg) {
        this.user_headimg = user_headimg;
    }

    public void setUser_signature(String user_signature) {
        this.user_signature = user_signature;
    }

    public String getUser_signature() {
        return user_signature;
    }

    public int getRes_island() {
        return res_island;
    }

    public int getRes_user_state() {
        return res_user_state;
    }

    public int getRes_privilege() {
        return res_privilege;
    }

    public void setRes_island(int res_island) {
        this.res_island = res_island;
    }

    public void setRes_user_state(int res_user_state) {
        this.res_user_state = res_user_state;
    }

    public void setRes_privilege(int res_privilege) {
        this.res_privilege = res_privilege;
    }

    public boolean getShowMore() {
        return showMore;
    }

    public void setShowMore(boolean showMore) {
        this.showMore = showMore;
    }
}