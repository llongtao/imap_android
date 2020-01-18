package com.test.hyxc.model;

import java.io.Serializable;

/**
 * Created by Mac on 11/16/19.
 */

public class Version implements Serializable{
    Integer version_id;
    String version_code;
    String version_name;
    String version_reason;
    String client_type;
    String client_name;
    String download_path;
    Time insert_time;
    Time update_time;

    public Integer getVersion_id() {
        return version_id;
    }

    public String getVersion_code() {
        return version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public String getVersion_reason() {
        return version_reason;
    }

    public String getClient_type() {
        return client_type;
    }

    public String getClient_name() {
        return client_name;
    }

    public Time getInsert_time() {
        return insert_time;
    }

    public Time getUpdate_time() {
        return update_time;
    }

    public void setVersion_id(Integer version_id) {
        this.version_id = version_id;
    }

    public void setVersion_code(String version_code) {
        this.version_code = version_code;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public void setVersion_reason(String version_reason) {
        this.version_reason = version_reason;
    }

    public void setClient_type(String client_type) {
        this.client_type = client_type;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public void setInsert_time(Time insert_time) {
        this.insert_time = insert_time;
    }

    public void setUpdate_time(Time update_time) {
        this.update_time = update_time;
    }

    public String getDownload_path() {
        return download_path;
    }

    public void setDownload_path(String download_path) {
        this.download_path = download_path;
    }
}
