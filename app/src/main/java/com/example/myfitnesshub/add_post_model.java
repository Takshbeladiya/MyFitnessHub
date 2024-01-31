package com.example.myfitnesshub;

public class add_post_model {
    String title, image_url, user_name, blog_description;

    add_post_model(){

    }

    public add_post_model(String title, String image_url, String user_name, String blog_description) {
        this.title = title;
        this.image_url = image_url;
        this.user_name = user_name;
        this.blog_description = blog_description;
    }

    public String getBlog_description() {
        return blog_description;
    }

    public void setBlog_description(String blog_description) {
        this.blog_description = blog_description;
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
