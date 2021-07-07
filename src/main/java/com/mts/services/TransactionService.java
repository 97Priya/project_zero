package com.mts.services;

import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.models.Account;
import com.mts.models.Transaction;

import java.sql.Date;
import java.util.List;

public class TransactionService {

    private  TransactionDao transactionDao;
    private AccountDao accountDao;

    public TransactionService(TransactionDao transactionDao, AccountDao accountDao){
        this.transactionDao=transactionDao;
        this.accountDao=accountDao;
    }

        public void getTopTenTransaction(long accountNum){
            Account account=accountDao.getAccoutById(accountNum);
            transactionDao.getTop_Ten_Transactions(account).stream().forEach(System.out::println);
        }

    public void getThisMonthTransaction(long accountNum){
        Account account=accountDao.getAccoutById(accountNum);
        transactionDao.getCurrent_month_Transactions(account).stream().forEach(System.out::println);
        }

    public void getLastThreeMonthTransaction(long accountNum){
        Account account=accountDao.getAccoutById(accountNum);
        transactionDao.getLast_Three_month_Transactions(account).stream().forEach(System.out::println);
        }

   public void getBetwenDateTransaction(long accountNum, Date toDate,Date fromDate){
        Account account=accountDao.getAccoutById(accountNum);
        transactionDao.getBetween_Date_Transaction(account,toDate,fromDate).stream().forEach(System.out::println);

    }
}
