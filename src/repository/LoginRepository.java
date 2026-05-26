package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DatabaseConnection;
import models.User;
import utils.PasswordUtils;

public class LoginRepository {

    public User login(String email, String password) {
       
    	String sql = "SELECT id_user, email, password, first_name, last_name, gender, date, role FROM users WHERE email = ?";

        try (
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (!PasswordUtils.checkPassword(password, hashedPassword))
                    return null;

                User user = new User();
                user.setId(rs.getInt("id_user"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setGender(rs.getString("gender"));                
                user.setDate(rs.getString("date"));
                user.setRole(rs.getString("role"));
                
                return user;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
