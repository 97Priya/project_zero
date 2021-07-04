package com.mts;

import com.mts.contants.Menu;
import com.mts.dto.AmoutTransferDetail;
import com.mts.models.Transaction;
import com.mts.services.MoneyTransferService;
import com.mts.services.MoneyTransferServiceImpl;

import java.util.EnumSet;
import java.util.Scanner;

public class App {
    MoneyTransferService moneyTransferService= MoneyTransferServiceImpl.getMoneyTransferService();

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

    public  static void main(String []args){
        new App().startMoneyTransfer();

    }
}
