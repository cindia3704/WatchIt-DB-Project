package com.watchIt;

import com.watchIt.Entity.Content;
import com.watchIt.Entity.ContentComment;
import com.watchIt.Entity.LoggedInUser;
import com.watchIt.Entity.MyContent;
import com.watchIt.Entity.Orders;
import com.watchIt.Entity.Rating;
import com.watchIt.Entity.SearchHistory;
import com.watchIt.Entity.Ticket;
import com.watchIt.Entity.User;
import com.watchIt.Entity.UserProfile;
import com.watchIt.dao.ContentCommentDao;
import com.watchIt.dao.ContentDao;
import com.watchIt.dao.MyContentDao;
import com.watchIt.dao.OrdersDao;
import com.watchIt.dao.RatingDao;
import com.watchIt.dao.SearchHistoryDao;
import com.watchIt.dao.TicketDao;
import com.watchIt.dao.UserDao;
import com.watchIt.dao.UserProfileDao;
import com.watchIt.enums.ContentGenre;
import com.watchIt.enums.ContentType;
import com.watchIt.enums.RateStatus;
import com.watchIt.enums.TicketType;
import com.watchIt.enums.UserStatus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
        Connection conn = null;

        final String USERNAME = "root";
        final String PASSWORD = "cindia3704";
        final String URL = "jdbc:mysql://localhost:3306/WatchIt?characterEncoding=latin1&useConfigs=maxPerformance";
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

        Scanner sc= new Scanner(System.in);
        welcomMsg(sc,conn);
        sc.close();

    }

    private static void welcomMsg(Scanner sc,Connection db) throws SQLException {
        System.out.println("\nWELCOME TO WatchIt\nPlease select one of the options below");
        System.out.println("1) Login");
        System.out.println("2) Register");
        System.out.println("3) Exit");

        System.out.print("option number: ");
        String option = sc.nextLine();
        switch (option){
            case "1":
                System.out.print("Username: ");
                String username = sc.nextLine();
                System.out.print("Password: ");
                String password = sc.nextLine();
                Object object = UserDao.findUser(sc,username,password,db);
                if(object instanceof LoggedInUser){
                    if(object.equals(null)){
                        System.out.println("Wrong username / password!");
                        welcomMsg(sc,db);
                    }
                    else{
                        mainPage(sc,db,((LoggedInUser) object));
                        break;
                    }
                }else{
                    welcomMsg(sc,db);
                }

                break;
            case "2":
                db.setAutoCommit(false);
                System.out.println("-----------------REGISTER-----------------");
                System.out.print("Username: ");
                String userName = sc.nextLine();
                System.out.print("Password: ");
                String pwd = sc.nextLine();
                System.out.print("Age: ");
                String age = sc.nextLine();
                Integer yearsOld =null;
                try{
                    yearsOld =Integer.parseInt(age);
                }
                catch(NumberFormatException ex){
                    System.out.println("Its not a valid Integer");
                }
                String sqlStmt = "select max(id) as total from user;";
                PreparedStatement pStmt = null;
                Integer id = null;
                try {
                    pStmt = db.prepareStatement(sqlStmt);
                    ResultSet rs = pStmt.executeQuery();
                    while (rs.next()) {
                        id = rs.getInt(1);
                    }
                   User user = new User(id+1,userName,pwd,yearsOld,UserStatus.INACTIVE);
                    UserDao.insertUser(user,db);
                }catch (SQLException e){
                    e.printStackTrace();
                    try {
                        db.rollback();
                        System.err.println(e.getMessage());
                        System.err.println("Transaction rollback");
                    } catch (SQLException e1) {
                        System.err.println(e1.getMessage());
                        System.err.println("There was an error making a rollback");
                    }
                }
                String sqlStmt2 = "select max(id) as count from user_profile;";
                PreparedStatement pStmt2 = null;
                Integer idForProfile = null;
                try {
                    pStmt2 = db.prepareStatement(sqlStmt2);
                    ResultSet rs2 = pStmt2.executeQuery();
                    while (rs2.next()) {
                        idForProfile = rs2.getInt(1);
                    }
                    UserProfile userProfile = new UserProfile(idForProfile+1,userName,id);
                    UserProfileDao.insertUserProfile(userProfile,db);
                }catch (SQLException e){
                    e.printStackTrace();
                    try {
                        db.rollback();
                        System.err.println(e.getMessage());
                        System.err.println("Transaction rollback");
                    } catch (SQLException e1) {
                        System.err.println(e1.getMessage());
                        System.err.println("There was an error making a rollback");
                    }
                }
                finally {
                    pStmt.close();
                    pStmt2.close();
                    db.commit();
                    db.setAutoCommit(true);
                }
                System.out.println("Registered Successfully!");
                welcomMsg(sc,db);
                break;
            case "3":
                System.out.println("See you again~ Bye!");
                break;
            default:
                System.out.println("Wrong Command. Please Select again...");
                welcomMsg(sc,db);
        }

    }

    private static void mainPage(Scanner sc,Connection db,LoggedInUser loggedInUser) throws SQLException {
        System.out.println("|--------------------------------------------------------|");
        System.out.println("|                         MAIN PAGE                      |");
        System.out.println("|           Please Choose one of the options below:      |");
        System.out.println("|1) See list of contents by TYPE                         |");
        System.out.println("|2) See list of contents by GENRE                        |");
        System.out.println("|3) See list of my contents                              |");
        System.out.println("|4) Search contents by title                             |");
        System.out.println("|5) Delete this user_profile & Exit                      |");
        System.out.println("|6) See list of my comments & modify comment             |");
        System.out.println("|7) Exit                                                 |");
        System.out.println("|--------------------------------------------------------|");
        System.out.print("option number: ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                ContentDao.getContentTypeList(sc,db);
                mainPage(sc,db,loggedInUser);
                break;
            case "2":
                ContentDao.getContentGenreList(sc,db);
                mainPage(sc,db,loggedInUser);
                break;
            case "3":
                myContentLists(sc,db,loggedInUser);
                mainPage(sc,db,loggedInUser);
                break;
            case "4":
                System.out.println("SEARCH KEY: ");
                String key = sc.nextLine();
                SearchHistoryDao.updateSearchHistory(db,loggedInUser,key);
                mainPage(sc,db,loggedInUser);
                break;
            case "5":
                UserProfileDao.deleteUserProfile(loggedInUser.getUserProfile(),db);
                break;
            case "6":
                ContentCommentDao.getMyCommentListAndModify(db,loggedInUser,sc);
                mainPage(sc,db,loggedInUser);
                break;
            case "7":
                System.out.println("See you again~ Bye!");
                break;
            default:
                System.out.println("Wrong input.. Try again");
                mainPage(sc, db, loggedInUser);
                break;
        }
    }

    private static void myContentLists(Scanner sc,Connection db,LoggedInUser loggedInUser) throws SQLException {
        db.setAutoCommit(false);
        List<MyContent> myContentList =MyContentDao.getMyContentList(sc,db,loggedInUser);
        List<Content> contentList = ContentDao.getMyContentList(sc,db,myContentList);
        for(int i=0;i<contentList.size();i++){
            Content selected = contentList.get(i);
            System.out.println("[ "+(i+1)+"] Title: "+selected.getTitle());
            System.out.println("   Year: "+selected.getYear());
            System.out.println("   Type: "+selected.getContentType());
            System.out.println("   Genre: "+selected.getContentGenre());
            System.out.println("   Description: "+selected.getDescription()+"\n");
        }
        System.out.println("|Insert content number for more info /\"menu\" to go back|");
        System.out.print("Input: ");
        String nextOp = sc.nextLine();
        if(nextOp.equals("menu")){
            return;
        }else{
            Integer selected = null;
            try{
                selected =Integer.parseInt(nextOp) -1;
            }
            catch(NumberFormatException ex){
                System.out.println("Its not a valid Integer");
            }
            Content detail =contentList.get(selected);
            String myRatingStatus = myContentList.get(selected).getRateStatus().toString();
            System.out.println("|--------------------------------------------------------|");
            System.out.println("|                    CONTENT DETAIL PAGE                 |");
            System.out.println("|--------------------------------------------------------|");
            System.out.println("Title: "+detail.getTitle());
            System.out.println("Year: "+detail.getYear());
            System.out.println("Type: "+detail.getContentType());
            System.out.println("Genre: "+detail.getContentGenre());
            System.out.println("Description: "+detail.getDescription()+"");
            System.out.println("Total Rating Score : "+detail.getTotalRateScore()+"");
            System.out.println("Age Limit : "+detail.getAgeLimit()+"");
            System.out.println("My rating status : "+myRatingStatus+"\n");
            System.out.println("-------------------------COMMENTS------------------------");
            List<ContentComment> contentComments = ContentCommentDao.getCommentForCertainContent(db,detail.getId());
            for(int i=0;i<contentComments.size();i++){
                String nickName =ContentCommentDao.getNickNameOfComments(db,contentComments.get(i));
                System.out.println("[ "+nickName+" ] : "+contentComments.get(i).getComment()+"\n");
            }

            System.out.println("Please choose one of the options below: ");
            System.out.println("1 ) Go back to list of my contents ");
            System.out.println("2 ) Go back to menu ");
            System.out.println("3 ) Delete this content from MyContent");
//            if(myRatingStatus.equals("DONE")){
//                System.out.println("4) Redo rating!");
//            }else{
            if(myRatingStatus.equals("NOT_DONE")){
                System.out.println("4) Rate this content");
            }
            System.out.print("Input: ");
            String nextOpInDetail = sc.nextLine();
            switch (nextOpInDetail){
                case "1":
                    db.commit();
                    myContentLists(sc,db,loggedInUser);
                    break;
                case "2":
                    db.commit();
                    mainPage(sc,db,loggedInUser);
                    break;
                case "3":
                    MyContentDao.deleteContentFromMyList(db,detail.getId(),loggedInUser);
                    db.commit();
                    break;
                case "4":
                    System.out.print("Score: ");
                    String score = sc.nextLine();
                    Double dscore = null;
                    try{
                        dscore=Double.parseDouble(score);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    if(myRatingStatus.equals("NOT_DONE")) {
                        MyContentDao.rateMyContent(db, detail.getId(), dscore, loggedInUser, "NOT_DONE", "DONE");
                    }
                    db.commit();
                    db.setAutoCommit(true);
//                    }else {
//                        MyContentDao.rateMyContent(db, detail.getId(), dscore,loggedInUser,"DONE","NOT_DONE");
//                    }
                    break;
            }

        }
    }


}

