package com.watchIt.dao;

import com.watchIt.Entity.Ticket;
import com.watchIt.Entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:41 오전
*/
public class TicketDao {
    public static void insertTicket(Ticket ticket, Connection conn) throws SQLException {
        String sqlStmt = "insert into ticket values(?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,ticket.getId());
            pStmt.setString(2,ticket.getTicketType().toString());
            pStmt.setInt(3,ticket.getPrice());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
