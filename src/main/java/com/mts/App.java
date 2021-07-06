package com.mts;

import com.mts.contants.AccountTransactionMenu;
import com.mts.contants.Menu;
import com.mts.dao.TransactionDao;
import com.mts.dto.AmoutTransferDetail;
import com.mts.models.Account;
import com.mts.models.Transaction;
import com.mts.services.MoneyTransferService;
import com.mts.services.MoneyTransferServiceImpl;
import com.mts.services.TransactionService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Scanner;

public class App {
    MoneyTransferService moneyTransferService= MoneyTransferServiceImpl.getMoneyTransferService();
    TransactionDao transactionDao=new TransactionDao();


    static void  getRequestedTransactions(Scanner scanner){
        for (AccountTransactionMenu menu:EnumSet.allOf(AccountTransactionMenu.class)){
            System.out.println(menu);
        }
        int input;
        input = scanner.nextInt();
        System.out.print("Enter the account Number: ");
        long accountNum=scanner.nextLong();
        TransactionService transactionService=new TransactionService();
        if(input==1){
            transactionService.getTopTenTransaction(accountNum);
        }else if(input==2){
            transactionService.getThisMonthTransaction(accountNum);
        }else  if(input==3){
            transactionService.getLastThreeMonthTransaction(accountNum);
        }else if(input==4){
            System.out.println("Enter two dates in between you wish to see transactions: ");
            Date toDate=dateParser(scanner);
            Date fromDate=dateParser(scanner);
            transactionService.getBetwenDateTransaction(accountNum,toDate,fromDate);
        }else{
            System.out.println("Invalid Input");
        }
    }

    void startMoneyTransfer(){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Welcome to Money Transfer System");
        while(true) {
            System.out.println("Press 1 to Continue......");
            System.out.println("Press 0 to exit");
            int input = scanner.nextInt();
            if (input == 1) {
                for (Menu menu : EnumSet.allOf(Menu.class)) {
                    System.out.println(menu);
                }
                input = scanner.nextInt();
                if (input == 1) {
                    AmoutTransferDetail amoutTransferDetail = moneyTransferService.getDetails(scanner);
                    moneyTransferService.transfer(amoutTransferDetail);
                } else if (input == 2) {
                    getRequestedTransactions(scanner);
                }
                else {
                    System.out.println("Invalid option");
                }
            }
            else if(input==0){
                break;
            }else{
                System.out.println("Invalid option...");
            }
        }
    }

    static Date dateParser(Scanner scanner){
           String str[] = {"year", "month", "day" };
        String date = "";

        for(int i=0; i<3; i++) {
            System.out.println("Enter " + str[i] + ": ");
            date = date + scanner.next() + "/";
        }
        date = date.substring(0, date.length()-1);
        System.out.println("date: "+ date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date parsedDate = null;


        try {
            parsedDate = (Date) dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return parsedDate;
    }

    public  static void main(String []args){
        new App().startMoneyTransfer();

    }
}
