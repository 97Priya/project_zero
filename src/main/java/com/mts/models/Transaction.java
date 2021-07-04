package com.mts.models;

import java.util.Date;

public class Transaction {
    private double tranasction_id;
    private Date time_stamp;
    private double amount;
    private long credit_to;
   private long debit_from;




    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getTranasction_id() {
        return tranasction_id;
    }

    public void setTranasction_id(double tranasction_id) {
        this.tranasction_id = tranasction_id;
    }

    public Date getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Date time_stamp) {
        this.time_stamp = time_stamp;
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
