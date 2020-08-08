package com.example.easynaukri;

public class HomeJobData {
    public String Title;
    public String Job;
    public int ImageId;

    public HomeJobData(String title, int imageId, String job) {
        Title = title;
        Job = job;
        ImageId = imageId;
    }

    public String getTitle() {
        return Title;
    }

    public String getJob() {
        return Job;
    }

    public int getImageId() {
        return ImageId;
    }
}
