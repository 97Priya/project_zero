package com.mts.services;

import com.mts.dao.AccountDao;
import com.mts.exception.AccountNotFound;
import com.mts.models.Account;

import java.sql.SQLException;

public class AccountServices {

    AccountDao accountDao;

    public AccountServices(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public void createNewAccount(String name) {
        Account acc = new Account();
        acc.setAccountHolderName(name);
        accountDao.createNewAccount(acc);
    }

    public void updateBalance(long accountNum, double amt) {
        Account account = null;
        try {
            account = accountDao.getAccoutById(null, accountNum);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        account.setBalance(amt);
        if (account == null) {
            throw new AccountNotFound(+accountNum + "" + "not found");
        }
        accountDao.updateBalance(account);

    }
}
