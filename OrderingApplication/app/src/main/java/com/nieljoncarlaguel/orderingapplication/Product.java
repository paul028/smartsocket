package com.nieljoncarlaguel.orderingapplication;


public class Product {
    String productname, thumbnailUrl, description;
    int price;

    public Product() {

    }

    public Product(String name, String thumbnailUrl, int price, String description)
    {
        this.productname = name;
        this.thumbnailUrl = thumbnailUrl;
        this.price = price;
        this.description = description;

    }

    public void setProductName(String name) {
        this.productname = name;
    }



    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getProductName() {
        return productname;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
