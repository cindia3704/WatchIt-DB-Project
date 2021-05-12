package com.watchIt.dao;

import com.watchIt.Entity.Orders;
import com.watchIt.Entity.SearchHistory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:51 오전
*/
public class OrdersDao {

    public static void insertOrders(Orders orders, Connection conn) throws SQLException {
        String sqlStmt = "insert into orders values(?,?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,orders.getId());
            pStmt.setDate(2,orders.getStartDate());
            pStmt.setDate(3,orders.getEndDate());
            pStmt.setInt(4,orders.getTicketId());
            pStmt.setInt(5,orders.getUserId());

            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
