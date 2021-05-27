package com.watchIt.dao;

import com.watchIt.Entity.User;
import com.watchIt.Entity.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

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
        Scanner sc= new Scanner(System.in);
        String sqlStmt = "select * from user_profile where user_id = ?;";
        PreparedStatement pStmt = null;
        String next = "ERR";
        try {
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1, user.getId());
            ResultSet rs = pStmt.executeQuery();
            Integer index = 1;
            System.out.println("Please choose 1 of the userProfiles\n");
            while(rs.next()) {
                Integer id = rs.getInt(1);
                String nickname = rs.getString(2);
                Integer userId = rs.getInt(3);
                UserProfile userProfile = new UserProfile(id, nickname, userId);
                System.out.println(index.toString()+") "+userProfile.getNickname());
                index++;

            }
            System.out.print("User profile number: ");
            Integer profileNum = sc.nextInt();
            while(profileNum < 0 && profileNum >=index){
                System.out.println("Wrong input.. Try agian");
                System.out.print("User profile number: ");
                profileNum = sc.nextInt();
            }
            next = profileNum.toString();

        } catch(SQLException e) {
            e.printStackTrace();
        }finally {
            pStmt.close();
            sc.close();
        }
        return next;
    }

}
