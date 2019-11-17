/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mazhar.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author mazhar
 */
public class DatabaseInit {
    public Connection getInitialize(){
        try {
            return DatabaseConnection.getConnectorManager().getDBConnecton();
        } catch (SQLException ex) {
            return null;
        }
    }
}
