package com.watchIt.Entity;

import java.sql.Date;
import java.time.LocalDate;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:49 오전
*/
public class SearchHistory {
    private int id;
    private String searchKey;
    private Date searchedDate;
    private int userProfileId;

    public SearchHistory(){};

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getSearchKey(){
        return searchKey;
    }

    public void setSearchKey(String searchKey){
        this.searchKey = searchKey;
    }

    public Date getSearchedDate(){
        return searchedDate;
    }

    public void setSearchedDate(Date searchedDate){
        this.searchedDate = searchedDate;
    }

    public int getUserProfileId(){
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId){
        this.userProfileId = userProfileId;
    }
}
