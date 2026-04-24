package com.coachia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/coach_ia";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // ton mot de passe WAMP

    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Connexion DB réussie !");
            } catch (SQLException e) {
                System.out.println("❌ Erreur connexion DB : " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }
}