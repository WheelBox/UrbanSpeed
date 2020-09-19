package com.abhirajsharma.urbanspeed.model;

public class AddressModel {
    private String name, phone, type, details, alternatePhn;
    private int size;

    public AddressModel(String name, String phone, String type, String details, String alternatePhn, int size) {
        this.name = name;
        this.phone = phone;
        this.type = type;
        this.details = details;
        this.alternatePhn = alternatePhn;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAlternatePhn() {
        return alternatePhn;
    }

    public void setAlternatePhn(String altrtnatePhn) {
        this.alternatePhn = altrtnatePhn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
