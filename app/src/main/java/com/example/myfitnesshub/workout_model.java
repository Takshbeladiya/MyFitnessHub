package com.example.myfitnesshub;

public class workout_model {

    String image_url, name, duration, exercise_type, description;

    workout_model(){
        //zero hour constructor for firebase
    }

    public workout_model(String image_url, String name, String duration, String exercise_type, String description) {
        this.image_url = image_url;
        this.name = name;
        this.duration = duration;
        this.exercise_type = exercise_type;
        this.description = description;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getExercise_type() {
        return exercise_type;
    }

    public void setExercise_type(String exercise_type) {
        this.exercise_type = exercise_type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
