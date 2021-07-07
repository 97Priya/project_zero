package com.mts.services;

import com.mts.config.ConnectionFactory;
import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.models.Account;
import com.mts.models.Transaction;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TransactionService {

    private  TransactionDao transactionDao;
    private AccountDao accountDao;

    public TransactionService(TransactionDao transactionDao, AccountDao accountDao){
        this.transactionDao=transactionDao;
        this.accountDao=accountDao;
    }

        public void getTopTenTransaction(long accountNum) throws SQLException {

            Account account=accountDao.getAccoutById(null,accountNum);
            transactionDao.getTop_Ten_Transactions(account).stream().forEach(System.out::println);
        }

    public void getThisMonthTransaction(long accountNum) throws SQLException {
        Account account=accountDao.getAccoutById(null,accountNum);
        transactionDao.getCurrent_month_Transactions(account).stream().forEach(System.out::println);
        }

    public void getLastThreeMonthTransaction(long accountNum) throws SQLException {
        Account account=accountDao.getAccoutById(null,accountNum);
        transactionDao.getLast_Three_month_Transactions(account).stream().forEach(System.out::println);
        }

   public void getBetwenDateTransaction(long accountNum, Date toDate,Date fromDate) throws SQLException {
        Account account=accountDao.getAccoutById(null,accountNum);
        transactionDao.getBetween_Date_Transaction(account,toDate,fromDate).stream().forEach(System.out::println);

    }
}
