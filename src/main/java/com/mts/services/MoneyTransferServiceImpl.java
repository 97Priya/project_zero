package com.mts.services;

import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.dto.AmoutTransferDetail;
import com.mts.exception.AccountNotFound;
import com.mts.exception.BalanceInsufficientException;
import com.mts.models.Account;
import com.mts.models.Transaction;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class MoneyTransferServiceImpl implements  MoneyTransferService{
    private static Logger logger = Logger.getLogger("service1");
    private static MoneyTransferService moneyTransferService;
    private   AccountDao accountDao;
    private  TransactionDao transactionDao;

    private MoneyTransferServiceImpl(TransactionDao transactionDao,AccountDao accountDao) {
            this.transactionDao=transactionDao;
            this.accountDao=accountDao;
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
            logger.error("Account "+receiverAccNo +" not found in records");
            throw new AccountNotFound("Account "+receiverAccNo +" not found in records");
        }
        if(accountDao.getAccoutById(senderAccNo)==null){
            logger.error("Account "+senderAccNo +" not found in records");
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
            logger.error("account does'nt have sufficient balance");
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
    public static MoneyTransferService getMoneyTransferService(TransactionDao transactionDao,AccountDao accountDao){
        if(moneyTransferService==null){
           moneyTransferService= new MoneyTransferServiceImpl(transactionDao,accountDao);
        }
            return moneyTransferService;

    }
}
