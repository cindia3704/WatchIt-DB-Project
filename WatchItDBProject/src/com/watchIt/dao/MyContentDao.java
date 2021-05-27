package com.watchIt.dao;

import com.watchIt.Entity.LoggedInUser;
import com.watchIt.Entity.MyContent;
import com.watchIt.Entity.SearchHistory;
import com.watchIt.enums.RateStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 8:41 오전
*/
public class MyContentDao {
    public static void insertMyContent(MyContent myContent,Connection conn) throws SQLException {
        String sqlStmt = "insert into my_content values(?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,myContent.getId());
            pStmt.setString(2,myContent.getRateStatus().toString());
            pStmt.setInt(3,myContent.getUserProfileId());
            pStmt.setInt(4,myContent.getContentId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
    public static void deleteContentFromMyList(Connection conn, Integer contentId) throws SQLException {
        conn.setAutoCommit(false); // default true
        String sqlStmt = "delete from my_content where content_id =?";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,contentId);
            pStmt.executeUpdate();

        }catch (SQLException e){
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
            pStmt.close();
            conn.commit();
            conn.setAutoCommit(true);
        }
    }

    public static List<MyContent> getMyContentList(Scanner sc, Connection conn, LoggedInUser loggedInUser)throws SQLException {
        conn.setAutoCommit(false); // default true
        String sqlStmt = "select * from my_content where user_profile_id = ?;";
        PreparedStatement pStmt = null;
        List<MyContent> myContentList = new ArrayList<MyContent>();
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,loggedInUser.getUserProfile().getId());
            ResultSet rs =pStmt.executeQuery();
            while (rs.next()){
                Integer id = rs.getInt(1);
                String status = rs.getString(2);
                RateStatus rateStatus=null;
                if(status.equals("DONE")){
                    rateStatus = RateStatus.DONE;
                }else{
                    rateStatus = RateStatus.NOT_DONE;
                }
                Integer userProfileId = rs.getInt(3);
                Integer contentID = rs.getInt(4);
                MyContent myContent = new MyContent(id,rateStatus,userProfileId,contentID);
                myContentList.add(myContent);
            }
        }catch (SQLException e){
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
            e.printStackTrace();
        }finally {
            pStmt.close();
            conn.commit();
            conn.setAutoCommit(true);
        }
        return myContentList;
    }
}
