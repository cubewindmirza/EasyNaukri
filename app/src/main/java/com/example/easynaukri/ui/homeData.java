package com.example.easynaukri.ui;

public class homeData {
    String Title;
    int Image;
    String Descprition;
    String Descprition1;
    String Descprition2;
    String Descprition3;
    String Descprition4;
    String Descprition5;
    String Amount;

    public homeData(String title, int image, String descprition, String descprition1, String descprition2,String descprition3,String descprition4,String descprition5,String amount) {
        Title = title;
        Image = image;
        Descprition = descprition;
        Descprition1 = descprition1;
        Descprition2 = descprition2;
        Descprition3=descprition3;
        Descprition4=descprition4;
        Descprition5=descprition5;
        Amount=amount;
    }

    public String getTitle() {
        return Title;
    }

    public int getImage() {
        return Image;
    }

    public String getDescprition() {
        return Descprition;
    }

    public String getDescprition1() {
        return Descprition1;
    }

    public String getDescprition2() {
        return Descprition2;
    }

    public String getDescprition3() {
        return Descprition3;
    }

    public String getDescprition4() {
        return Descprition4;
    }

    public String getDescprition5() {
        return Descprition5;
    }

    public String getAmount() {
        return Amount;
    }
}
