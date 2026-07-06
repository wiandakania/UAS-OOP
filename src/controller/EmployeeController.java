package controller;

import dao.EmployeeDAO;
import java.time.LocalDate;
import java.util.List;
import model.Employee;

/**
 *
 * @author WIANDA
 */
public class EmployeeController {

    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public EmployeeController() {
    }

    public int create(Employee employee) {
        if (employee.getName().trim().isEmpty()
                || employee.getPosition().trim().isEmpty()
                || employee.getSalary() <= 0
                || employee.getHireDate() == null) {
            return -1;
        }
        return employeeDAO.create(employee);
    }

    public List<Employee> getAll() {
        return employeeDAO.getAll();
    }

    public int update(Employee employee, int id) {
        if (employee.getName().trim().isEmpty()
                || employee.getPosition().trim().isEmpty()
                || employee.getSalary() <= 0
                || employee.getHireDate() == null) {
            return -1;
        }
        return employeeDAO.update(employee);
    }

    public int delete(int id) {
        if (id <= 0) {
            return -1;
        }
        return employeeDAO.delete(id);
    }

    public Employee search(int id) {
        if (id <= 0) {
            return null;
        }
        return employeeDAO.search(id);
    }

    public List<Employee> getPage(int page) {
        if (page < 1) {
            page = 1;
        }
        int limit = 5;
        int offset = (page - 1) * limit;

        return employeeDAO.getPage(limit, offset);
    }
}