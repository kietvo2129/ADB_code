package com.quickblox.sample.videochat.java.Counting.Count.Line;

public class LineMaster {
    String id,line_no,line_nm;

    public LineMaster(String id, String line_no, String line_nm) {
        this.id = id;
        this.line_no = line_no;
        this.line_nm = line_nm;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLine_no() {
        return line_no;
    }

    public void setLine_no(String line_no) {
        this.line_no = line_no;
    }

    public String getLine_nm() {
        return line_nm;
    }

    public void setLine_nm(String line_nm) {
        this.line_nm = line_nm;
    }
}
