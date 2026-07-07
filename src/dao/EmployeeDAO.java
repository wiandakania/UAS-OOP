package dao;

import config.DBConnection;
import interfaces.CrudRepository;
import interfaces.Pageable;
import interfaces.Searchable;
import model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeDAO implements CrudRepository<Employee, Integer>, Pageable<Employee>, Searchable<Employee> {

    private Connection connection;

    public EmployeeDAO() {
        try {
            connection = DBConnection.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int create(Employee e) {
        try {
            String sql = "INSERT INTO employees (name, email, phone, position, salary, hire_date, status) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, e.getName());
            stmt.setString(2, e.getEmail());
            stmt.setString(3, e.getPhone());
            stmt.setString(4, e.getPosition());
            stmt.setDouble(5, e.getSalary());
            stmt.setDate(6, java.sql.Date.valueOf(e.getHireDate()));
            stmt.setString(7, e.getStatus());

            stmt.executeUpdate();
            return 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    @Override
    public int update(Employee e) {
        try {
            String sql = "UPDATE employees SET name=?, email=?, phone=?, position=?, salary=?, hire_date=?, status=? WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, e.getName());
            stmt.setString(2, e.getEmail());
            stmt.setString(3, e.getPhone());
            stmt.setString(4, e.getPosition());
            stmt.setDouble(5, e.getSalary());
            stmt.setDate(6, java.sql.Date.valueOf(e.getHireDate()));
            stmt.setString(7, e.getStatus());
            stmt.setInt(8, e.getId());

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
            String sql = "DELETE FROM employees WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);
            stmt.executeUpdate();

            return 1;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public Employee search(Integer id) {
        Employee employee = null;

        try {
            String sql = "SELECT * FROM employees WHERE id=?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getDate("hire_date").toLocalDate(),
                        rs.getString("status")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employee;
    }

    public List<Employee> getAll() {
        List<Employee> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM employees ORDER BY name";
            PreparedStatement stmt = connection.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getDate("hire_date").toLocalDate(),
                        rs.getString("status")
                );

                list.add(employee);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    public int count() {
        int total = 0;

        try {
            String sql = "SELECT COUNT(*) FROM employees";
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
    public List<Employee> getPage(int limit, int offset) {
        List<Employee> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM employees ORDER BY name LIMIT ? OFFSET ?";
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, limit);
            stmt.setInt(2, offset);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getDate("hire_date").toLocalDate(),
                        rs.getString("status")
                );

                list.add(employee);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Employee> search(String keyword) {
        List<Employee> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM employees WHERE name LIKE ? OR email LIKE ? OR position LIKE ? ORDER BY name";
            PreparedStatement stmt = connection.prepareStatement(sql);

            String pattern = "%" + keyword + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            stmt.setString(3, pattern);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("position"),
                        rs.getDouble("salary"),
                        rs.getDate("hire_date").toLocalDate(),
                        rs.getString("status")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
}
