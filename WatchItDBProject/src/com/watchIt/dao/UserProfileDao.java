package com.watchIt.dao;

import com.watchIt.Entity.User;
import com.watchIt.Entity.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserProfileDao {
    public static void insertUserProfile(UserProfile userProfile, Connection conn) throws SQLException {
        String sqlStmt = "insert into user_profile values(?,?,?);";
        PreparedStatement pStmt = null;
        try {
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1, userProfile.getId());
            pStmt.setString(2, userProfile.getNickname());
            pStmt.setInt(3, userProfile.getUserId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            pStmt.close();
        }
    }

    public static String getUserProfile(User user, Connection conn) throws SQLException {
        String sqlStmt = "select * from user_profile where user_id = ?;";
        PreparedStatement pStmt = null;
        String next = "ERR";
        try {
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1, user.getId());
            ResultSet rs = pStmt.executeQuery();

        } catch(SQLException e) {
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
        return next;
    }

}
