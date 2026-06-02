package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Appointment;
import models.Employee;
import models.Service;

public class NewDateRepository {

    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String sql = "SELECT id_service, name, description, duration_min, price FROM service WHERE id_business = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id_service"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setDuration(rs.getInt("duration_min"));
                service.setPrice(rs.getDouble("price"));
                services.add(service);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return services;
    }

    public Service getServiceById(int id) {
        String sql = "SELECT id_service, name, description, duration_min, price FROM service WHERE id_service = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("id_service"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setDuration(rs.getInt("duration_min"));
                service.setPrice(rs.getDouble("price"));
                return service;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT id_employee, first_name, last_name, experience FROM employee WHERE id_business = 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id_employee"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setExperience(rs.getInt("experience"));
                employees.add(employee);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return employees;
    }

    public Employee getEmployeeById(int id) {
        String sql = "SELECT id_employee, first_name, last_name, experience FROM employee WHERE id_employee = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Employee employee = new Employee();
                employee.setId(rs.getInt("id_employee"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setExperience(rs.getInt("experience"));
                return employee;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean saveAppointment(int userId, int serviceId, int employeeId, int year, int month, int day, LocalTime time, int durationMin) {
        String sql = "INSERT INTO appointment (id_user, id_business, id_service, id_employee, appointment_date, start_time, end_time, status) VALUES (?, 1, ?, ?, ?, ?, ?, 'pendiente')";

        LocalDate date = LocalDate.of(year, month, day);
        LocalTime endTime = time.plusMinutes(durationMin);

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setInt(2, serviceId);
            stmt.setInt(3, employeeId);
            stmt.setDate(4, java.sql.Date.valueOf(date));
            stmt.setTime(5, java.sql.Time.valueOf(time));
            stmt.setTime(6, java.sql.Time.valueOf(endTime));

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
