package com.coachia.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection { private static final String URL = "jdbc:mysql://localhost:3306/coach_ia";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // vide avec WAMP

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
