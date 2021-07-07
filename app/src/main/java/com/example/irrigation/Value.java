package com.example.irrigation;

public class Value {

    String Humidity,Moisture,Temperture;

    public Value() {
    }

    public Value(String humidity, String moisture, String temperture) {
        Humidity = humidity;
        Moisture = moisture;
        Temperture = temperture;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getMoisture() {
        return Moisture;
    }

    public void setMoisture(String moisture) {
        Moisture = moisture;
    }

    public String getTemperture() {
        return Temperture;
    }

    public void setTemperture(String temperture) {
        Temperture = temperture;
    }
}
