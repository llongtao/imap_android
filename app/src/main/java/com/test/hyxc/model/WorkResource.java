package com.test.hyxc.model;

import java.io.Serializable;

public class WorkResource implements Serializable {
    private  Integer work_id;
    private String work_resource_url;

    public Integer getWork_id() {
        return work_id;
    }

    public String getWork_resource_url() {
        return work_resource_url;
    }

    public void setWork_id(Integer work_id) {
        this.work_id = work_id;
    }

    public void setWork_resource_url(String work_resource_url) {
        this.work_resource_url = work_resource_url;
    }
}
