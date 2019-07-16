package com.rental.utils;

import java.sql.*;
import java.sql.DriverManager;

class DatabaseConnector {
    private static final String DBURL = "jdbc:mysql://localhost:3306/movierental_pn?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String DBUSER = "root";
    private static final String DBPASS = "mTr/2*>jvVW9NV@";
    Connection connect;

    DatabaseConnector() {
        try {
            connect = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
