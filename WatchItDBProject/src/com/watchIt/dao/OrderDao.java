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
    public static void insertOrder(Order order,Connection conn) throws SQLException {
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
