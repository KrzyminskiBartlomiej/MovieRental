package com.rental.utils;

import java.sql.*;
import java.sql.DriverManager;

class DatabaseConnector {
    private final static String DBURL = "jdbc:mysql://localhost:3306/movierental_pn?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private final static String DBUSER = "root";
    private final static String DBPASS = "mTr/2*>jvVW9NV@";
    Connection connect;

    DatabaseConnector() {
        try {
            connect = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
