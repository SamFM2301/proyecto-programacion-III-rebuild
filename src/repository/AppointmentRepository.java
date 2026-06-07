package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Appointment;
import models.AppointmentDetail;

public class AppointmentRepository {

    public List<Appointment> getAppointmentsByUserId(int userId) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.id_appointment, a.id_user, a.id_business, a.id_service, a.id_employee, " +
                     "a.appointment_date, a.start_time, a.end_time, a.status, a.notes " +
                     "FROM appointment a WHERE a.id_user = ? ORDER BY a.appointment_date DESC, a.start_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id_appointment"));
                appointment.setIdUser(rs.getInt("id_user"));
                appointment.setIdBusiness(rs.getInt("id_business"));
                appointment.setIdService(rs.getInt("id_service"));
                appointment.setIdEmployee(rs.getInt("id_employee"));
                appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                appointment.setStartTime(rs.getTime("start_time").toLocalTime());
                appointment.setEndTime(rs.getTime("end_time").toLocalTime());
                appointment.setStatus(rs.getString("status"));
                appointment.setNotes(rs.getString("notes"));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.id_appointment, a.id_user, a.id_business, a.id_service, a.id_employee, " +
                     "a.appointment_date, a.start_time, a.end_time, a.status, a.notes " +
                     "FROM appointment a WHERE a.status != 'cancelada' ORDER BY a.appointment_date DESC, a.start_time DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id_appointment"));
                appointment.setIdUser(rs.getInt("id_user"));
                appointment.setIdBusiness(rs.getInt("id_business"));
                appointment.setIdService(rs.getInt("id_service"));
                appointment.setIdEmployee(rs.getInt("id_employee"));
                appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                appointment.setStartTime(rs.getTime("start_time").toLocalTime());
                appointment.setEndTime(rs.getTime("end_time").toLocalTime());
                appointment.setStatus(rs.getString("status"));
                appointment.setNotes(rs.getString("notes"));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }

    public Appointment getAppointmentById(int id) {
        String sql = "SELECT id_appointment, id_user, id_business, id_service, id_employee, " +
                     "appointment_date, start_time, end_time, status, notes " +
                     "FROM appointment WHERE id_appointment = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id_appointment"));
                appointment.setIdUser(rs.getInt("id_user"));
                appointment.setIdBusiness(rs.getInt("id_business"));
                appointment.setIdService(rs.getInt("id_service"));
                appointment.setIdEmployee(rs.getInt("id_employee"));
                appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                appointment.setStartTime(rs.getTime("start_time").toLocalTime());
                appointment.setEndTime(rs.getTime("end_time").toLocalTime());
                appointment.setStatus(rs.getString("status"));
                appointment.setNotes(rs.getString("notes"));
                return appointment;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean updateAppointmentStatus(int id, String status) {
        String sql = "UPDATE appointment SET status = ? WHERE id_appointment = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean cancelAppointment(int id) {
        return updateAppointmentStatus(id, "cancelada");
    }

    public boolean deleteAppointment(int id) {
        String sql = "DELETE FROM appointment WHERE id_appointment = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointmentsByEmployeeAndDate(int employeeId, LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT id_appointment, id_user, id_business, id_service, id_employee, " +
                     "appointment_date, start_time, end_time, status, notes " +
                     "FROM appointment WHERE id_employee = ? AND appointment_date = ? " +
                     "AND status != 'cancelada' ORDER BY start_time";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id_appointment"));
                appointment.setIdUser(rs.getInt("id_user"));
                appointment.setIdBusiness(rs.getInt("id_business"));
                appointment.setIdService(rs.getInt("id_service"));
                appointment.setIdEmployee(rs.getInt("id_employee"));
                appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                appointment.setStartTime(rs.getTime("start_time").toLocalTime());
                appointment.setEndTime(rs.getTime("end_time").toLocalTime());
                appointment.setStatus(rs.getString("status"));
                appointment.setNotes(rs.getString("notes"));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }

    public boolean hasConflict(int employeeId, String date, String startTime, String endTime) {
        String sql = "SELECT COUNT(*) FROM appointment WHERE id_employee = ? AND appointment_date = ? " +
                     "AND status != 'cancelada' AND " +
                     "((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?))";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, employeeId);
            stmt.setString(2, date);
            stmt.setString(3, startTime);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.setString(6, endTime);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public List<AppointmentDetail> getDetailedAppointmentsByUserAndDate(int userId, LocalDate date) {
        List<AppointmentDetail> appointments = new ArrayList<>();
        String sql = "SELECT " +
                     "  a.id_appointment, " +
                     "  a.appointment_date, " +
                     "  a.start_time, " +
                     "  a.end_time, " +
                     "  a.status, " +
                     "  a.notes, " +
                     "  s.name       AS service_name, " +
                     "  s.price      AS service_price, " +
                     "  e.first_name AS employee_first, " +
                     "  e.last_name  AS employee_last " +
                     "FROM appointment a " +
                     "JOIN service  s ON a.id_service  = s.id_service " +
                     "LEFT JOIN employee e ON a.id_employee = e.id_employee " +
                     "WHERE a.id_user = ? AND a.appointment_date = ? " +
                     "ORDER BY a.start_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AppointmentDetail detail = new AppointmentDetail();
                detail.setId(rs.getInt("id_appointment"));
                detail.setDate(rs.getDate("appointment_date").toLocalDate());
                detail.setStartTime(rs.getTime("start_time").toLocalTime());
                detail.setEndTime(rs.getTime("end_time").toLocalTime());
                detail.setStatus(rs.getString("status"));
                detail.setNotes(rs.getString("notes"));
                detail.setServiceName(rs.getString("service_name"));
                detail.setServicePrice(rs.getDouble("service_price"));

                String firstName = rs.getString("employee_first");
                String lastName = rs.getString("employee_last");
                if (firstName != null && lastName != null) {
                    detail.setEmployeeFullName(firstName + " " + lastName);
                } else {
                    detail.setEmployeeFullName("Sin asignar");
                }

                appointments.add(detail);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }

    public List<Appointment> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.id_appointment, a.id_user, a.id_business, a.id_service, a.id_employee, " +
                     "a.appointment_date, a.start_time, a.end_time, a.status, a.notes " +
                     "FROM appointment a WHERE a.appointment_date = ? ORDER BY a.start_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setId(rs.getInt("id_appointment"));
                appointment.setIdUser(rs.getInt("id_user"));
                appointment.setIdBusiness(rs.getInt("id_business"));
                appointment.setIdService(rs.getInt("id_service"));
                appointment.setIdEmployee(rs.getInt("id_employee"));
                appointment.setAppointmentDate(rs.getDate("appointment_date").toLocalDate());
                appointment.setStartTime(rs.getTime("start_time").toLocalTime());
                appointment.setEndTime(rs.getTime("end_time").toLocalTime());
                appointment.setStatus(rs.getString("status"));
                appointment.setNotes(rs.getString("notes"));
                appointments.add(appointment);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }

    public List<AppointmentDetail> getDetailedAppointmentsByDate(LocalDate date) {
        List<AppointmentDetail> appointments = new ArrayList<>();
        String sql = "SELECT " +
                     "  a.id_appointment, " +
                     "  a.appointment_date, " +
                     "  a.start_time, " +
                     "  a.end_time, " +
                     "  a.status, " +
                     "  a.notes, " +
                     "  s.name       AS service_name, " +
                     "  s.price      AS service_price, " +
                     "  e.first_name AS employee_first, " +
                     "  e.last_name  AS employee_last, " +
                     "  u.first_name AS client_first, " +
                     "  u.last_name  AS client_last " +
                     "FROM appointment a " +
                     "JOIN service  s ON a.id_service  = s.id_service " +
                     "LEFT JOIN employee e ON a.id_employee = e.id_employee " +
                     "JOIN users u ON a.id_user = u.id_user " +
                     "WHERE a.appointment_date = ? " +
                     "ORDER BY a.start_time ASC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                AppointmentDetail detail = new AppointmentDetail();
                detail.setId(rs.getInt("id_appointment"));
                detail.setDate(rs.getDate("appointment_date").toLocalDate());
                detail.setStartTime(rs.getTime("start_time").toLocalTime());
                detail.setEndTime(rs.getTime("end_time").toLocalTime());
                detail.setStatus(rs.getString("status"));
                detail.setNotes(rs.getString("notes"));
                detail.setServiceName(rs.getString("service_name"));
                detail.setServicePrice(rs.getDouble("service_price"));

                String empFirst = rs.getString("employee_first");
                String empLast = rs.getString("employee_last");
                if (empFirst != null && empLast != null) {
                    detail.setEmployeeFullName(empFirst + " " + empLast);
                } else {
                    detail.setEmployeeFullName("Sin asignar");
                }

                String clientFirst = rs.getString("client_first");
                String clientLast = rs.getString("client_last");
                if (clientFirst != null && clientLast != null) {
                    detail.setClientFullName(clientFirst + " " + clientLast);
                } else {
                    detail.setClientFullName("Cliente desconocido");
                }

                appointments.add(detail);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return appointments;
    }
}