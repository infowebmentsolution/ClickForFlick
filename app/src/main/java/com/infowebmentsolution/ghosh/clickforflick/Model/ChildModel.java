package com.infowebmentsolution.ghosh.clickforflick.Model;

public class ChildModel {
    private  String videoImage;
    private String movieName;

    public ChildModel(String videoImage, String movieName){
        this.videoImage = videoImage;
        this.movieName = movieName;
    }
    public String getVideoImage() {
        return videoImage;
    }
    public String getMovieName() {
        return movieName;
    }
}
