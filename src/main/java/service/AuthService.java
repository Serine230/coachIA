package service;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;

public class AuthService {

    private UserDAO userDAO;

    public AuthService() {
        this.userDAO = new UserDAO();
    }

    // ===== INSCRIPTION =====
    public boolean inscrire(String nom, String email, String motDePasse) {
        // Vérifier si email déjà utilisé
        if (userDAO.findByEmail(email) != null) {
            return false;
        }

        // Hasher le mot de passe
        String motDePasseHashe = BCrypt.hashpw(motDePasse, BCrypt.gensalt());

        // Créer l'utilisateur
        User user = new User(nom, email, motDePasseHashe);
        userDAO.create(user);
        return true;
    }

    // ===== CONNEXION =====
    public User connecter(String email, String motDePasse) {
        User user = userDAO.findByEmail(email);

        if (user == null) {
            return null; // Email inexistant
        }

        // Vérifier le mot de passe
        if (BCrypt.checkpw(motDePasse, user.getMotDePasse())) {
            return user; // Connexion réussie
        }

        return null; // Mauvais mot de passe
    }

    // ===== VALIDATION EMAIL =====
    public boolean isEmailValide(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    // ===== VALIDATION MOT DE PASSE =====
    public boolean isMotDePasseValide(String motDePasse) {
        return motDePasse != null && motDePasse.length() >= 6;
    }
}