package com.watchIt.Entity;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 6:49 오전
*/

public class UserProfile {
    private int id;
    private String nickname;
    private int userId;

    public UserProfile(){};

    public UserProfile(Integer id, String nickname, Integer userId) {
        this.id = id;
        this.nickname = nickname;
        this.userId = userId;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int id){
        this.userId = id;
    }

    public String getNickname(){
        return nickname;
    }

    public void setNickname(String nickname){
        this.nickname=nickname;
    }
}
