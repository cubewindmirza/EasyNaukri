package com.example.easynaukri.ui;

public class HomeDataList {
    public String title;
    public int image;
    public String desp;




    public HomeDataList(String title, int image, String desp) {
        this.title = title;
        this.image = image;
        this.desp = desp;

    }

    public String getTitle() {
        return title;
    }

    public int getImage() {
        return image;
    }

    public String getDesp() {
        return desp;
    }

}
