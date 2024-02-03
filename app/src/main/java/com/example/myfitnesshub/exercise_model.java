package com.example.myfitnesshub;

public class exercise_model {
    String title, description, url;
    int calories;

    exercise_model(){
        //zero hour constructor for firebase
    }

    public exercise_model(String title, String description, String url, int calories) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.calories = calories;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
