package com.mts.dao;

import com.mts.config.ConnectionFactory;
import com.mts.models.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDao {

    Connection connection;

    public void createNewAccount(Account account){
        if(connection==null){
            try {
                connection=ConnectionFactory.getMySqlConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        int n=0;
        try{
            Statement stmt = connection.createStatement();
             n= stmt.executeUpdate("INSERT INTO accounts (account_holder_name) VALUES ('"+account.getAccountHolderName()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(n==1)
        System.out.println("Account created");
    }

    public  void  updateBalance(Account account){
        if(connection==null){
            try {
               connection= ConnectionFactory.getMySqlConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        int n=0;
        try{
            Statement stmt = connection.createStatement();
            n= stmt.executeUpdate("UPDATE accounts set balance= '" + account.getBalance()+ "' where account_number='+" +account.getAccountNumber() + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(n==1)
            System.out.println("Account Updated");
    }

    public Account getAccoutById(Connection connection, long account_num) throws SQLException {
        if(connection==null){
            connection=ConnectionFactory.getMySqlConnection();
        }
        Account account = null;
        ResultSet rs = null;

        Statement stmt = connection.createStatement();
        rs = stmt.executeQuery("select * from accounts where account_number=" + account_num);
        while (rs.next()) {
            account = new Account();
            account.setAccountNumber(rs.getLong("account_number"));
            account.setBalance(rs.getDouble("balance"));
        }
        return account;
    }

    public Boolean isAmountSufficientForTransaction(Connection connection, long account_num, double amt) throws SQLException {
        Account account = getAccoutById(connection, account_num);
        if (account.getBalance() >= amt) {
            return true;
        } else {
            return false;
        }
    }

    public void updateAccount(Connection connection, Account account) throws SQLException {

        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE `accounts` set balance= '" + account.getBalance() + "' where account_number='+" + account.getAccountNumber() + "'");


    }

}
