package com.watchIt;

import com.watchIt.Entity.Content;
import com.watchIt.Entity.Ticket;
import com.watchIt.Entity.User;
import com.watchIt.Entity.UserProfile;
import com.watchIt.dao.ContentDao;
import com.watchIt.dao.TicketDao;
import com.watchIt.dao.UserDao;
import com.watchIt.dao.UserProfileDao;
import com.watchIt.enums.ContentGenre;
import com.watchIt.enums.ContentType;
import com.watchIt.enums.TicketType;
import com.watchIt.enums.UserStatus;

import java.sql.SQLException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
//        createTickets();

//        createUsers();
//        makeRandomContents();

    }

    //create 3 tickets
    private static void createTickets()throws SQLException{
        TicketDao ticketDao = new TicketDao();

        Ticket ticket1 = new Ticket();
        ticket1.setId(1);
        ticket1.setTicketType(TicketType.BASIC);
        ticket1.setPrice(9500);
        ticketDao.insertTicket(ticket1);

        Ticket ticket2 = new Ticket();
        ticket2.setId(2);
        ticket2.setTicketType(TicketType.STANDARD);
        ticket2.setPrice(15000);
        ticketDao.insertTicket(ticket2);

        Ticket ticket3 = new Ticket();
        ticket3.setId(3);
        ticket3.setTicketType(TicketType.PREMIUM);
        ticket3.setPrice(20000);
        ticketDao.insertTicket(ticket3);
    }

    // create random users
    private static void createUsers() throws SQLException {
        int count = 1;
        for(int i =1;i<=5;i++){
            UserDao userDao = new UserDao();
            User user = new User();
            user.setId(i);
            user.setUserAge(getRandomIndex(10,60));
            user.setPassword(getRandomString(3,10));
            user.setUsername(getRandomString(3,10));
            user.setUserStatus(getUserStatus(getRandomIndex(0,2)));
            userDao.insertUser(user);

            int numOfProfiles = getRandomIndex(1,4);
            System.out.println("num profile: "+numOfProfiles);
            for(int j=count;j<numOfProfiles+count;j++){
                UserProfileDao userProfileDao = new UserProfileDao();
                UserProfile userProfile = new UserProfile();
                userProfile.setUserId(i);
                userProfile.setId(j);
                userProfile.setNickname(getRandomString(3,10));
                userProfileDao.insertUserProfile(userProfile);
                System.out.println("DONE: "+i);
            }
            count = count+numOfProfiles;
        }
    }

    private static void makeRandomContents() throws SQLException {
        for(int i =1;i<=5;i++){
            ContentDao contentDao = new ContentDao();
            Content content = new Content();
            content.setId(i);
            content.setContentType(getContentType(getRandomIndex(0,4)));
            content.setContentGenre(getContentGenre(getRandomIndex(0,12)));
            content.setTitle(getRandomString(5,10));
            content.setYear(getRandomIndex(1980,2022));
            content.setDescription(getRandomString(30,300));
            content.setPoster("https://watchIt.com/"+getRandomString(5,20)+".jpg");
            content.setVideo("https://watchIt.com/"+getRandomString(5,20)+".mp4");
            content.setTotalRateScore(getRandomDouble(0.0,5.0));
            content.setAgeLimit(getRandomIndex(0,20));
            contentDao.insertContent(content);
        }
    }

    // get random number
    private static int getRandomIndex(Integer minNum,Integer count){
        int min = minNum;
        int max = count;
        int random = (int) ((Math.random() * (max - min)) + min);
        System.out.println(random);
        return random;
    }

    private static double getRandomDouble(double minNum, double maxNum){
        Random r = new Random();
        double randomValue = minNum + (maxNum - minNum) * r.nextDouble();
        return randomValue;
    }

    // get random string made with alphabets  & length range from 3~10
    private static String getRandomString(int minNum,int maxNum){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = getRandomIndex(minNum,maxNum);
        for(int i =0;i<length;i++){
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    // get random userStatus
    public static UserStatus getUserStatus(int index){
        if(index ==1) {
            return UserStatus.ACTIVE;
        }
        else{
            return UserStatus.INACTIVE;
        }
    }

    public static ContentType getContentType(int index){
        if(index ==1) {
            return ContentType.DRAMA;
        }
        if(index ==2){
            return ContentType.MOVIE;
        }
        if(index ==3){
            return ContentType.ENTERTAINMENT;
        }
        else{
            return ContentType.ORIGINAL;
        }
    }

    public static ContentGenre getContentGenre(int index){
        if(index ==1) {
            return ContentGenre.DRAMA;
        }
        if(index ==2){
            return ContentGenre.ACTION;
        }
        if(index ==3){
            return ContentGenre.COMEDY;
        }
        if(index==5){
            return ContentGenre.ADVENTURE;
        }
        if(index==6){
            return ContentGenre.FANTASY;
        }
        if(index==7){
            return ContentGenre.HORROR;
        }
        if(index==8){
            return ContentGenre.MYSTERY;
        }
        if(index==9){
            return ContentGenre.ROMANCE;
        }
        if(index==10){
            return ContentGenre.ROMENTIC_COMEDY;
        }
        if(index==11){
            return ContentGenre.SF;
        }
        else{
            return ContentGenre.ETC;
        }
    }
}

