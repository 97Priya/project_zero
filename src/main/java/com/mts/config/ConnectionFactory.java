package com.mts.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


//factory class
public class ConnectionFactory {
    private  static Properties properties=new Properties();

    static {
        try {
            FileInputStream fis=new FileInputStream("src/main/resources/db.properties");
            properties.load(fis);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static{

        try {
            Class.forName(properties.getProperty("db.driver"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public  static Connection  getMySqlConnection() throws SQLException {

            String url=properties.getProperty("db.url");
            String username=properties.getProperty("db.username");
            String password=properties.getProperty("db.password");

            return DriverManager.getConnection(url,username,password);


    }



}
