package com.watchIt.Entity;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:07 오전
*/

import com.watchIt.enums.ContentGenre;
import com.watchIt.enums.ContentType;

public class Content {
    private int id;
    private ContentType contentType;
    private ContentGenre contentGenre;
    private String title;
    private int year;
    private String description;
    private String poster;
    private String video;
    private double totalRateScore;
    private int ageLimit;

    public Content(){};

    public Content(Integer id, ContentType type, ContentGenre genre, String title, Integer year, String description, String poster, String video, double rateScore, Integer ageLimit) {
        this.id=id;
        this.title = title;
        this.contentType = type;
        this.contentGenre = genre;
        this.description = description;
        this.poster = poster;
        this.year = year;
        this.totalRateScore = rateScore;
        this.video = video;
        this.ageLimit = ageLimit;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public ContentType getContentType(){
        return contentType;
    }

    public void setContentType(ContentType contentType){
        this.contentType = contentType;
    }

    public ContentGenre getContentGenre(){
        return contentGenre;
    }

    public void setContentGenre(ContentGenre contentGenre){
        this.contentGenre = contentGenre;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year=year;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description=description;
    }

    public String getPoster(){
        return poster;
    }

    public void setPoster(String poster){
        this.poster=poster;
    }

    public String getVideo(){
        return video;
    }

    public void setVideo(String video){
        this.video=video;
    }

    public double getTotalRateScore(){
        return totalRateScore;
    }

    public void setTotalRateScore(double totalRateScore){
        this.totalRateScore=totalRateScore;
    }

    public int getAgeLimit(){
        return ageLimit;
    }

    public void setAgeLimit(int ageLimit){
        this.ageLimit = ageLimit;
    }
}
