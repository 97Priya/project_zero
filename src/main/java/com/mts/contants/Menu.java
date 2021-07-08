package com.mts.contants;

public enum Menu {
    TRANSFER_AMOUNT("Press 1 to Start amount transfer",1),
    VIEW_TRANSACTIONS("Press 2 to View Transactions",2),
    CREATEACCOUNT("Press 3 to create a new Account",3),
    UPDATEBALANCE("Press 4 to update your balance",4);

    Menu(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;
    private  int value;

    @Override public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
