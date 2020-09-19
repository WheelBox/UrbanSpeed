package com.abhirajsharma.urbanspeed.model;

public class SliderModel {

    private String banner;
    private String tag;
    private String background;

    public SliderModel(String banner, String tag, String background) {
        this.banner = banner;
        this.tag = tag;
        this.background = background;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }
}
