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
import java.sql.SQLException;
import java.time.LocalDate;
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

//        createTickets(conn);
//        makeRandomContents(conn);
//        createUsers(conn);
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
//                        System.out.println("Logged in user : "+ ((LoggedInUser) object).getUser().getUsername());
                    }
                }else{
                    welcomMsg(sc,db);
                }

                break;
            case "2":
                System.out.println("Register is not implemented yet...");
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
        System.out.println("|5) Delete this user_profiel                             |");
        System.out.println("|6) Exit                                                 |");
        System.out.println("|--------------------------------------------------------|");
        System.out.print("option number: ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                break;
            case "2":
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
                mainPage(sc,db,loggedInUser);
                break;
            case "6":
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


        //create 3 tickets
//    private static void createTickets(Connection db)throws SQLException{
//        Ticket ticket1 = new Ticket();
//        ticket1.setId(1);
//        ticket1.setTicketType(TicketType.BASIC);
//        ticket1.setPrice(9500);
//        TicketDao.insertTicket(ticket1,db);
//
//        Ticket ticket2 = new Ticket();
//        ticket2.setId(2);
//        ticket2.setTicketType(TicketType.STANDARD);
//        ticket2.setPrice(15000);
//        TicketDao.insertTicket(ticket2,db);
//
//        Ticket ticket3 = new Ticket();
//        ticket3.setId(3);
//        ticket3.setTicketType(TicketType.PREMIUM);
//        ticket3.setPrice(20000);
//        TicketDao.insertTicket(ticket3,db);
//    }
//
//    // create random users
//    private static void createUsers(Connection conn) throws SQLException {
//        int count = 1;
//        int countSearchHistory = 1;
//        int countMyContents =1;
//        int countComments = 1;
//
//        for(int i =1;i<=10000;i++){
//            User user = new User();
//            user.setId(i);
//            user.setUserAge(getRandomIndex(10,60));
//            user.setPassword(getRandomString(3,10));
//            user.setUsername(getRandomString(3,10));
//            user.setUserStatus(getUserStatus(getRandomIndex(0,2)));
//            UserDao.insertUser(user,conn);
//            makeRandomOrder(i,conn);
//
//
//            int numOfProfiles = getRandomIndex(1,4);
//            System.out.println("num profile: "+numOfProfiles);
//
//            for(int j=count;j<numOfProfiles+count;j++){
//                int numOfSearchHistory = getRandomIndex(0,30);
//                int numOfMyContents = getRandomIndex(0,20);
//                int numOfComments = getRandomIndex(0,10);
//
//                UserProfile userProfile = new UserProfile();
//                userProfile.setUserId(i);
//                userProfile.setId(j);
//                userProfile.setNickname(getRandomString(3,10));
//                UserProfileDao.insertUserProfile(userProfile,conn);
//                System.out.println("DONE: "+i);
//
//                makeSearchHistory(countSearchHistory,numOfSearchHistory,j,conn);
//                makeRandomMyContent(countMyContents,numOfMyContents,j,conn);
//                makeRandomComments(countComments,numOfComments,j,conn);
//
//                countSearchHistory = countSearchHistory+numOfSearchHistory;
//                countMyContents = countMyContents+numOfMyContents;
//                countComments = countComments + numOfComments;
//            }
//            count = count+numOfProfiles;
//
//        }
//    }
//    private static void makeRandomOrder(int userId,Connection conn) throws SQLException {
//        Orders order = new Orders();
//        order.setId(userId);
//        Date selected = getRandomDate();
//        order.setStartDate(selected);
//        order.setEndDate(getEndDateForSelectedDate(selected));
//        order.setUserId(userId);
//        order.setTicketId(getRandomIndex(1,3));
//        OrdersDao.insertOrders(order,conn);
//    }
//
//    private static void makeRandomComments(int start,int count, int userId, Connection conn) throws SQLException{
//        for(int i = start;i<start+count;i++){
//            System.out.println(" COMMENT: "+i);
//            int contentID = getRandomIndex(1,100000);
//            ContentComment contentComment = new ContentComment();
//            contentComment.setId(i);
//            contentComment.setComment(getRandomString(10,50));
//            contentComment.setUserProfileId(userId);
//            contentComment.setContentId(contentID);
//            ContentCommentDao.insertComments(contentComment,conn);
//
//            Rating rating = new Rating();
//            rating.setId(i);
//            rating.setScore(getRandomDouble(0.0,5.0));
//            rating.setContentId(contentID);
//            rating.setUserProfileId(userId);
//            RatingDao.insertRating(rating,conn);
//
//        }
//    }
//
//    private static void makeRandomMyContent(int start,int count,int userId,Connection conn)throws SQLException{
//        for(int i= start;i<start+count;i++){
//            MyContent  myContent = new MyContent();
//
//            myContent.setId(i);
//            myContent.setContentId(getRandomIndex(1,100000));
//            myContent.setUserProfileId(userId);
//            myContent.setRateStatus(getRateStatus(getRandomIndex(0,2)));
//            MyContentDao.insertMyContent(myContent,conn);
//        }
//    }
//
//    private static void makeSearchHistory(int start,int count,int userId,Connection conn) throws SQLException{
//        System.out.println("SEARCH HISTORY From : "+start+"  to "+count);
//        for(int i =start;i<start+count;i++){
//            System.out.println("SEARCH HISTORY START: "+i);
//            SearchHistory searchHistory = new SearchHistory();
//
//            searchHistory.setId(i);
//            searchHistory.setSearchKey(getRandomString(3,20));
//            searchHistory.setSearchedDate(getRandomDate());
//            searchHistory.setUserProfileId(userId);
//            SearchHistoryDao.insertSearchHistory(searchHistory,conn);
//        }
//    }
//
//    private static void makeRandomContents(Connection conn) throws SQLException {
//        for(int i =1;i<=100000;i++){
//            Content content = new Content();
//            content.setId(i);
//            content.setContentType(getContentType(getRandomIndex(0,4)));
//            content.setContentGenre(getContentGenre(getRandomIndex(0,12)));
//            content.setTitle(getRandomString(5,10));
//            content.setYear(getRandomIndex(1980,2022));
//            content.setDescription(getRandomString(30,300));
//            content.setPoster("https://watchIt.com/"+getRandomString(5,20)+".jpg");
//            content.setVideo("https://watchIt.com/"+getRandomString(5,20)+".mp4");
//            content.setTotalRateScore(getRandomDouble(0.0,5.0));
//            content.setAgeLimit(getRandomIndex(0,20));
//            ContentDao.insertContent(content,conn);
//        }
//    }
//
//    // get random number
//    private static int getRandomIndex(Integer minNum,Integer count){
//        int min = minNum;
//        int max = count;
//        int random = (int) ((Math.random() * (max - min)) + min);
////        System.out.println(random);
//        return random;
//    }
//
//    private static double getRandomDouble(double minNum, double maxNum){
//        Random r = new Random();
//        double randomValue = minNum + (maxNum - minNum) * r.nextDouble();
//        return randomValue;
//    }
//
//    // get random string made with alphabets  & length range from 3~10
//    private static String getRandomString(int minNum,int maxNum){
//        String alphabet = "abcdefghijklmnopqrstuvwxyz";
//        StringBuilder sb = new StringBuilder();
//        Random random = new Random();
//        int length = getRandomIndex(minNum,maxNum);
//        for(int i =0;i<length;i++){
//            int index = random.nextInt(alphabet.length());
//            char randomChar = alphabet.charAt(index);
//            sb.append(randomChar);
//        }
//        return sb.toString();
//    }
//
//    private static Date getRandomDate(){
//        GregorianCalendar gc = new GregorianCalendar();
//
//        int year = getRandomIndex(2019, 2021);
//
//        int month = getRandomIndex(1,12);
//        int day=30;
//        switch (month){
//            case 1:
//            case 3:
//            case 5:
//            case 7:
//            case 8:
//            case 10:
//            case 12:
//                day =31;
//                break;
//            case 2:
//                day = 28;
//                break;
//            case 4:
//            case 6:
//            case 9:
//            case 11:
//                day = 30;
//                break;
//        }
//
////        System.out.println(year + "-" + month + "-" + day);
//        return Date.valueOf(LocalDate.of(year,month,day));
//    }
//
//    private static Date getEndDateForSelectedDate(Date start){
//        LocalDate datee = start.toLocalDate();
//        datee = datee.plusMonths(1);
//        System.out.println("END: "+datee.toString());
//        return Date.valueOf(datee);
//    }
//
//    // get random userStatus
//    public static UserStatus getUserStatus(int index){
//        if(index ==1) {
//            return UserStatus.ACTIVE;
//        }
//        else{
//            return UserStatus.INACTIVE;
//        }
//    }
//
//    public static ContentType getContentType(int index){
//        if(index ==1) {
//            return ContentType.DRAMA;
//        }
//        if(index ==2){
//            return ContentType.MOVIE;
//        }
//        if(index ==3){
//            return ContentType.ENTERTAINMENT;
//        }
//        else{
//            return ContentType.ORIGINAL;
//        }
//    }
//
//    public static ContentGenre getContentGenre(int index){
//        if(index ==1) {
//            return ContentGenre.DRAMA;
//        }
//        if(index ==2){
//            return ContentGenre.ACTION;
//        }
//        if(index ==3){
//            return ContentGenre.COMEDY;
//        }
//        if(index==5){
//            return ContentGenre.ADVENTURE;
//        }
//        if(index==6){
//            return ContentGenre.FANTASY;
//        }
//        if(index==7){
//            return ContentGenre.HORROR;
//        }
//        if(index==8){
//            return ContentGenre.MYSTERY;
//        }
//        if(index==9){
//            return ContentGenre.ROMANCE;
//        }
//        if(index==10){
//            return ContentGenre.ROMENTIC_COMEDY;
//        }
//        if(index==11){
//            return ContentGenre.SF;
//        }
//        else{
//            return ContentGenre.ETC;
//        }
//    }
//
//    public static RateStatus getRateStatus(int index) {
//        if(index==1){
//            return RateStatus.DONE;
//        }else {
//            return RateStatus.NOT_DONE;
//        }
//    }

}

