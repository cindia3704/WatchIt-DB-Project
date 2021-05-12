package com.watchIt.dao;

import com.watchIt.Entity.ContentComment;
import com.watchIt.Entity.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 9:16 오전
*/
public class RatingDao {
    public static void insertRating(Rating rating, Connection conn) throws SQLException {
        String sqlStmt = "insert into rating values(?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,rating.getId());
            pStmt.setDouble(2,rating.getScore());
            pStmt.setInt(3,rating.getUserProfileId());
            pStmt.setInt(4,rating.getContentId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }
}
