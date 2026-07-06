package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

public class UserDAO {

    private Connection connection;

    public UserDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User searchByUsername(String username) {
        User u = null;

        try {
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                u = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return u;
    }

    public int updatePassword(int userId, String newHashedPassword) {
        try {
            String sql = "UPDATE users SET password=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, newHashedPassword);
            stmt.setInt(2, userId);

            stmt.executeUpdate();
            return 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }
}