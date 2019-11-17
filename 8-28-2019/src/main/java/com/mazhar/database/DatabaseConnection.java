/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pk
 */
public class DatabaseConnection {
    private static DatabaseConnection connectorManager = null;
    private Connection connection = null;


    public void createConnection() throws SQLException, ClassNotFoundException {
//        Class.forName("com.mysql.jdbc.Driver");
//        String url = "jdbc:mysql://localhost:3306/coaching_center?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//        String name = "root";
//        String password = "";

        connection =  DriverManager.
                    getConnection("jdbc:mysql://localhost/facebookbot?userUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
    }

    public Connection getDBConnecton() throws SQLException {
        if (connection == null) {
            try {
                createConnection();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return connection;
    }

 public static DatabaseConnection getConnectorManager(){
     if(connectorManager==null){
         connectorManager= new DatabaseConnection();
     }
     return connectorManager;
 }
}
