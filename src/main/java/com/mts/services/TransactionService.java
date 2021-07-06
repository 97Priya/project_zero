package com.mts.services;

import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.models.Account;
import com.mts.models.Transaction;

import java.sql.Date;
import java.util.List;

public class TransactionService {

    private  static TransactionDao transactionDao=new TransactionDao();
    AccountDao accountDao=new AccountDao();

        public void getTopTenTransaction(long accountNum){
            Account account=accountDao.getAccoutById(accountNum);
            List<Transaction> transactions=transactionDao.getTop_Ten_Transactions(account);
            for(Transaction transaction:transactions){
                System.out.println(transaction);
            }
        }
    public void getThisMonthTransaction(long accountNum){
        Account account=accountDao.getAccoutById(accountNum);
        List<Transaction> transactions=transactionDao.getCurrent_month_Transactions(account);
        for(Transaction transaction:transactions){
            System.out.println(transaction);
        }
    }

    public void getLastThreeMonthTransaction(long accountNum){
        Account account=accountDao.getAccoutById(accountNum);
        List<Transaction> transactions=transactionDao.getLast_Three_month_Transactions(account);
        for(Transaction transaction:transactions){
            System.out.println(transaction);
        }
    }

   public void getBetwenDateTransaction(long accountNum, Date toDate,Date fromDate){
        Account account=accountDao.getAccoutById(accountNum);
        List<Transaction> transactions=transactionDao.getBetween_Date_Transaction(account,toDate,fromDate);
        for(Transaction transaction:transactions){
            System.out.println(transaction);
        }
    }
}
