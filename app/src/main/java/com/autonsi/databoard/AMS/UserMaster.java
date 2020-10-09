package com.autonsi.databoard.AMS;

public class UserMaster {

    String userid,usernm;

    public UserMaster(String userid, String usernm) {
        this.userid = userid;
        this.usernm = usernm;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsernm() {
        return usernm;
    }

    public void setUsernm(String usernm) {
        this.usernm = usernm;
    }
}
