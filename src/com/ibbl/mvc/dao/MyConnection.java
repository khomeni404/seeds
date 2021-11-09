/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ibbl.mvc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {
    
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    public static Connection getDBConnection(){
        Connection dbConnection = null;
        try{
            Class.forName(DB_DRIVER);
            
        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }   
        
        try{
            dbConnection = (Connection) DriverManager.getConnection(DB_CONNECTION+"/seeds", DB_USER, DB_PASSWORD);
            return dbConnection;
       }catch (SQLException e){
            System.out.println(e.getMessage());
       }        
        return dbConnection;
    }
    public static Connection getDBConnection2(){
        Connection dbConnection = null;
        try{
            Class.forName(DB_DRIVER);

        }catch (ClassNotFoundException e){
            System.out.println(e.getMessage());
        }

        try{
            dbConnection = (Connection) DriverManager.getConnection(DB_CONNECTION+"/aa_vision", DB_USER, DB_PASSWORD);
            return dbConnection;
       }catch (SQLException e){
            System.out.println(e.getMessage());
       }
        return dbConnection;
    }
}
