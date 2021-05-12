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
}
