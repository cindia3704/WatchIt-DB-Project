package com.watchIt.dao;

import com.watchIt.Entity.Content;
import com.watchIt.Entity.LoggedInUser;
import com.watchIt.Entity.SearchHistory;
import com.watchIt.Entity.Ticket;
import com.watchIt.enums.ContentGenre;
import com.watchIt.enums.ContentType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:51 오전
*/
public class SearchHistoryDao {

    public static void insertSearchHistory(SearchHistory searchHistory,Connection conn) throws SQLException {
        String sqlStmt = "insert into search_history values(?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,searchHistory.getId());
            pStmt.setString(2,searchHistory.getSearchKey());
            pStmt.setDate(3,searchHistory.getSearchedDate());
            pStmt.setInt(4,searchHistory.getUserProfileId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }

    public static void updateSearchHistory(Connection conn, LoggedInUser loggedInUser, String searchKey) throws  SQLException{
        conn.setAutoCommit(false);
        String sqlStmt = "select * from search_history where user_profile_id = ? order by searched_date DESC ";
        PreparedStatement pStmt = null;
        List <SearchHistory> searchHistories = new ArrayList<SearchHistory>();
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,loggedInUser.getUserProfile().getId());
            ResultSet rs = pStmt.executeQuery();
            System.out.println("YOUR SEARCH HISTORIES: ");
            while(rs.next()){
                Integer id = rs.getInt(1);
                String key = rs.getString(2);
                Date date = rs.getDate(3);
                Integer userID = rs.getInt(4);
                SearchHistory searchHistory = new SearchHistory(id,key,date,userID);
                searchHistories.add(searchHistory);
                System.out.println("[SEARCH KEY] : "+searchHistory.getSearchKey()+ "      [SEARCHED AT] :"+searchHistory.getSearchedDate().toString());
            }
        } catch (SQLException e){
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
        String sqlStmt2 = "select id from search_history where user_profile_id = ? and search_key =?";
        PreparedStatement pStmt2 = null;
        Integer id = null;
        try{
            pStmt2 = conn.prepareStatement(sqlStmt2);
            pStmt2.setInt(1,loggedInUser.getUserProfile().getId());
            pStmt2.setString(2,searchKey);
            ResultSet rs = pStmt2.executeQuery();
            while(rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e){
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
        String sqlStmt3 = "update search_history set searched_date = ? where id =?";
        PreparedStatement pStmt3 = null;
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();
        int day = LocalDate.now().getDayOfMonth();
        try{
            pStmt3 = conn.prepareStatement(sqlStmt3);
            pStmt3.setDate(1,Date.valueOf(LocalDate.of(year,month,day)));
            pStmt3.setInt(2,id);
            pStmt3.executeUpdate();
        } catch (SQLException e){
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
        String sqlStmt4 = "select * from content where title =?";
        PreparedStatement pStmt4 = null;
        try{
            pStmt4 = conn.prepareStatement(sqlStmt4);
            pStmt4.setString(1,searchKey);
            ResultSet rs = pStmt4.executeQuery();
            Content newContent = null;
            while(rs.next()){
                Integer contentId = rs.getInt(1);
                ContentType type = ContentDao.getContentType(rs.getString(2));
                ContentGenre genre = ContentDao.getContentGenre(rs.getString(3));
                String title = rs.getString(4);
                Integer contentYear = rs.getInt(5);
                String description = rs.getString(6);
                String poster = rs.getString(7);
                String video = rs.getString(8);
                double rateScore = rs.getDouble(9);
                Integer ageLimit = rs.getInt(10);
                newContent = new Content(contentId,type,genre,title,contentYear,description,poster,video,rateScore,ageLimit);
            }
            String sqlStmt5 = "select rating_status from my_content where content_id =? and user_profile_id = ?";
            PreparedStatement pStmt5 = null;
            String rateStatus = null;
            try{
                pStmt5 = conn.prepareStatement(sqlStmt5);
                pStmt5.setInt(1,id);
                pStmt5.setInt(2,loggedInUser.getUserProfile().getId());
                ResultSet rs5 = pStmt5.executeQuery();
                while(rs5.next()) {
                    rateStatus = rs5.getString(1);
                }
            } catch (SQLException e){
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
            if(newContent!=null){
                System.out.println("Title: "+newContent.getTitle());
                System.out.println("Year: "+newContent.getYear());
                System.out.println("Type: "+newContent.getContentType());
                System.out.println("Genre: "+newContent.getContentGenre());
                System.out.println("Description: "+newContent.getDescription()+"");
                System.out.println("Total Rating Score : "+newContent.getTotalRateScore()+"");
                System.out.println("Age Limit : "+newContent.getAgeLimit()+"");
                if(rateStatus.equals("DONE")){
                    System.out.println("Content is in your list & is RATED");

                }else if(rateStatus.equals("NOT_DONE")){
                    System.out.println("Content is in your list & is NOT RATED");

                }else{
                    System.out.println("Content is not in your list!");
                }
            }else{
                System.out.println("THERE IS NO CONTENT WITH TITLE : "+searchKey);
            }
        } catch (SQLException e){
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
        finally {
            pStmt.close();
            conn.commit();
            conn.setAutoCommit(true);
        }
    }
}
