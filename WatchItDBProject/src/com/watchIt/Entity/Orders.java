package com.watchIt.Entity;

import java.sql.Date;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:49 오전
*/
public class Orders {
    private int id;
    private Date startDate;
    private Date endDate;
    private int userId;
    private int ticketId;

    public Orders(){};

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
        this.startDate = startDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public void setEndDate(Date endDate){
        this.endDate = endDate;
    }

    public int getUserId(){
        return userId;
    }

    public void setUserId(int userId){
        this.userId = userId;
    }

    public int getTicketId(){
        return ticketId;
    }

    public void setTicketId(int ticketId){
        this.ticketId = ticketId;
    }
}
