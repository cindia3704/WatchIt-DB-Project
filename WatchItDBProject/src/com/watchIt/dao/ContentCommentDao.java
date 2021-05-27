package com.watchIt.dao;

import com.watchIt.Entity.Content;
import com.watchIt.Entity.ContentComment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 9:06 오전
*/
public class ContentCommentDao {
    public static void insertComments(ContentComment contentComment, Connection conn) throws SQLException {
        String sqlStmt = "insert into content_comment values(?,?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,contentComment.getId());
            pStmt.setString(2,contentComment.getComment());
            pStmt.setInt(3,contentComment.getUserProfileId());
            pStmt.setInt(4,contentComment.getContentId());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }

}
