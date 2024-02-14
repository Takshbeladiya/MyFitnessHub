package com.example.myfitnesshub;

public class exercise_model {
    String image_url, name, time, exercise_type, type;

    exercise_model(){
        //zero hour constructor for firebase
    }

    public exercise_model(String image_url, String name, String time, String exercise_type, String type) {
        this.image_url = image_url;
        this.name = name;
        this.time = time;
        this.exercise_type = exercise_type;
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getExercise_type() {
        return exercise_type;
    }

    public void setExercise_type(String exercise_type) {
        this.exercise_type = exercise_type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
