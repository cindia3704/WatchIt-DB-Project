package com.watchIt.dao;

import com.watchIt.Entity.User;
import com.watchIt.enums.UserStatus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    public static String findUser(String username, String password,Connection conn) throws SQLException{
        String sqlStmt = "select * from user where username = ? and password = ?;";
        PreparedStatement pStmt = null;
        conn.setAutoCommit(false); // default true
        String next = "ERR";
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setString(1,username);
            pStmt.setString(2,password);
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()){
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String pw = rs.getString(3);
                Integer age = rs.getInt(4);
                String status = rs.getString(5);
                UserStatus userStatus = null;
                if(status.equals("ACTIVE")){
                    userStatus = UserStatus.ACTIVE;
                } else {
                    userStatus = UserStatus.INACTIVE;
                }
                try {
                    User found = new User(id, name, pw, age, userStatus);
                    System.out.println("Logged in as "+found.getUsername());
                    if(userStatus.equals(UserStatus.ACTIVE)){
                        next = "CHOOSEPROFILE";
                    }else{
                        next = "PAY";
                        System.out.println("Logged in as "+found.getUsername()+"\nYou are INACTIVE! To turn active you must buy a ticket...");
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
            conn.commit();
            conn.setAutoCommit(true);
        }
        return next;
    }



}
