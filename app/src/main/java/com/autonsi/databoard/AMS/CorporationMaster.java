package com.autonsi.databoard.AMS;

public class CorporationMaster {
    String company_name,company_cd;

    public CorporationMaster(String company_name, String company_cd) {
        this.company_name = company_name;
        this.company_cd = company_cd;
    }



    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_cd() {
        return company_cd;
    }

    public void setCompany_cd(String company_cd) {
        this.company_cd = company_cd;
    }
}
