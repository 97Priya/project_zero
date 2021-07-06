package com.mts.dao;

import com.mts.config.ConnectionFactory;
import com.mts.models.Account;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountDao {

   public Account getAccoutById(long account_num){
            Account account=null;
            ResultSet rs=null;
        try( Connection connection= ConnectionFactory.getMySqlConnection();){
            Statement stmt = connection.createStatement();
            rs = stmt.executeQuery("select * from accounts where account_number="+account_num);
             while (rs.next()){
                 account=new Account();
                account.setAccountNumber(rs.getLong("account_number"));
                account.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
         return  account;
   }

    public Boolean isAmountSufficientForTransaction(long account_num,double amt){
       Account account=getAccoutById(account_num);
        if(account.getBalance()>=amt){
            return  true;
        }
        else{
            return  false;
        }
    }

    public  void updateAccount(Account account){
         try(Connection connection= ConnectionFactory.getMySqlConnection();) {
            Statement statement=connection.createStatement();
            statement.executeUpdate("UPDATE `accounts` set balance= '"+account.getBalance() +"' where account_number='+"+account.getAccountNumber() +"'");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
