package com.rental.utils;

import java.sql.*;
import java.sql.DriverManager;

/**
 * Provides connection to use the mySql database with program.<p>
 * Contains all necessary data for correct connect between program and mySQL server.
 *
 * @author Piotr Nawrocki
 */
class DatabaseConnector {
    private static final String DBURL = "jdbc:mysql://localhost:3306/movierental_pn?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DBUSER = "root";
    private static final String DBPASS = "mTr/2*>jvVW9NV@";
    Connection connect;

    /**
     * The DatabaseConnector constructor provides information about mySQL connection parameters.
     */
    DatabaseConnector() {
        try {
            connect = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
