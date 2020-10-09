package com.autonsi.databoard.AMS;

public class BuildingMaster {

    String Building_nm,Building_co,category;

    public BuildingMaster(String building_nm, String building_co, String category) {
        Building_nm = building_nm;
        Building_co = building_co;
        this.category = category;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBuilding_nm() {
        return Building_nm;
    }

    public void setBuilding_nm(String building_nm) {
        Building_nm = building_nm;
    }

    public String getBuilding_co() {
        return Building_co;
    }

    public void setBuilding_co(String building_co) {
        Building_co = building_co;
    }
}
