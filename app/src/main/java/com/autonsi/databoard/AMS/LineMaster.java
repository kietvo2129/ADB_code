package com.autonsi.databoard.AMS;

public class LineMaster {
    String line_nm,line_no;

    public LineMaster(String line_nm, String line_no) {
        this.line_nm = line_nm;
        this.line_no = line_no;
    }

    public String getLine_nm() {
        return line_nm;
    }

    public void setLine_nm(String line_nm) {
        this.line_nm = line_nm;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }
}
