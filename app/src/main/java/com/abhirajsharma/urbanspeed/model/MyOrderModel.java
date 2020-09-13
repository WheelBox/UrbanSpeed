package com.abhirajsharma.urbanspeed.model;

public class MyOrderModel {

    private String name, deliveryStat, description, product_id, order_id, rating, review, otp;
    private String image;
    private boolean is_cancled, is_deliverd;

    public MyOrderModel(String name, String deliveryStat, String image, String description, String order_id, String product_id, String rating, String review, boolean is_cancled, boolean is_deliverd, String otp) {
        this.name = name;
        this.deliveryStat = deliveryStat;
        this.image = image;
        this.description = description;
        this.order_id = order_id;
        this.product_id = product_id;
        this.rating = rating;
        this.review = review;
        this.is_cancled = is_cancled;
        this.is_deliverd = is_deliverd;
        this.otp = otp;

    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public boolean isIs_cancled() {
        return is_cancled;
    }

    public void setIs_cancled(boolean is_cancled) {
        this.is_cancled = is_cancled;
    }

    public boolean isIs_deliverd() {
        return is_deliverd;
    }

    public void setIs_deliverd(boolean is_deliverd) {
        this.is_deliverd = is_deliverd;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeliveryStat() {
        return deliveryStat;
    }

    public void setDeliveryStat(String deliveryStat) {
        this.deliveryStat = deliveryStat;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
