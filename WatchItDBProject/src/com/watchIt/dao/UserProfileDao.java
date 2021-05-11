package com.watchIt.dao;

import com.watchIt.Entity.User;
import com.watchIt.Entity.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserProfileDao {
    private Connection conn;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "cindia3704";
    private static final String URL = "jdbc:mysql://localhost:3306/WatchIt?characterEncoding=latin1&useConfigs=maxPerformance";

    public UserProfileDao(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("Class not found!!");
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("Connection failed!!");
        }
    }

    public void insertUserProfile(UserProfile userProfile) throws SQLException {
        String sqlStmt = "insert into user_profile values(?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,userProfile.getId());
            pStmt.setString(2,userProfile.getNickname());
            pStmt.setInt(3,userProfile.getUserId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
