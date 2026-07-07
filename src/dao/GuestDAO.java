package dao;

import config.DBConnection;
import interfaces.CrudRepository;
import interfaces.Pageable;
import interfaces.Searchable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Guest;

public class GuestDAO implements CrudRepository<Guest, Integer>, Pageable<Guest>, Searchable<Guest> {

    private Connection connection;

    public GuestDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(GuestDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
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

    @Override
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

    @Override
    public int delete(Integer id) {
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

    public Guest search(Integer id) {
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

    public int count() {
        int total = 0;

        try {
            String sql = "SELECT COUNT(*) FROM guests";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return total;
    }

    @Override
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

    @Override
    public List<Guest> search(String keyword) {
        List<Guest> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM guests WHERE name LIKE ? OR email LIKE ? OR phone LIKE ? OR id_number LIKE ? ORDER BY name";
            PreparedStatement stmt = connection.prepareStatement(sql);

            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);
            stmt.setString(4, pattern);

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
