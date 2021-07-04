package com.mts.dao;

import com.mts.config.DBConfig;
import com.mts.models.Account;
import com.mts.models.Transaction;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TransactionDao {

    private AccountDao accountDao=new AccountDao();
    private Connection connection= DBConfig.getMySqlConnection();

    public boolean saveTransactionA(Transaction transaction) {

        ResultSet rs=null;
        try {
            Statement stmt = connection.createStatement();
            java.sql.Date myDate= Date.valueOf(LocalDate.now());
            int n= stmt.executeUpdate("INSERT INTO transactions (time_stamp,amount,debit_from,credit_to) VALUES ('"+myDate+"','"+transaction.getAmount()+"','"+transaction.getDebit_from()+"','"+transaction.getCredit_to()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }




}
