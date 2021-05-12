package com.watchIt.Entity;

import com.watchIt.enums.RateStatus;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 8:39 오전
*/
public class MyContent {
    private int id;
    private RateStatus rateStatus;
    private int userProfileId;
    private int contentId;

    public MyContent(){};

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public RateStatus getRateStatus(){
        return rateStatus;
    }

    public void setRateStatus(RateStatus rateStatus){
        this.rateStatus=rateStatus;
    }

    public int getUserProfileId(){
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId){
        this.userProfileId = userProfileId;
    }

    public int getContentId(){
        return contentId;
    }

    public void setContentId(int contentId){
        this.contentId = contentId;
    }
}
