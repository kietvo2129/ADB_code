package com.quickblox.sample.videochat.java.Counting.CountList;

public class CountListItem {

    private String Id;
    private String LineId;
    private String LineName;
    private String Efficiency;
    private String Actual;
    private String Defective;
    private String Target;
    private String TargetHour;

    public CountListItem(String id, String lineId, String lineName, String efficiency, String actual, String defective, String target, String targetHour) {
        Id = id;
        LineId = lineId;
        LineName = lineName;
        Efficiency = efficiency;
        Actual = actual;
        Defective = defective;
        Target = target;
        TargetHour = targetHour;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getLineId() {
        return LineId;
    }

    public void setLineId(String lineId) {
        LineId = lineId;
    }

    public String getLineName() {
        return LineName;
    }

    public void setLineName(String lineName) {
        LineName = lineName;
    }

    public String getEfficiency() {
        return Efficiency;
    }

    public void setEfficiency(String efficiency) {
        Efficiency = efficiency;
    }

    public String getActual() {
        return Actual;
    }

    public void setActual(String actual) {
        Actual = actual;
    }

    public String getDefective() {
        return Defective;
    }

    public void setDefective(String defective) {
        Defective = defective;
    }

    public String getTarget() {
        return Target;
    }

    public void setTarget(String target) {
        Target = target;
    }

    public String getTargetHour() {
        return TargetHour;
    }

    public void setTargetHour(String targetHour) {
        TargetHour = targetHour;
    }
}

