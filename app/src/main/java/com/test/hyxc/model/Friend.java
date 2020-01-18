package com.test.hyxc.model;

import java.io.Serializable;

public class Friend  implements Serializable{
    private  Integer user_id1;
    private String user_name1;
    private  Integer  user_id2;
    private  String user_name2;
    private Integer friend_state;
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
}

