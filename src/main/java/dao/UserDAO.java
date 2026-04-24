package dao;

import model.User;
import java.sql.*;

public class UserDAO {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/coachIA";
        String user = "root";
        String password = "";
        return DriverManager.getConnection(url, user, password);
    }

    // ===== CRÉER UN UTILISATEUR =====
    public boolean create(User user) {
        String sql = "INSERT INTO users (nom, email, mot_de_passe) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getMotDePasse());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur create user: " + e.getMessage());
            return false;
        }
    }

    // ===== TROUVER PAR EMAIL =====
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Erreur findByEmail: " + e.getMessage());
        }
        return null;
    }

    // ===== TROUVER PAR ID =====
    public User findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setEmail(rs.getString("email"));
                user.setMotDePasse(rs.getString("mot_de_passe"));
                return user;
            }

        } catch (SQLException e) {
            System.out.println("Erreur findById: " + e.getMessage());
        }
        return null;
    }

    // ===== METTRE À JOUR PROFIL =====
    public boolean updateProfile(User user) {
        String sql = "UPDATE users SET nom = ?, email = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getEmail());
            stmt.setInt(3, user.getId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.out.println("Erreur updateProfile: " + e.getMessage());
            return false;
        }
    }
}