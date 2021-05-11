package com.watchIt.dao;

import com.watchIt.Entity.SearchHistory;
import com.watchIt.Entity.Ticket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:51 오전
*/
public class SearchHistoryDao {
    private Connection conn;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "cindia3704";
    private static final String URL = "jdbc:mysql://localhost:3306/WatchIt?characterEncoding=latin1&useConfigs=maxPerformance";

    public SearchHistoryDao(){
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

    public void insertSearchHistory(SearchHistory searchHistory) throws SQLException {
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
}
