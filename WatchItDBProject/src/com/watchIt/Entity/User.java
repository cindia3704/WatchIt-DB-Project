package com.watchIt.Entity;

import com.watchIt.enums.UserStatus;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 4:49 오전
*/
public class User {
    private int id;
    private String username;
    private String password;
    private int userAge;
    private UserStatus userStatus;

    public User(){};

    public User(Integer id, String name, String pw, Integer age, UserStatus userStatus) {
        this.id = id;
        this.userAge = age;
        this.username = name;
        this.password = pw;
        this.userStatus = userStatus;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public int getUserAge(){
        return userAge;
    }

    public void setUserAge(int userAge){
        this.userAge = userAge;
    }

    public UserStatus getUserStatus(){
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus){
        this.userStatus = userStatus;
    }

    @Override
    public String toString(){
        return "User [id=" + id + ", username="+username+", password="+password+", user_age="+userAge+", status="+userStatus+"]";
    }

}
