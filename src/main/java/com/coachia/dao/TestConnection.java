package com.coachia.dao;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) throws Exception {
        System.out.println("Test de connexion...");
        Connection conn = DatabaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Connexion reussie !");
            conn.close();
        } else {
            System.out.println("Echec connexion !");
        }
    }
}