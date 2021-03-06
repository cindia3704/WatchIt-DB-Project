package com.watchIt.dao;
/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:15 오전
*/

import com.watchIt.Entity.Content;
import com.watchIt.Entity.MyContent;
import com.watchIt.Entity.User;
import com.watchIt.enums.ContentGenre;
import com.watchIt.enums.ContentType;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ContentDao {
    public static void insertContent(Content content,Connection conn) throws SQLException {
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



    public static Content getOneContent(Connection conn, Integer contentId)throws SQLException {
        String sqlStmt = "select * from Content where id =?";
        PreparedStatement pStmt = null;
        Content newContent = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,contentId);
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()){
                Integer id = rs.getInt(1);
                ContentType type = getContentType(rs.getString(2));
                ContentGenre genre = getContentGenre(rs.getString(3));
                String title = rs.getString(4);
                Integer year = rs.getInt(5);
                String description = rs.getString(6);
                String poster = rs.getString(7);
                String video = rs.getString(8);
                double rateScore = rs.getDouble(9);
                Integer ageLimit = rs.getInt(10);
                newContent = new Content(id,type,genre,title,year,description,poster,video,rateScore,ageLimit);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
        return newContent;
    }

    public static List<Content> getMyContentList(Scanner sc, Connection conn, List<MyContent> myContentList) throws SQLException{
        String sqlStmt = "select * from Content where id =?;";
        PreparedStatement pStmt = null;
        List<Content> contentList = new ArrayList<Content>();
        for(int i=0;i<myContentList.size();i++){
            try{
                pStmt = conn.prepareStatement(sqlStmt);
                pStmt.setInt(1,myContentList.get(i).getContentId());
                ResultSet rs =pStmt.executeQuery();

                while (rs.next()){
                    Integer id = rs.getInt(1);
                    ContentType type = getContentType(rs.getString(2));
                    ContentGenre genre = getContentGenre(rs.getString(3));
                    String title = rs.getString(4);
                    Integer year = rs.getInt(5);
                    String description = rs.getString(6);
                    String poster = rs.getString(7);
                    String video = rs.getString(8);
                    double rateScore = rs.getDouble(9);
                    Integer ageLimit = rs.getInt(10);
                    Content newContent = new Content(id,type,genre,title,year,description,poster,video,rateScore,ageLimit);
                    contentList.add(newContent);
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
            }finally {
                pStmt.close();
            }
        }
        return contentList;
    }

    public static void getContentTypeList(Scanner sc, Connection conn) throws SQLException{
        String sqlStmt = "select distinct type from Content ";
        PreparedStatement pStmt = null;
        List<String> typeList = new ArrayList<String>();
        List<Content> contentList = new ArrayList<Content>();
        try{
                pStmt = conn.prepareStatement(sqlStmt);
                ResultSet rs =pStmt.executeQuery();

                while (rs.next()){
                    String type = rs.getString(1);
                    typeList.add(type);
                }
                for(int i=0;i<typeList.size();i++){
                    System.out.println("[ "+(i+1)+" ]: "+typeList.get(i));
                }
                System.out.print("Select Type: ");
                String op = sc.nextLine();
                Integer index = null;
                try{
                    index =Integer.parseInt(op);
                }
                catch(NumberFormatException ex){
                    System.out.println("Its not a valid Integer");
                }
                if(index<0 || index >= typeList.size()){
                    System.out.println("Invalid number.. Try again ");
                    System.out.print("Select Type: ");
                    op = sc.nextLine();
                    try{
                        index =Integer.parseInt(op);
                    }
                    catch(NumberFormatException ex){
                        System.out.println("Its not a valid Integer");
                    }
                }
                String sqlStmt2 = "select * from content where type=? ";
                PreparedStatement pStmt2 = null;
                try {
                    pStmt2 = conn.prepareStatement(sqlStmt2);
                    pStmt2.setString(1,typeList.get(index-1));
                    ResultSet rs2 =pStmt2.executeQuery();
                    while(rs2.next()){
                        Integer id = rs2.getInt(1);
                        ContentType type = getContentType(rs2.getString(2));
                        ContentGenre genre = getContentGenre(rs2.getString(3));
                        String title = rs2.getString(4);
                        Integer year = rs2.getInt(5);
                        String description = rs2.getString(6);
                        String poster = rs2.getString(7);
                        String video = rs2.getString(8);
                        double rateScore = rs2.getDouble(9);
                        Integer ageLimit = rs2.getInt(10);
                        Content newContent = new Content(id,type,genre,title,year,description,poster,video,rateScore,ageLimit);
                        contentList.add(newContent);
                    }
                    for(int i = 0; i<contentList.size();i++){
                        System.out.println("[ "+(i+1)+" ]   Title: "+contentList.get(i).getTitle());
                        System.out.println("Type : "+contentList.get(i).getContentType());
                        System.out.println("Genre : "+contentList.get(i).getContentGenre());
                        System.out.println("Poster : "+contentList.get(i).getPoster());
                        System.out.println("Year : "+contentList.get(i).getYear());
                        System.out.println("Rate Score : "+contentList.get(i).getTotalRateScore()+"\n");
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
            }finally {
                pStmt2.close();
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
            }finally {
                pStmt.close();
            }

    }

    public static void getContentGenreList(Scanner sc, Connection conn) throws SQLException{
        String sqlStmt = "select distinct genre from Content ";
        PreparedStatement pStmt = null;
        List<String> genreList = new ArrayList<String>();
        List<Content> contentList = new ArrayList<Content>();
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            ResultSet rs =pStmt.executeQuery();

            while (rs.next()){
                String genre = rs.getString(1);
                genreList.add(genre);
            }
            for(int i=0;i<genreList.size();i++){
                System.out.println("[ "+(i+1)+" ]: "+genreList.get(i));
            }
            System.out.print("Select Type: ");
            String op = sc.nextLine();
            Integer index = null;
            try{
                index =Integer.parseInt(op);
            }
            catch(NumberFormatException ex){
                System.out.println("Its not a valid Integer");
            }
            if(index<0 || index >= genreList.size()){
                System.out.println("Invalid number.. Try again ");
                System.out.print("Select Type: ");
                op = sc.nextLine();
                try{
                    index =Integer.parseInt(op);
                }
                catch(NumberFormatException ex){
                    System.out.println("Its not a valid Integer");
                }
            }
            String sqlStmt2 = "select * from content where genre=? ";
            PreparedStatement pStmt2 = null;
            try {
                pStmt2 = conn.prepareStatement(sqlStmt2);
                pStmt2.setString(1,genreList.get(index-1));
                ResultSet rs2 =pStmt2.executeQuery();
                while(rs2.next()){
                    Integer id = rs2.getInt(1);
                    ContentType type = getContentType(rs2.getString(2));
                    ContentGenre genre = getContentGenre(rs2.getString(3));
                    String title = rs2.getString(4);
                    Integer year = rs2.getInt(5);
                    String description = rs2.getString(6);
                    String poster = rs2.getString(7);
                    String video = rs2.getString(8);
                    double rateScore = rs2.getDouble(9);
                    Integer ageLimit = rs2.getInt(10);
                    Content newContent = new Content(id,type,genre,title,year,description,poster,video,rateScore,ageLimit);
                    contentList.add(newContent);
                }
                for(int i = 0; i<contentList.size();i++){
                    System.out.println("[ "+(i+1)+" ]   Title: "+contentList.get(i).getTitle());
                    System.out.println("Type : "+contentList.get(i).getContentType());
                    System.out.println("Genre : "+contentList.get(i).getContentGenre());
                    System.out.println("Poster : "+contentList.get(i).getPoster());
                    System.out.println("Year : "+contentList.get(i).getYear());
                    System.out.println("Rate Score : "+contentList.get(i).getTotalRateScore()+"\n");
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
            }finally {
                pStmt2.close();
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
        }finally {
            pStmt.close();
        }

    }

    public static ContentGenre getContentGenre(String genre){
        if(genre.equals("DRAMA")) {
            return ContentGenre.DRAMA;
        }
        if(genre.equals("ACTION")){
            return ContentGenre.ACTION;
        }
        if(genre.equals("COMEDY")){
            return ContentGenre.COMEDY;
        }
        if(genre.equals("ADVENTURE")){
            return ContentGenre.ADVENTURE;
        }
        if(genre.equals("FANTASY")){
            return ContentGenre.FANTASY;
        }
        if(genre.equals("HORROR")){
            return ContentGenre.HORROR;
        }
        if(genre.equals("MYSTERY")){
            return ContentGenre.MYSTERY;
        }
        if(genre.equals("ROMANCE")){
            return ContentGenre.ROMANCE;
        }
        if(genre.equals("ROMENTIC_COMEDY")){
            return ContentGenre.ROMENTIC_COMEDY;
        }
        if(genre.equals("SF")){
            return ContentGenre.SF;
        }
        else{
            return ContentGenre.ETC;
        }
    }

    public static ContentType getContentType(String type){
        if(type.equals("DRAMA")) {
            return ContentType.DRAMA;
        }
        if(type.equals("MOVIE")){
            return ContentType.MOVIE;
        }
        if(type.equals("ENTERTAINMENT")){
            return ContentType.ENTERTAINMENT;
        }
        else{
            return ContentType.ORIGINAL;
        }
    }
}
