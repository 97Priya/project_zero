package com.mts.contants;

public enum AccountTransactionMenu {


    	Top_10_Transaction("Press 1 for Top 10 Transactions: ",1),
	Current_month_Transactions("Press 2 for Current Month Transactions: ",2),
	Last_3_month_Transactions("Press 3 for Last 3 Month Transactions",3),
	FROM_DATE_TO_FROM_DATE("Press 4 to get Transaction between two dates: ",4);

    private String name;
    private  int value;

    AccountTransactionMenu(String name, int value) {
        this.name = name;
        this.value = value;
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

    @Override
    public String toString() {
        return "AccountTransactionMenu{" +
                "name='" + name + '\'' +
                '}';
    }
}
