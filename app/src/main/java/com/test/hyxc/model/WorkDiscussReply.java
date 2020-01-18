package com.test.hyxc.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class WorkDiscussReply implements Serializable {
    private  int work_id;
    private  int discuss_id;
    private  int reply_from_id;
    private  int reply_to_id;
    private String reply_text;
    private Time reply_time;
    private int reply_state;
    ///
    private String from_headimg;
    private String from_nickname;
    private String to_nickname;
    private String to_headimg;


    public int getWork_id() {
        return work_id;
    }

    public int getDiscuss_id() {
        return discuss_id;
    }

    public int getReply_from_id() {
        return reply_from_id;
    }

    public int getReply_to_id() {
        return reply_to_id;
    }

    public String getReply_text() {
        return reply_text;
    }


    public int getReply_state() {
        return reply_state;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }

    public void setDiscuss_id(int discuss_id) {
        this.discuss_id = discuss_id;
    }

    public void setReply_from_id(int reply_from_id) {
        this.reply_from_id = reply_from_id;
    }

    public void setReply_to_id(int reply_to_id) {
        this.reply_to_id = reply_to_id;
    }

    public void setReply_text(String reply_text) {
        this.reply_text = reply_text;
    }


    public void setReply_state(int reply_state) {
        this.reply_state = reply_state;
    }

    public Time getReply_time() {
        return reply_time;
    }

    public void setReply_time(Time reply_time) {
        this.reply_time=reply_time;
    }

    public String getFrom_headimg() {
        return from_headimg;
    }

    public String getFrom_nickname() {
        return from_nickname;
    }

    public String getTo_nickname() {
        return to_nickname;
    }

    public String getTo_headimg() {
        return to_headimg;
    }

    public void setFrom_headimg(String from_headimg) {
        this.from_headimg=from_headimg;
    }

    public void setFrom_nickname(String from_nickname) {
        this.from_nickname=from_nickname;
    }

    public void setTo_nickname(String to_nickname) {
        this.to_nickname=to_nickname;
    }

    public void setTo_headimg(String to_headimg) {
        this.to_headimg=to_headimg;
    }
}
