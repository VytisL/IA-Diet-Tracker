package com.benedict.DietTracker.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseDriver {
    private Connection conn;
    private static final Logger logger = Logger.getLogger(DatabaseDriver.class.getName());

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:dietTrackerDB.db");
        } catch (SQLException e) {
            logger.severe("Cannot connect to database: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.severe("Error closing database connection: " + e.getMessage());
        }
    }
}
