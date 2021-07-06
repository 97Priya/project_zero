package com.mts.dao;

import com.mts.config.ConnectionFactory;
import com.mts.contants.AccountTransactionMenu;
import com.mts.models.Account;
import com.mts.models.Transaction;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao implements TransactionRepository {

    private AccountDao accountDao=new AccountDao();

     public boolean saveTransaction(Transaction transaction) {
         ResultSet rs=null;
        try(Connection connection=ConnectionFactory.getMySqlConnection()) {
            Statement stmt = connection.createStatement();
            java.sql.Date myDate= Date.valueOf(LocalDate.now());
            int n= stmt.executeUpdate("INSERT INTO transactions (time_stamp,amount,debit_from,credit_to) VALUES ('"+myDate+"','"+transaction.getAmount()+"','"+transaction.getDebit_from()+"','"+transaction.getCredit_to()+"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
     }

    @Override
    public List<Transaction> getTop_Ten_Transactions(Account account) {
        List<Transaction> transactions=new ArrayList<>();
        try(Connection connection=ConnectionFactory.getMySqlConnection()) {
            String sql="select * from transactions where debit_from=? or credit_to=? and row_count()<=10";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,account.getAccountNumber());
            preparedStatement.setLong(2,account.getAccountNumber());
            ResultSet resultSet=preparedStatement.executeQuery();
           while (resultSet.next()){
               Transaction transaction=new Transaction();
               transaction.setTranasction_id(resultSet.getLong(1));
               transaction.setTime_stamp(resultSet.getTimestamp(2));
               transaction.setAmount(resultSet.getDouble(3));
               transaction.setDebit_from(resultSet.getLong(4));
               transaction.setCredit_to(resultSet.getLong(5));
               transactions.add(transaction);
           }
            } catch (SQLException e) {
            e.printStackTrace();
        }
        return  transactions;
    }

    @Override
    public List<Transaction> getCurrent_month_Transactions(Account account) {
        List<Transaction> transactions=new ArrayList<>();
        try(Connection connection=ConnectionFactory.getMySqlConnection()) {
            String sql="select * from transactions where debit_from=? or credit_to=? and MONTH(time_stamp)=MONTH(now()) and YEAR(time_stamp)=YEAR(now())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,account.getAccountNumber());
            preparedStatement.setLong(2,account.getAccountNumber());
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction=new Transaction();
                transaction.setTranasction_id(resultSet.getLong(1));
                transaction.setTime_stamp(resultSet.getTimestamp(2));
                transaction.setAmount(resultSet.getDouble(3));
                transaction.setDebit_from(resultSet.getLong(4));
                transaction.setCredit_to(resultSet.getLong(5));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  transactions;
    }

    @Override
    public List<Transaction> getLast_Three_month_Transactions(Account account) {
        List<Transaction> transactions=new ArrayList<>();
        try(Connection connection=ConnectionFactory.getMySqlConnection()) {
            String sql="select * from transactions where debit_from=? or credit_to=? and MONTH(time_stamp)>=MONTH(now())-3 and YEAR(time_stamp)=YEAR(now())";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,account.getAccountNumber());
            preparedStatement.setLong(2,account.getAccountNumber());
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction=new Transaction();
                transaction.setTranasction_id(resultSet.getLong(1));
                transaction.setTime_stamp(resultSet.getTimestamp(2));
                transaction.setAmount(resultSet.getDouble(3));
                transaction.setDebit_from(resultSet.getLong(4));
                transaction.setCredit_to(resultSet.getLong(5));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  transactions;
    }

    @Override
    public List<Transaction> getBetween_Date_Transaction(Account account, Date to_date, Date from_date) {
        List<Transaction> transactions=new ArrayList<>();
        try(Connection connection=ConnectionFactory.getMySqlConnection()) {
            String sql="SELECT * FROM mts.transactions WHERE debit_from=? or credit_to=? and time_stamp BETWEEN ? AND ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1,account.getAccountNumber());
            preparedStatement.setLong(2,account.getAccountNumber());
            preparedStatement.setDate(3,to_date);
            preparedStatement.setDate(4,from_date);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()){
                Transaction transaction=new Transaction();
                transaction.setTranasction_id(resultSet.getLong(1));
                transaction.setTime_stamp(resultSet.getTimestamp(2));
                transaction.setAmount(resultSet.getDouble(3));
                transaction.setDebit_from(resultSet.getLong(4));
                transaction.setCredit_to(resultSet.getLong(5));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  transactions;
    }

}
