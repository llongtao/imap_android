package com.test.hyxc.model;

import java.io.Serializable;
import java.util.List;
public class UserWorkDiscussShow implements Serializable {
    private  int work_id;
    private  int discuss_id;
    private int user_id;
    private String user_name;
    private  String user_nickname;
    private  String user_headimg;
    private Time discuss_time;
    private String discuss_text;
    private int discuss_state;
    private int discuss_like_count;
    private  int discuss_reply_count;//评论回复数
    private int discuss_reply_select_count;//回复 选几条
    private List<WorkDiscussReply> workDiscussReplyList;

    public int getWork_id() {
        return work_id;
    }

    public int getUser_id() {
        return user_id;
    }


    public String getDiscuss_text() {
        return discuss_text;
    }

    public int getDiscuss_state() {
        return discuss_state;
    }

    public int getDiscuss_like_count() {
        return discuss_like_count;
    }

    public int getDiscuss_reply_count() {
        return discuss_reply_count;
    }

    public int getDiscuss_reply_select_count() {
        return discuss_reply_select_count;
    }

    public List<WorkDiscussReply> getWorkDiscussReplyList() {
        return workDiscussReplyList;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public void setDiscuss_text(String discuss_text) {
        this.discuss_text = discuss_text;
    }

    public void setDiscuss_state(int discuss_state) {
        this.discuss_state = discuss_state;
    }

    public void setDiscuss_like_count(int discuss_like_count) {
        this.discuss_like_count = discuss_like_count;
    }

    public void setDiscuss_reply_count(int discuss_reply_count) {
        this.discuss_reply_count = discuss_reply_count;
    }

    public void setDiscuss_reply_select_count(int discuss_reply_select_count) {
        this.discuss_reply_select_count = discuss_reply_select_count;
    }

    public void setWorkDiscussReplyList(List<WorkDiscussReply> workDiscussReplyList) {
        this.workDiscussReplyList = workDiscussReplyList;
    }

    public int getDiscuss_id() {
        return discuss_id;
    }

    public void setDiscuss_id(int discuss_id) {
        this.discuss_id = discuss_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_nickname() {
        return user_nickname;
    }

    public String getUser_headimg() {
        return user_headimg;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setUser_nickname(String user_nickname) {
        this.user_nickname = user_nickname;
    }

    public void setUser_headimg(String user_headimg) {
        this.user_headimg = user_headimg;
    }

    public Time getDiscuss_time() {
        return discuss_time;
    }

    public void setDiscuss_time(Time discuss_time) {
        this.discuss_time=discuss_time;
    }
}
