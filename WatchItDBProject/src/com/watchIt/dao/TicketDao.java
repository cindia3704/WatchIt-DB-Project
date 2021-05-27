package com.watchIt.dao;

import com.watchIt.Entity.LoggedInUser;
import com.watchIt.Entity.Orders;
import com.watchIt.Entity.Ticket;
import com.watchIt.Entity.User;
import com.watchIt.enums.TicketType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * @author : Jisoo Kim
 * @date: 2021/05/12 7:41 오전
*/
public class TicketDao {
    public static void insertTicket(Ticket ticket, Connection conn) throws SQLException {
        String sqlStmt = "insert into ticket values(?,?,?);";
        PreparedStatement pStmt = null;
        try{
            pStmt = conn.prepareStatement(sqlStmt);
            pStmt.setInt(1,ticket.getId());
            pStmt.setString(2,ticket.getTicketType().toString());
            pStmt.setInt(3,ticket.getPrice());
            pStmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            pStmt.close();
        }
    }

    public static String getTicketTypes(Scanner sc,User user, Connection conn) throws SQLException{
        String sqlStmt = "select * from ticket;";
        PreparedStatement pStmt = conn.prepareStatement(sqlStmt);
        String option ="4";
        System.out.println("\nPlease choose 1 of the options:");
        try {
            List<Ticket> tickets = new ArrayList<Ticket>();
            ResultSet resultSet = pStmt.executeQuery();
            Integer index = 1;
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String ticketType = resultSet.getString(2);
                Integer price = resultSet.getInt(3);
                TicketType type = null;
                if (ticketType.equals("BASIC")) {
                    type = TicketType.BASIC;
                } else if (ticketType.equals("STANDARD")) {
                    type = TicketType.STANDARD;
                } else {
                    type = TicketType.PREMIUM;
                }

                Ticket newTicket = new Ticket(id, price, type);
                tickets.add(newTicket);
                System.out.println(index.toString() + ") Type: " + newTicket.getTicketType().toString() + "   Price: " + newTicket.getPrice());
                index++;
            }
            System.out.println(index.toString() + ") Exit");

            System.out.print("Option number: ");
            option = sc.nextLine();
            Integer select=null;
            try{
                select =Integer.parseInt(option);
            }
            catch(NumberFormatException ex){
                System.out.println("Its not a valid Integer");
            }
            while(select < 0 && select >index){
                System.out.println("Wrong input.. Try agian");
                System.out.print("Option number: ");
                option = sc.nextLine();
                try{
                    select =Integer.parseInt(option);
                }
                catch(NumberFormatException ex){
                    System.out.println("Its not a valid Integer");
                }
            }

            if(select!=4){
                Statement stmt3 = conn.createStatement();
                ResultSet rs3 = stmt3.executeQuery("SELECT MAX(id) as total FROM orders");
                Integer total = 0;
                int month = LocalDate.now().getMonthValue();
                int year = LocalDate.now().getYear();
                int day = LocalDate.now().getDayOfMonth();

                int nextMonth = LocalDate.now().plusMonths(1).getMonthValue();
                while(rs3.next()){
                    total = rs3.getInt("total");
                }
                System.out.println("TOTAL : "+total.toString());
                Orders orders = new Orders();
                orders.setId(total+1);
                orders.setTicketId(select);
                orders.setUserId(user.getId());
                orders.setStartDate(Date.valueOf(LocalDate.of(year,month,day)));
                orders.setEndDate(Date.valueOf(LocalDate.of(year,nextMonth,day)));

                OrdersDao.insertOrders(orders,conn);

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
        return option;
    }
}
