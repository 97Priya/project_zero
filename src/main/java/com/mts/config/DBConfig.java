package com.mts.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConfig {

    public  static Connection  getMySqlConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/mts", "root", "root");
//here sonoo is database name, root is username and password
        } catch(Exception e){ System.out.println(e);}
        return null;
    }



}
