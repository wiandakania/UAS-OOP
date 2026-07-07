package dao;

import config.DBConnection;
import interfaces.CrudRepository;
import interfaces.Pageable;
import interfaces.Searchable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Reservation;

public class ReservationDAO implements CrudRepository<Reservation, Integer>,
                                      Pageable<Reservation>,
                                      Searchable<Reservation> {

    private Connection connection;

    public ReservationDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(ReservationDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int create(Reservation r) {
        try {
            String sql = "INSERT INTO reservations (guest_id, room_id, check_in, check_out, total_price, status) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, r.getGuestId());
            stmt.setInt(2, r.getRoomId());
            stmt.setDate(3, java.sql.Date.valueOf(r.getCheckIn()));
            stmt.setDate(4, java.sql.Date.valueOf(r.getCheckOut()));
            stmt.setDouble(5, r.getTotalPrice());
            stmt.setString(6, r.getStatus());

            stmt.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Reservation search(int id) {
        Reservation r = null;

        try {
            String sql =
                    "SELECT r.*, g.name AS guest_name, rm.room_number, rm.room_type " +
                    "FROM reservations r " +
                    "JOIN guests g ON r.guest_id = g.id " +
                    "JOIN rooms rm ON r.room_id = rm.id " +
                    "WHERE r.id=?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                r = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("guest_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                );

                r.setGuestName(rs.getString("guest_name"));
                r.setRoomNumber(rs.getString("room_number"));
                r.setRoomType(rs.getString("room_type"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return r;
    }

    public List<Reservation> getAll() {
        List<Reservation> list = new ArrayList<>();

        try {
            String sql =
                    "SELECT r.*, g.name AS guest_name, rm.room_number, rm.room_type " +
                    "FROM reservations r " +
                    "JOIN guests g ON r.guest_id = g.id " +
                    "JOIN rooms rm ON r.room_id = rm.id " +
                    "ORDER BY r.id DESC";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("guest_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                );

                r.setGuestName(rs.getString("guest_name"));
                r.setRoomNumber(rs.getString("room_number"));
                r.setRoomType(rs.getString("room_type"));

                list.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public List<Reservation> getPage(int limit, int offset) {
        List<Reservation> list = new ArrayList<>();

        try {
            String sql =
                    "SELECT r.*, g.name AS guest_name, rm.room_number, rm.room_type " +
                    "FROM reservations r " +
                    "JOIN guests g ON r.guest_id = g.id " +
                    "JOIN rooms rm ON r.room_id = rm.id " +
                    "ORDER BY r.id DESC " +
                    "LIMIT ? OFFSET ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation r = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("guest_id"),
                        rs.getInt("room_id"),
                        rs.getDate("check_in").toLocalDate(),
                        rs.getDate("check_out").toLocalDate(),
                        rs.getDouble("total_price"),
                        rs.getString("status")
                );

                r.setGuestName(rs.getString("guest_name"));
                r.setRoomNumber(rs.getString("room_number"));
                r.setRoomType(rs.getString("room_type"));

                list.add(r);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int count() {
        int total = 0;

        try {
            String sql = "SELECT COUNT(*) FROM reservations";
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
    
    public int update(Reservation r) {
    String sql = "UPDATE reservations SET guest_id=?, room_id=?, check_in=?, check_out=?, status=? WHERE id=?";

    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, r.getGuestId());
        ps.setInt(2, r.getRoomId());
        ps.setDate(3, java.sql.Date.valueOf(r.getCheckIn()));
        ps.setDate(4, java.sql.Date.valueOf(r.getCheckOut()));
        ps.setString(5, r.getStatus());
        ps.setInt(6, r.getId());

        return ps.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}

    @Override
    public int delete(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Reservation> search(String keyword) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}