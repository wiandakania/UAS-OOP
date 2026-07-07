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
import model.Payment;

public class PaymentDAO implements CrudRepository<Payment, Integer>, Pageable<Payment>, Searchable<Payment> {

    private Connection connection;

    public PaymentDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int create(Payment p) {
        try {
            String sql = "INSERT INTO payments (reservation_id, amount, method, status) VALUES (?,?,?,?)";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            stmt.setInt(1, p.getReservationId());
            stmt.setDouble(2, p.getAmount());
            stmt.setString(3, p.getMethod());
            stmt.setString(4, p.getStatus());

            stmt.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Payment p) {
        try {
            String sql = "UPDATE payments SET reservation_id=?, amount=?, method=?, status=? WHERE id=?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            stmt.setInt(1, p.getReservationId());
            stmt.setDouble(2, p.getAmount());
            stmt.setString(3, p.getMethod());
            stmt.setString(4, p.getStatus());
            stmt.setInt(5, p.getId());

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
            String sql = "DELETE FROM payments WHERE id=?";
            PreparedStatement stmt = this.connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Payment search(Integer id) {
        Payment p = null;

        try {
            String sql = "SELECT * FROM payments WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getDouble("amount"),
                        rs.getString("method"),
                        rs.getString("status"),
                        rs.getTimestamp("payment_date").toLocalDateTime()
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return p;
    }

    public List<Payment> getAll() {
        List<Payment> list = new ArrayList<>();

        try {
            String sql =
                    "SELECT p.*, g.name AS guest_name, rm.room_number " +
                    "FROM payments p " +
                    "JOIN reservations r ON p.reservation_id=r.id " +
                    "JOIN guests g ON r.guest_id=g.id " +
                    "JOIN rooms rm ON r.room_id=rm.id " +
                    "ORDER BY p.payment_date DESC";

            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getDouble("amount"),
                        rs.getString("method"),
                        rs.getString("status"),
                        rs.getTimestamp("payment_date").toLocalDateTime()
                );

                p.setGuestName(rs.getString("guest_name"));
                p.setRoomNumber(rs.getString("room_number"));

                list.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int count() {
        int total = 0;

        try {
            String sql = "SELECT COUNT(*) FROM payments";
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
    public List<Payment> getPage(int limit, int offset) {
        List<Payment> list = new ArrayList<>();

        try {
            String sql =
                    "SELECT p.*, g.name AS guest_name, rm.room_number " +
                    "FROM payments p " +
                    "JOIN reservations r ON p.reservation_id=r.id " +
                    "JOIN guests g ON r.guest_id=g.id " +
                    "JOIN rooms rm ON r.room_id=rm.id " +
                    "ORDER BY p.payment_date DESC LIMIT ? OFFSET ?";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getDouble("amount"),
                        rs.getString("method"),
                        rs.getString("status"),
                        rs.getTimestamp("payment_date").toLocalDateTime()
                );

                p.setGuestName(rs.getString("guest_name"));
                p.setRoomNumber(rs.getString("room_number"));

                list.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Payment> search(String keyword) {
        List<Payment> list = new ArrayList<>();

        try {
            String sql =
                    "SELECT p.*, g.name AS guest_name, rm.room_number " +
                    "FROM payments p " +
                    "JOIN reservations r ON p.reservation_id=r.id " +
                    "JOIN guests g ON r.guest_id=g.id " +
                    "JOIN rooms rm ON r.room_id=rm.id " +
                    "WHERE p.method LIKE ? OR p.status LIKE ? OR g.name LIKE ? " +
                    "ORDER BY p.payment_date DESC";

            PreparedStatement stmt = connection.prepareStatement(sql);

            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("reservation_id"),
                        rs.getDouble("amount"),
                        rs.getString("method"),
                        rs.getString("status"),
                        rs.getTimestamp("payment_date").toLocalDateTime()
                );

                p.setGuestName(rs.getString("guest_name"));
                p.setRoomNumber(rs.getString("room_number"));

                list.add(p);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public double getTotalRevenue() {
        double total = 0;

        try {
            String sql = "SELECT COALESCE(SUM(amount),0) FROM payments WHERE status='PAID'";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                total = rs.getDouble(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return total;
    }
}
