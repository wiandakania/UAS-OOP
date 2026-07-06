package model;

import java.time.LocalDate;

public class Employee extends Person {

    private String position;
    private double salary;
    private LocalDate hireDate;
    private String status;

    public Employee() {}

    public Employee(int id, String name, String email, String phone,
                    String position, double salary,
                    LocalDate hireDate, String status) {

        super(id, name, email, phone);
        this.position = position;
        this.salary = salary;
        this.hireDate = hireDate;
        this.status = status;
    }

    @Override
    public String getRole() {
        return "EMPLOYEE";
    }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }

    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}