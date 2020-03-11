package com.example.android.quakereport;

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private long mDateMilliSeconds;

    public Earthquake(double magnitude, String location, long dateMilliSeconds){
        this.mMagnitude = magnitude;
        this.mLocation = location;
        this.mDateMilliSeconds = dateMilliSeconds;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public Long getTimeInMilliSeconds() {
        return mDateMilliSeconds;
    }
}
