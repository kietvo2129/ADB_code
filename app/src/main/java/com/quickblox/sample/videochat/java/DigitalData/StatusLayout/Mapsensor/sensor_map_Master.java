package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor;

public class sensor_map_Master {
    String SensorID,SensorName,Temperature,Humidity,Power,Pressure;

    public sensor_map_Master(String sensorID, String sensorName, String temperature, String humidity, String power, String pressure) {
        SensorID = sensorID;
        SensorName = sensorName;
        Temperature = temperature;
        Humidity = humidity;
        Power = power;
        Pressure = pressure;
    }

    public String getSensorID() {
        return SensorID;
    }

    public void setSensorID(String sensorID) {
        SensorID = sensorID;
    }

    public String getSensorName() {
        return SensorName;
    }

    public void setSensorName(String sensorName) {
        SensorName = sensorName;
    }

    public String getTemperature() {
        return Temperature;
    }

    public void setTemperature(String temperature) {
        Temperature = temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getPower() {
        return Power;
    }

    public void setPower(String power) {
        Power = power;
    }

    public String getPressure() {
        return Pressure;
    }

    public void setPressure(String pressure) {
        Pressure = pressure;
    }
}
