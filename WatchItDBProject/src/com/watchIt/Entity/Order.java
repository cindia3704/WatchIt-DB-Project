package com.watchIt.Entity;

import java.util.Date;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 8:01 오전
*/
public class Order {
    private int id;
    private Date startDate;
    private Date endDate;
    private int ticketId;
    private int userId;

    public Order(){};

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Date getStartDate(){
        return startDate;
    }

    public void setStartDate(Date startDate){
        this.startDate=startDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public void setEndDate(Date endDate){
        this.endDate=endDate;
    }

    public int getTicketId(){
        return ticketId;
    }

    public void setTicketId(int ticketId){
        this.ticketId=ticketId;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId=userId;
    }

}
