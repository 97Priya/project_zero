package com.mts;

import com.mts.contants.AccountTransactionMenu;
import com.mts.contants.Menu;
import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.dto.AmoutTransferDetail;
import com.mts.models.Account;
import com.mts.models.Transaction;
import com.mts.services.AccountServices;
import com.mts.services.MoneyTransferService;
import com.mts.services.MoneyTransferServiceImpl;
import com.mts.services.TransactionService;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.EnumSet;
import java.util.Scanner;

public class App {
    private static Logger logger = Logger.getLogger("mts");
    static TransactionDao transactionDao=new TransactionDao();
    static AccountDao accountDao=new AccountDao();
    AccountServices accountServices=new AccountServices(accountDao);
    MoneyTransferService moneyTransferService= MoneyTransferServiceImpl.getMoneyTransferService(transactionDao,accountDao);

    static void  getRequestedTransactions(Scanner scanner){
        for (AccountTransactionMenu menu:EnumSet.allOf(AccountTransactionMenu.class)){
            System.out.println(menu);
        }
        int input;
        input = scanner.nextInt();
        System.out.print("Enter the account Number: ");
        long accountNum=scanner.nextLong();
        TransactionService transactionService=new TransactionService(transactionDao,accountDao);
        try {
            if (input == 1) {
                transactionService.getTopTenTransaction(accountNum);
            } else if (input == 2) {
                transactionService.getThisMonthTransaction(accountNum);
            } else if (input == 3) {
                transactionService.getLastThreeMonthTransaction(accountNum);
            } else if (input == 4) {
                System.out.println("Enter two dates in between you wish to see transactions: ");
                System.out.print("Enter start date as dd-MM-yyyy:  ");
                Date toDate = getDate(scanner.next());
                System.out.print("Enter end date as dd-MM-yyyy:  ");
                Date fromDate = getDate(scanner.next());
                transactionService.getBetwenDateTransaction(accountNum, toDate, fromDate);
            } else {
                System.out.println("Invalid Input");
            }
        }catch (SQLException e){
            e.printStackTrace();
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
                }else if(input==3){
                    System.out.print("Enter your name: ");
                    accountServices.createNewAccount(scanner.next());
                }
                else if(input==4){
                    System.out.print("Enter your account Number");
                    long acc=scanner.nextLong();
                    System.out.print("Enter new balance: ");
                    double amt=scanner.nextDouble();
                    accountServices.updateBalance(acc,amt);
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

    static Date getDate(String startDate){

        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date date = null;
        try {
            date = sdf1.parse(startDate);
        } catch (ParseException e) {
              e.printStackTrace();
        }
        java.sql.Date sqlStartDate = new java.sql.Date(date.getTime());
        return sqlStartDate;
    }


    public  static void main(String []args){
        new App().startMoneyTransfer();

    }
}
