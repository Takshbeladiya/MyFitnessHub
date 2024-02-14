package com.example.myfitnesshub;

public class shopping_cart_model {
    String product, user_name;
    int quantity;

    shopping_cart_model(){
        //zero hour constructor for firebase
    }

    public shopping_cart_model(String product, String user_name, int quantity) {
        this.product = product;
        this.user_name = user_name;
        this.quantity = quantity;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
