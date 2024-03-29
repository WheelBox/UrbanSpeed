package com.abhirajsharma.urbanspeed.model;

import java.util.ArrayList;

public class GroceryProductModel {

    String name,offertype,offerAmount,price,rating,reviewCount,id,tag_list,description,store_id;
    long stock;
    String image;
    private ArrayList<String> tags;
    public GroceryProductModel(String image, String name, String offertype, String offerAmount, String price, String rating, String reviewCount, long Stock, String id,String tag_list,String description,String store_id) {
        this.image=image;
        this.name = name;
        this.id=id;
        this.offertype = offertype;
        this.offerAmount = offerAmount;
        this.price = price;
        this.rating = rating;
        this.reviewCount=reviewCount;
        this.stock=Stock;
        this.tag_list=tag_list;
        this.description=description;
        this.store_id=store_id;

    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag_list() {
        return tag_list;
    }

    public void setTag_list(String tag_list) {
        this.tag_list = tag_list;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getOffertype() {
        return offertype;
    }

    public void setOffertype(String offertype) {
        this.offertype = offertype;
    }

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String offerAmount) {
        this.offerAmount = offerAmount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }
}
