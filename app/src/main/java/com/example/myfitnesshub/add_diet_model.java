package com.example.myfitnesshub;

public class add_diet_model {
    String title, user_name, cook_time, description, image_url;
    int calories;

    public add_diet_model(String title, String user_name, String cook_time, String description, String image_url, int calories) {
        this.title = title;
        this.user_name = user_name;
        this.cook_time = cook_time;
        this.description = description;
        this.image_url = image_url;
        this.calories = calories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getCook_time() {
        return cook_time;
    }

    public void setCook_time(String cook_time) {
        this.cook_time = cook_time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
