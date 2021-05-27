package com.watchIt.Entity;

public class LoggedInUser {
    private User user;
    private UserProfile userProfile;

    public LoggedInUser( User user, UserProfile userProfile) {
        this.user = user;
        this.userProfile = userProfile;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public UserProfile getUserProfile(){
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile){
        this.userProfile = userProfile;
    }
}
