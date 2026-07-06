package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Guest;

public class GuestDAO {

    private Connection connection;

    public GuestDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(GuestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int create(Guest g) {
        try {
            String sql = "INSERT INTO guests (name, email, phone, id_number, address) VALUES (?,?,?,?,?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, g.getName());
            stmt.setString(2, g.getEmail());
            stmt.setString(3, g.getPhone());
            stmt.setString(4, g.getIdNumber());
            stmt.setString(5, g.getAddress());

            stmt.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int update(Guest g) {
        try {
            String sql = "UPDATE guests SET name=?, email=?, phone=?, id_number=?, address=? WHERE id=?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            stmt.setString(1, g.getName());
            stmt.setString(2, g.getEmail());
            stmt.setString(3, g.getPhone());
            stmt.setString(4, g.getIdNumber());
            stmt.setString(5, g.getAddress());
            stmt.setInt(6, g.getId());

            stmt.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int delete(int id) {
        try {
            String sql = "DELETE FROM guests WHERE id=?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Guest search(int id) {
        Guest g = null;

        try {
            String sql = "SELECT * FROM guests WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                g = new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("id_number"),
                        rs.getString("address")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return g;
    }

    public List<Guest> getAll() {
        List<Guest> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM guests ORDER BY name";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("id_number"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Guest> getPage(int limit, int offset) {
        List<Guest> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM guests ORDER BY name LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Guest(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("id_number"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
}