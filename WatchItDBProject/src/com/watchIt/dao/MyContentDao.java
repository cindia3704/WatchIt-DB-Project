package com.watchIt.dao;

import com.watchIt.Entity.MyContent;
import com.watchIt.Entity.SearchHistory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}
