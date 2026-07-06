package dao;

import config.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Room;

public class RoomDAO {

    private Connection connection;

    public RoomDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(RoomDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int create(Room r) {
        try {
            String sql = "INSERT INTO rooms (room_number, room_type, price_per_night, status, capacity, description) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, r.getRoomNumber());
            stmt.setString(2, r.getRoomType());
            stmt.setDouble(3, r.getPricePerNight());
            stmt.setString(4, r.getStatus());
            stmt.setInt(5, r.getCapacity());
            stmt.setString(6, r.getDescription());

            stmt.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int update(Room r) {
        try {
            String sql = "UPDATE rooms SET room_number=?, room_type=?, price_per_night=?, status=?, capacity=?, description=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, r.getRoomNumber());
            stmt.setString(2, r.getRoomType());
            stmt.setDouble(3, r.getPricePerNight());
            stmt.setString(4, r.getStatus());
            stmt.setInt(5, r.getCapacity());
            stmt.setString(6, r.getDescription());
            stmt.setInt(7, r.getId());

            stmt.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public int delete(int id) {
        try {
            String sql = "DELETE FROM rooms WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Room search(int id) {
        Room r = null;

        try {
            String sql = "SELECT * FROM rooms WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                r = new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status"),
                        rs.getInt("capacity"),
                        rs.getString("description")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }

    public List<Room> getAll() {
        List<Room> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM rooms ORDER BY room_number";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status"),
                        rs.getInt("capacity"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Room> getPage(int limit, int offset) {
        List<Room> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM rooms ORDER BY room_number LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status"),
                        rs.getInt("capacity"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Room> getAvailable() {
        List<Room> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM rooms WHERE status='AVAILABLE' ORDER BY room_number";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Room(
                        rs.getInt("id"),
                        rs.getString("room_number"),
                        rs.getString("room_type"),
                        rs.getDouble("price_per_night"),
                        rs.getString("status"),
                        rs.getInt("capacity"),
                        rs.getString("description")
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
            String sql = "SELECT COUNT(*) FROM rooms";
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
}