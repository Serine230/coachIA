package com.coachia.service;

import com.coachia.dao.impl.UserDAOImpl;
import com.coachia.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

public class AuthService {

    private UserDAOImpl userDAO;

    public AuthService() {
        this.userDAO = new UserDAOImpl();
    }

    // ─── INSCRIPTION ───────────────────────────
    public User register(String username, String email, String password) {

        // 1. Vérifier que l'email n'existe pas déjà
        Optional<User> existing = userDAO.findByEmail(email);
        if (existing.isPresent()) {
            throw new RuntimeException("Email déjà utilisé !");
        }

        // 2. Hasher le mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // 3. Créer l'utilisateur
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPasswordHash(hashedPassword);

        // 4. Sauvegarder en base
        return userDAO.create(newUser);
    }

    // ─── CONNEXION ─────────────────────────────
    public Optional<User> login(String email, String password) {

        // 1. Chercher l'utilisateur par email
        Optional<User> userOpt = userDAO.findByEmail(email);

        // 2. Vérifier le mot de passe
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (BCrypt.checkpw(password, user.getPasswordHash())) {
                return Optional.of(user); // ✅ Login réussi
            }
        }

        return Optional.empty(); // ❌ Échec
    }
}