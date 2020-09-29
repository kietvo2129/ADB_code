package com.autonsi.databoard.Counting.Count.QCCheck;

public class QCcheckMaster {

    String qc_check_subject_id,qc_check_subject_code,qc_check_subject_name,qc_check_subject_master_code,qc_check_subject_master_type,
    qc_check_detail_id,qc_check_detail_code,qc_check_detail_name;
    int tong;

    public QCcheckMaster(String qc_check_subject_id, String qc_check_subject_code, String qc_check_subject_name, String qc_check_subject_master_code, String qc_check_subject_master_type, String qc_check_detail_id, String qc_check_detail_code, String qc_check_detail_name,int tong) {
        this.qc_check_subject_id = qc_check_subject_id;
        this.qc_check_subject_code = qc_check_subject_code;
        this.qc_check_subject_name = qc_check_subject_name;
        this.qc_check_subject_master_code = qc_check_subject_master_code;
        this.qc_check_subject_master_type = qc_check_subject_master_type;
        this.qc_check_detail_id = qc_check_detail_id;
        this.qc_check_detail_code = qc_check_detail_code;
        this.qc_check_detail_name = qc_check_detail_name;
        this.tong = tong;
    }

    public int getTong() {
        return tong;
    }

    public void setTong(int tong) {
        this.tong = tong;
    }

    public String getQc_check_subject_id() {
        return qc_check_subject_id;
    }

    public void setQc_check_subject_id(String qc_check_subject_id) {
        this.qc_check_subject_id = qc_check_subject_id;
    }

    public String getQc_check_subject_code() {
        return qc_check_subject_code;
    }

    public void setQc_check_subject_code(String qc_check_subject_code) {
        this.qc_check_subject_code = qc_check_subject_code;
    }

    public String getQc_check_subject_name() {
        return qc_check_subject_name;
    }

    public void setQc_check_subject_name(String qc_check_subject_name) {
        this.qc_check_subject_name = qc_check_subject_name;
    }

    public String getQc_check_subject_master_code() {
        return qc_check_subject_master_code;
    }

    public void setQc_check_subject_master_code(String qc_check_subject_master_code) {
        this.qc_check_subject_master_code = qc_check_subject_master_code;
    }

    public String getQc_check_subject_master_type() {
        return qc_check_subject_master_type;
    }

    public void setQc_check_subject_master_type(String qc_check_subject_master_type) {
        this.qc_check_subject_master_type = qc_check_subject_master_type;
    }

    public String getQc_check_detail_id() {
        return qc_check_detail_id;
    }

    public void setQc_check_detail_id(String qc_check_detail_id) {
        this.qc_check_detail_id = qc_check_detail_id;
    }

    public String getQc_check_detail_code() {
        return qc_check_detail_code;
    }

    public void setQc_check_detail_code(String qc_check_detail_code) {
        this.qc_check_detail_code = qc_check_detail_code;
    }

    public String getQc_check_detail_name() {
        return qc_check_detail_name;
    }

    public void setQc_check_detail_name(String qc_check_detail_name) {
        this.qc_check_detail_name = qc_check_detail_name;
    }
}
