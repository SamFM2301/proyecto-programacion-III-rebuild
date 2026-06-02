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
}