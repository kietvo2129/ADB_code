package com.autonsi.databoard.AMS;

public class MCDetailMaster {
    String MachineNo,MachineName,MachineTypeName,Description,SerialNumber,PurchaseDate,ValidDate
            ,Image,MachineStatusName,ReferenceLink,Document;

    public MCDetailMaster(String machineNo, String machineName, String machineTypeName, String description, String serialNumber, String purchaseDate, String validDate, String image, String machineStatusName, String referenceLink, String document) {
        MachineNo = machineNo;
        MachineName = machineName;
        MachineTypeName = machineTypeName;
        Description = description;
        SerialNumber = serialNumber;
        PurchaseDate = purchaseDate;
        ValidDate = validDate;
        Image = image;
        MachineStatusName = machineStatusName;
        ReferenceLink = referenceLink;
        Document = document;
    }

    public String getMachineNo() {
        return MachineNo;
    }

    public void setMachineNo(String machineNo) {
        MachineNo = machineNo;
    }

    public String getMachineName() {
        return MachineName;
    }

    public void setMachineName(String machineName) {
        MachineName = machineName;
    }

    public String getMachineTypeName() {
        return MachineTypeName;
    }

    public void setMachineTypeName(String machineTypeName) {
        MachineTypeName = machineTypeName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getValidDate() {
        return ValidDate;
    }

    public void setValidDate(String validDate) {
        ValidDate = validDate;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getMachineStatusName() {
        return MachineStatusName;
    }

    public void setMachineStatusName(String machineStatusName) {
        MachineStatusName = machineStatusName;
    }

    public String getReferenceLink() {
        return ReferenceLink;
    }

    public void setReferenceLink(String referenceLink) {
        ReferenceLink = referenceLink;
    }

    public String getDocument() {
        return Document;
    }

    public void setDocument(String document) {
        Document = document;
    }
}
