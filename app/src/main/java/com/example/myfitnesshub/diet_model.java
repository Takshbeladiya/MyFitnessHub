package com.example.myfitnesshub;

public class diet_model {
    String  cook_time, title, image_url;
    int calories;

    diet_model(){
        //zero hour constructor for firebase
    }

    public diet_model(String cook_time, String title, String image_url, int calories) {
        this.cook_time = cook_time;
        this.title = title;
        this.image_url = image_url;
        this.calories = calories;
    }

    public String getCook_time() {
        return cook_time;
    }

    public void setCook_time(String cook_time) {
        this.cook_time = cook_time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }
}
