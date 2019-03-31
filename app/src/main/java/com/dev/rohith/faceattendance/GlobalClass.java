package com.dev.rohith.faceattendance;

import android.app.Application;

public class GlobalClass extends Application{

    private String name;
    private double m1;
    private double m2;
    private double m3;
    private double m4;

    public String getVehicle() {

        return name;
    }

    public void setVehicle(String aName) {

        name = aName;

    }

    public void getMass(){

    }



}