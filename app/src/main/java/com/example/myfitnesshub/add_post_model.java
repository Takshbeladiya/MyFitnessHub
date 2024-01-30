package com.example.myfitnesshub;

public class add_post_model {
    String title, image_url, user_name;

    add_post_model(){

    }

    public add_post_model(String title, String image_url, String user_name) {
        this.title = title;
        this.image_url = image_url;
        this.user_name = user_name;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
