package com.abhirajsharma.urbanspeed.model;

public class ShopModel  {

    String image,name,category,distance,rating,offer;

    public ShopModel(String image, String name, String category, String distance, String rating, String offer) {
        this.image = image;
        this.name = name;
        this.category = category;
        this.distance = distance;
        this.rating = rating;
        this.offer = offer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOffer() {
        return offer;
    }

    public void setOffer(String offer) {
        this.offer = offer;
    }
}
