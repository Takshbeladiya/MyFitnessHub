package com.example.myfitnesshub;

public class blog_model {

    String title, image_url;

    blog_model(){
        //zero hour constructor for firebase
    }

    public blog_model(String title, String image_url) {
        this.title = title;
        this.image_url = image_url;
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
}