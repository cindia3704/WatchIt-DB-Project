package com.watchIt.Entity;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 9:04 오전
*/
public class ContentComment {
    private int id;
    private String comment;
    private int userProfileId;
    private int contentId;

    public ContentComment(){};

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getComment(){
        return comment;
    }

    public void setComment(String comment){
        this.comment = comment;
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
