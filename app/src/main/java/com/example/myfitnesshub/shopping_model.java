package com.example.myfitnesshub;

public class shopping_model {
    String title, url, rating, color, description;
    int price;

    shopping_model(){
        //zero hour constructor for firebase
    }

    public shopping_model(String title, String url, String rating, String color, String description, int price) {
        this.title = title;
        this.url = url;
        this.color = color;
        this.description = description;
        this.price = price;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
