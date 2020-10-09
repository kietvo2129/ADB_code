package com.autonsi.databoard.AMS;

public class Floormaster {
    String lct_nm,lct_cd;

    public Floormaster(String lct_nm, String lct_cd) {
        this.lct_nm = lct_nm;
        this.lct_cd = lct_cd;
    }

    public String getLct_nm() {
        return lct_nm;
    }

    public void setLct_nm(String lct_nm) {
        this.lct_nm = lct_nm;
    }

    public String getLct_cd() {
        return lct_cd;
    }

    public void setLct_cd(String lct_cd) {
        this.lct_cd = lct_cd;
    }
}
