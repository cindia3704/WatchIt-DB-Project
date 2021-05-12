package com.watchIt.Entity;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 9:14 오전
*/
public class Rating {
    private int id;
    private double score;
    private int userProfileId;
    private int contentId;

    public Rating(){};

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getScore(){
        return score;
    }

    public void setScore(double score){
        this.score = score;
    }

    public int getUserProfileId(){
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId){
        this.userProfileId=userProfileId;
    }

    public int getContentId(){
        return contentId;
    }

    public void setContentId(int contentId){
        this.contentId=contentId;
    }
}
