package com.test.hyxc.model;

import java.io.Serializable;

/**
 * Created by WWW on 2019/1/23.
 */

public class ActivityResource implements Serializable {
    private  Integer act_id;
    private  String act_resource_url;

    public Integer getAct_id() {
        return act_id;
    }

    public String getAct_resource_url() {
        return act_resource_url;
    }

    public void setAct_id(Integer act_id) {
        this.act_id = act_id;
    }

    public void setAct_resource_url(String act_resource_url) {
        this.act_resource_url = act_resource_url;
    }
}
