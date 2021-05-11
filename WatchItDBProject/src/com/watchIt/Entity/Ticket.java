package com.watchIt.Entity;

import com.watchIt.enums.TicketType;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:39 오전
*/
public class Ticket {
    private int id;
    private TicketType ticketType;
    private int price;

    public Ticket(){};

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public TicketType getTicketType(){
        return ticketType;
    }

    public void setTicketType(TicketType ticketType){
        this.ticketType = ticketType;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price= price;
    }
}
