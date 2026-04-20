package com.coachia.service;

import com.coachia.model.User;

public class SessionManager {

    // L'utilisateur connecté gardé en mémoire
    private static User currentUser = null;

    // Connecter un utilisateur
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    // Récupérer l'utilisateur connecté
    public static User getCurrentUser() {
        return currentUser;
    }

    // Vérifier si quelqu'un est connecté
    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    // Déconnecter
    public static void logout() {
        currentUser = null;
    }
}