package com.mts.dao;

import com.mts.models.Account;
import com.mts.models.Transaction;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public interface TransactionRepository {

    boolean saveTransaction(Connection connection,Transaction transaction);

    List<Transaction> getTop_Ten_Transactions(Account account);
	List<Transaction> getCurrent_month_Transactions(Account account);
	List<Transaction> getLast_Three_month_Transactions(Account account);
	List<Transaction> getBetween_Date_Transaction(Account account,Date to_date,Date from_date);

}
