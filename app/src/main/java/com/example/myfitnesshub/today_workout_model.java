package com.example.myfitnesshub;

public class today_workout_model {

    String name, type;

    today_workout_model(){
        //zero hour constructor for firebase
    }

    public today_workout_model(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
