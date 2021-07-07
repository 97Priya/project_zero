package com.mts.services;

import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.dto.AmoutTransferDetail;
import com.mts.exception.AccountNotFound;
import com.mts.exception.BalanceInsufficientException;
import com.mts.models.Account;
import com.mts.models.Transaction;

import java.util.Scanner;

public class MoneyTransferServiceImpl implements  MoneyTransferService{

    private static MoneyTransferService moneyTransferService;
    private  static AccountDao accountDao=new AccountDao();
    private static TransactionDao transactionDao=new TransactionDao();

    private MoneyTransferServiceImpl() {

    }

    public AmoutTransferDetail getDetails(Scanner scanner) {
        AmoutTransferDetail amoutTransferDetail=new AmoutTransferDetail();
        System.out.print("Enter Sender's Account Number: ");

        amoutTransferDetail.setDebit_from(scanner.nextLong());
        System.out.print("Enter Reciever's Account Number: ");

        amoutTransferDetail.setCredit_to(scanner.nextLong());
        System.out.print("Enter the amount ");

        amoutTransferDetail.setAmount(scanner.nextDouble());
        return amoutTransferDetail;
    }

    public boolean transfer(AmoutTransferDetail amoutTransferDetail) {
        long senderAccNo=amoutTransferDetail.getDebit_from();
        long receiverAccNo=amoutTransferDetail.getCredit_to();
        double amt=amoutTransferDetail.getAmount();
        if(accountDao.getAccoutById(receiverAccNo)==null){
            throw new AccountNotFound("Account "+receiverAccNo +" not found in records");
        }
        if(accountDao.getAccoutById(senderAccNo)==null){
            throw new AccountNotFound("Account "+senderAccNo +" not found in records");
        } if(accountDao.isAmountSufficientForTransaction(senderAccNo,amt)){
            Transaction transaction=new Transaction();
            transaction.setDebit_from(senderAccNo);
            transaction.setCredit_to(receiverAccNo);
            transaction.setAmount(amt);
            transactionDao.saveTransaction(transaction);
            updateAccount(transaction.getCredit_to(),transaction.getAmount(),"credit");
            updateAccount(transaction.getDebit_from(),transaction.getAmount(),"debit");
        }else{
            throw new BalanceInsufficientException("your account does'nt have sufficient balance");
        }

          return true;
    }

    public void updateAccount(long accNum,double amt,String type){
        Account account=accountDao.getAccoutById(accNum);
        if(type.equals("credit")){
            account.setBalance(account.getBalance()+amt);
        }
        else if(type.equals("debit")){
            account.setBalance(account.getBalance()-amt);
        }
        accountDao.updateAccount(account);
    }
    public static MoneyTransferService getMoneyTransferService(){
        if(moneyTransferService==null){
           moneyTransferService= new MoneyTransferServiceImpl();
        }
            return moneyTransferService;

    }
}
