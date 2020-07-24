package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor;


public class MapInfoSensor {
    String ssid,ss_no,ss_nm,current_temp,current_press,current_pow,current_humi,time_update,
            min_temp,max_temp,min_press,max_press,min_humi,max_humi,min_pow,max_pow;

    public MapInfoSensor(String ssid, String ss_no, String ss_nm, String current_temp, String current_press, String current_pow, String current_humi, String time_update, String min_temp, String max_temp, String min_press, String max_press, String min_humi, String max_humi, String min_pow, String max_pow) {
        this.ssid = ssid;
        this.ss_no = ss_no;
        this.ss_nm = ss_nm;
        this.current_temp = current_temp;
        this.current_press = current_press;
        this.current_pow = current_pow;
        this.current_humi = current_humi;
        this.time_update = time_update;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.min_press = min_press;
        this.max_press = max_press;
        this.min_humi = min_humi;
        this.max_humi = max_humi;
        this.min_pow = min_pow;
        this.max_pow = max_pow;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getSs_no() {
        return ss_no;
    }

    public void setSs_no(String ss_no) {
        this.ss_no = ss_no;
    }

    public String getSs_nm() {
        return ss_nm;
    }

    public void setSs_nm(String ss_nm) {
        this.ss_nm = ss_nm;
    }

    public String getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(String current_temp) {
        this.current_temp = current_temp;
    }

    public String getCurrent_press() {
        return current_press;
    }

    public void setCurrent_press(String current_press) {
        this.current_press = current_press;
    }

    public String getCurrent_pow() {
        return current_pow;
    }

    public void setCurrent_pow(String current_pow) {
        this.current_pow = current_pow;
    }

    public String getCurrent_humi() {
        return current_humi;
    }

    public void setCurrent_humi(String current_humi) {
        this.current_humi = current_humi;
    }

    public String getTime_update() {
        return time_update;
    }

    public void setTime_update(String time_update) {
        this.time_update = time_update;
    }

    public String getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(String min_temp) {
        this.min_temp = min_temp;
    }

    public String getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(String max_temp) {
        this.max_temp = max_temp;
    }

    public String getMin_press() {
        return min_press;
    }

    public void setMin_press(String min_press) {
        this.min_press = min_press;
    }

    public String getMax_press() {
        return max_press;
    }

    public void setMax_press(String max_press) {
        this.max_press = max_press;
    }

    public String getMin_humi() {
        return min_humi;
    }

    public void setMin_humi(String min_humi) {
        this.min_humi = min_humi;
    }

    public String getMax_humi() {
        return max_humi;
    }

    public void setMax_humi(String max_humi) {
        this.max_humi = max_humi;
    }

    public String getMin_pow() {
        return min_pow;
    }

    public void setMin_pow(String min_pow) {
        this.min_pow = min_pow;
    }

    public String getMax_pow() {
        return max_pow;
    }

    public void setMax_pow(String max_pow) {
        this.max_pow = max_pow;
    }
}
