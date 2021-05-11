package com.watchIt;

import java.sql.SQLException;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws SQLException {
	// write your code here
        for(int i =1;i<=10;i++){
            UserDao userDao = new UserDao();
            User user = new User();
            user.setId(i);
            user.setUserAge(getRandomIndex(10,60));
            user.setPassword(getRandomString());
            user.setUsername(getRandomString());
            user.setUserStatus(getUserStatus(getRandomIndex(0,2)));
            userDao.insertUser(user);
        }
    }


    private static int getRandomIndex(Integer minNum,Integer count){
        int min = minNum;
        int max = count;
        int random = (int) ((Math.random() * (max - min)) + min);
        System.out.println(random);
        return random;
    }

    private static String getRandomString(){
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int length = getRandomIndex(3,10);
        for(int i =0;i<length;i++){
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public static UserStatus getUserStatus(int index){
        if(index ==1) {
            return UserStatus.ACTIVE;
        }
        else{
            return UserStatus.INACTIVE;
        }
    }
}

