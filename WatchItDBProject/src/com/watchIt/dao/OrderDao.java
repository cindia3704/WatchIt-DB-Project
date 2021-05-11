package com.watchIt.dao;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 8:25 오전
*/

import com.watchIt.Entity.Order;
import com.watchIt.Entity.SearchHistory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderDao {
    private Connection conn;

    private static final String USERNAME = "root";
    private static final String PASSWORD = "cindia3704";
    private static final String URL = "jdbc:mysql://localhost:3306/WatchIt?characterEncoding=latin1&useConfigs=maxPerformance";

    public OrderDao(){
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

    public void insertOrder(Order order) throws SQLException {
        String sqlStmt = "insert into order values(?,?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,order.getId());
            pStmt.setDate(2, (Date) order.getStartDate());
            pStmt.setDate(3, (Date) order.getEndDate());
            pStmt.setInt(4,order.getTicketId());
            pStmt.setInt(5,order.getUserId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
