package com.watchIt.dao;

import com.watchIt.Entity.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 5:09 오전
*/
public class UserDao {

    public static void insertUser(User user, Connection conn) throws SQLException {
        String sqlStmt = "insert into User values(?,?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,user.getId());
            pStmt.setString(2,user.getUsername());
            pStmt.setString(3,user.getPassword());
            pStmt.setInt(4,user.getUserAge());
            pStmt.setString(5,user.getUserStatus().toString());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
