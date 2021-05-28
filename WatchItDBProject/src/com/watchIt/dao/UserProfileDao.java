package com.watchIt.dao;

import com.watchIt.Entity.LoggedInUser;
import com.watchIt.Entity.User;
import com.watchIt.Entity.UserProfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public static LoggedInUser getUserProfile(Scanner sc,User user, Connection conn) throws SQLException {
        String sqlStmt = "select * from user_profile where user_id = ?;";
        PreparedStatement pStmt = null;
        LoggedInUser loggedInUser =null;
        try {
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1, user.getId());
            ResultSet rs = pStmt.executeQuery();
            Integer index = 1;
            System.out.println("\nPlease choose 1 of the userProfiles:");
            List<UserProfile> userProfileList = new ArrayList<UserProfile>();
            while(rs.next()) {
                Integer id = rs.getInt(1);
                String nickname = rs.getString(2);
                Integer userId = rs.getInt(3);
                UserProfile userProfile = new UserProfile(id, nickname, userId);
                userProfileList.add(userProfile);
                System.out.println(index.toString()+") "+userProfile.getNickname());
                index++;
            }

            System.out.print("User profile number: ");
            String profileString =null;
            profileString=sc.nextLine();
            Integer profileNum = 0;
            try{
                profileNum =Integer.parseInt(profileString);
            }
            catch(NumberFormatException ex){
                System.out.println("Its not a valid Integer");
            }
            while(profileNum < 0 && profileNum >=index){
                System.out.println("Wrong input.. Try agian");
                System.out.print("User profile number: ");
                profileString = sc.nextLine();
                try{
                    profileNum =Integer.parseInt(profileString);
                }
                catch(NumberFormatException ex){
                    System.out.println("Its not a valid Integer");
                }
            }
            loggedInUser = new LoggedInUser(user, userProfileList.get(profileNum - 1));

        } catch(SQLException e) {
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
        return loggedInUser;
    }

    public static void deleteUserProfile(UserProfile userProfile, Connection conn) throws SQLException {
        conn.setAutoCommit(false);
        String sqlStmt = "delete from content_comment where user_profile_id = ?";
        PreparedStatement pStmt = null;
        try {
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1, userProfile.getId());
            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        }
        String sqlStmt2 = "delete from rating where user_profile_id = ?";
        PreparedStatement pStmt2 = null;
        try {
            pStmt2 = conn.prepareStatement(sqlStmt2);
            pStmt2.setInt(1, userProfile.getId());
            pStmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        }
        String sqlStmt3 = "delete from search_history where user_profile_id = ?";
        PreparedStatement pStmt3 = null;
        try {
            pStmt3 = conn.prepareStatement(sqlStmt3);
            pStmt3.setInt(1, userProfile.getId());
            pStmt3.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        }
        String sqlStmts = "delete from my_content where user_profile_id = ?";
        PreparedStatement pStmts = null;
        try {
            pStmts = conn.prepareStatement(sqlStmts);
            pStmts.setInt(1, userProfile.getId());
            pStmts.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        }
        String sqlStmt4 = "delete from user_profile where id = ?";
        PreparedStatement pStmt4 = null;
        try {
            pStmt4 = conn.prepareStatement(sqlStmt4);
            pStmt4.setInt(1, userProfile.getId());
            pStmt4.executeUpdate();
            System.out.println("User profile deleted successfully...");
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        }finally {
            pStmt2.close();
            pStmt.close();
            pStmt3.close();
            pStmt4.close();
            conn.commit();
            conn.setAutoCommit(true);
        }
    }

}
