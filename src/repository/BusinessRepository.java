package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.math.BigDecimal;

import config.DatabaseConnection;
import models.Business;

public class BusinessRepository {

    public Business getBusiness() {
        return getBusinessById(1);
    }

    public Business getBusinessById(int id) {
        String sql = "SELECT id_business, name, address, phone, opening_time, close_time, qualification " +
                     "FROM business WHERE id_business = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Business business = new Business();
                business.setId(rs.getInt("id_business"));
                business.setName(rs.getString("name"));
                business.setAddress(rs.getString("address"));
                business.setPhone(rs.getString("phone"));
                business.setOpeningTime(rs.getString("opening_time"));
                business.setCloseTime(rs.getString("close_time"));
                business.setQualification(rs.getBigDecimal("qualification"));
                return business;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public boolean updateBusiness(Business business) {
        String sql = "UPDATE business SET name = ?, address = ?, phone = ?, opening_time = ?, close_time = ? WHERE id_business = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, business.getName());
            stmt.setString(2, business.getAddress());
            stmt.setString(3, business.getPhone());
            stmt.setString(4, business.getOpeningTime());
            stmt.setString(5, business.getCloseTime());
            stmt.setInt(6, business.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateQualification(int id, BigDecimal qualification) {
        String sql = "UPDATE business SET qualification = ? WHERE id_business = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBigDecimal(1, qualification);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
}