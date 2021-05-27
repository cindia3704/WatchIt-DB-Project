package com.watchIt.dao;

import com.watchIt.Entity.Content;
import com.watchIt.Entity.ContentComment;
import com.watchIt.Entity.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<ContentComment> getCommentForCertainContent(Connection conn, Integer contentId) throws SQLException {
//        conn.setAutoCommit(false);
        String sqlStmt = "select * from content_comment where content_id ="+contentId+";";
        PreparedStatement pStmt = null;
        List<ContentComment> contentCommentList = new ArrayList<ContentComment>();
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()){
                Integer id = rs.getInt(1);
                String comment = rs.getString(2);
                Integer userProfileID = rs.getInt(3);
                Integer contentID = rs.getInt(4);
                ContentComment contentComment = new ContentComment(id,comment,userProfileID,contentID);
                contentCommentList.add(contentComment);
            }
        }catch (SQLException e){
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        }

        finally {
            pStmt.close();
//            conn.commit();
//            conn.setAutoCommit(true);
        }
        return contentCommentList;
    }

    public static String getNickNameOfComments(Connection conn, ContentComment contentComment) throws SQLException {
//        conn.setAutoCommit(false);
        String sqlStmt2 = "select nickname from user_profile Where id =?;";
        PreparedStatement pStmt2 = null;
        String userProfiles = null;
        try {
            pStmt2 = conn.prepareStatement(sqlStmt2);
            Integer id = contentComment.getUserProfileId();
            pStmt2.setInt(1,id);
            ResultSet rs = pStmt2.executeQuery();
            while (rs.next()){
                userProfiles = rs.getString(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
            try {
                conn.rollback();
                System.err.println(e.getMessage());
                System.err.println("Transaction rollback");
            } catch (SQLException e1) {
                System.err.println(e1.getMessage());
                System.err.println("There was an error making a rollback");
            }
        } finally {
            pStmt2.close();
//            conn.commit();
//            conn.setAutoCommit(true);
        }
        return userProfiles;
    }
}
