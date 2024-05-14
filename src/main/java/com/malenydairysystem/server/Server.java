/*
    Students: Tina Losin (10569238)
    Description: Manages server operations, including handling multiple client connections simultaneously using a thread-per-connection 
                 model, and synchronizing access to the database during concurrent operations.
    
    
 */
package com.malenydairysystem.server;

import com.malenydairysystem.database.DatabaseManager;
import java.sql.SQLException;

public class Server
{

    public static void main(String[] args) throws SQLException
    {
        // Create the DatabaseUtility Object
        DatabaseManager database = new DatabaseManager();

        // Create the Database
        database.createDatabase();

        // Create the Database Tables
        database.createTables();
    }
}
