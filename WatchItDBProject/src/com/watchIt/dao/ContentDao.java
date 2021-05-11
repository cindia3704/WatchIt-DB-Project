package com.watchIt.dao;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:15 오전
*/

import com.watchIt.Entity.Content;
import com.watchIt.Entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContentDao {
    private Connection conn;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "cindia3704";
    private static final String URL = "jdbc:mysql://localhost:3306/WatchIt?characterEncoding=latin1&useConfigs=maxPerformance";

    public ContentDao(){
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

    public void insertContent(Content content) throws SQLException {
        String sqlStmt = "insert into Content values(?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,content.getId());
            pStmt.setString(2,content.getContentType().toString());
            pStmt.setString(3,content.getContentGenre().toString());
            pStmt.setString(4,content.getTitle());
            pStmt.setInt(5,content.getYear());
            pStmt.setString(6,content.getDescription());
            pStmt.setString(7,content.getPoster());
            pStmt.setString(8,content.getVideo());
            pStmt.setDouble(9,content.getTotalRateScore());
            pStmt.setInt(10,content.getAgeLimit());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
