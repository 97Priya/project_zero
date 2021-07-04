package com.mts.dto;

import java.util.Date;

public class AmoutTransferDetail {

     private double amount;
     private long credit_to;
     private long debit_from;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getCredit_to() {
        return credit_to;
    }

    public void setCredit_to(long credit_to) {
        this.credit_to = credit_to;
    }

    public long getDebit_from() {
        return debit_from;
    }

    public void setDebit_from(long debit_from) {
        this.debit_from = debit_from;
    }
}
