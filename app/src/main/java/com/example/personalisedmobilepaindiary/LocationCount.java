package com.example.personalisedmobilepaindiary;

public class LocationCount {
    private String location;
    private int count;
    public  LocationCount (String location, int count){
        this.location = location;
        this.count = count;
    }

    public void setLocation (String newLocation) {
        location = newLocation;
    }

    public void setCount(int newCount) {
        count = newCount;
    }

    public String getLocation(){
        return location;
    }

    public int getCount(){
        return count;
    }
}
