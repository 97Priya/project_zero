package com.mts.services;

import com.mts.config.ConnectionFactory;
import com.mts.dao.AccountDao;
import com.mts.dao.TransactionDao;
import com.mts.dto.AmoutTransferDetail;
import com.mts.exception.AccountNotFound;
import com.mts.exception.BalanceInsufficientException;
import com.mts.models.Account;
import com.mts.models.Transaction;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class MoneyTransferServiceImpl implements MoneyTransferService {
    private static Logger logger = Logger.getLogger("service1");
    private static MoneyTransferService moneyTransferService;
    private AccountDao accountDao;
    private TransactionDao transactionDao;

    private MoneyTransferServiceImpl(TransactionDao transactionDao, AccountDao accountDao) {
        this.transactionDao = transactionDao;
        this.accountDao = accountDao;
    }

    public AmoutTransferDetail getDetails(Scanner scanner) {
        AmoutTransferDetail amoutTransferDetail = new AmoutTransferDetail();
        System.out.print("Enter Sender's Account Number: ");

        amoutTransferDetail.setDebit_from(scanner.nextLong());
        System.out.print("Enter Reciever's Account Number: ");

        amoutTransferDetail.setCredit_to(scanner.nextLong());
        System.out.print("Enter the amount ");

        amoutTransferDetail.setAmount(scanner.nextDouble());
        return amoutTransferDetail;
    }

    public boolean transfer(AmoutTransferDetail amoutTransferDetail) {
        long senderAccNo = amoutTransferDetail.getDebit_from();
        long receiverAccNo = amoutTransferDetail.getCredit_to();
        double amt = amoutTransferDetail.getAmount();
        Connection connection = null;
        try {

            connection = ConnectionFactory.getMySqlConnection();
            connection.setAutoCommit(false);
            if (accountDao.getAccoutById(connection, senderAccNo) == null) {
                logger.error("Account " + senderAccNo + " not found in records");
                throw new AccountNotFound("Account " + senderAccNo + " not found in records");
            }
            if (accountDao.getAccoutById(connection, receiverAccNo) == null) {
                logger.error("Account " + receiverAccNo + " not found in records");
                throw new AccountNotFound("Account " + receiverAccNo + " not found in records");
            }
            if (accountDao.isAmountSufficientForTransaction(connection, senderAccNo, amt)) {
                Transaction transaction = new Transaction();
                transaction.setDebit_from(senderAccNo);
                transaction.setCredit_to(receiverAccNo);
                transaction.setAmount(amt);
                transactionDao.saveTransaction(connection, transaction);
                updateAccount(connection, transaction.getCredit_to(), transaction.getAmount(), "credit");
               boolean b = true;
                if (b) {
                    throw new IllegalStateException("boo00000om");
                }
                updateAccount(connection, transaction.getDebit_from(), transaction.getAmount(), "debit");
            } else {
                logger.error("account does'nt have sufficient balance");
                throw new BalanceInsufficientException("your account does'nt have sufficient balance");
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        }catch(AccountNotFound accountNotFound){
            System.err.println("Account not found");
        }catch (BalanceInsufficientException balanceInsufficientException){
            System.err.println("balance insuffiecient");
        }
        finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void updateAccount(Connection connection, long accNum, double amt, String type) throws SQLException {
        Account account = accountDao.getAccoutById(connection, accNum);
        if (type.equals("credit")) {
            account.setBalance(account.getBalance() + amt);
        } else if (type.equals("debit")) {
            account.setBalance(account.getBalance() - amt);
        }
        accountDao.updateAccount(connection, account);
    }

    public static MoneyTransferService getMoneyTransferService(TransactionDao transactionDao, AccountDao accountDao) {
        if (moneyTransferService == null) {
            moneyTransferService = new MoneyTransferServiceImpl(transactionDao, accountDao);
        }
        return moneyTransferService;

    }
}
