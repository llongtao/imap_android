package com.test.hyxc.model;

import java.io.Serializable;

public class Time implements Serializable {
    private Integer date;
    private Integer hours;
    private Integer seconds;
    private Integer month;
    private String nanos;
    private String timezoneOffset;
    private Integer year;
    private Integer minutes;
    private Long time;
    private Integer day;

    public Integer getDate() {
        return date;
    }

    public Integer getHours() {
        return hours;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public Integer getMonth() {
        return month;
    }

    public String getNanos() {
        return nanos;
    }

    public String getTimezoneOffset() {
        return timezoneOffset;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public Long getTime() {
        return time;
    }

    public Integer getDay() {
        return day;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setNanos(String nanos) {
        this.nanos = nanos;
    }

    public void setTimezoneOffset(String timezoneOffset) {
        this.timezoneOffset = timezoneOffset;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
}
