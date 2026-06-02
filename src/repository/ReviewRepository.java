package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.DatabaseConnection;
import models.Review;

public class ReviewRepository {

    public List<Review> getReviewsByBusinessId(int businessId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.id_review, r.id_appointment, r.id_user, r.id_business, r.rating, r.comment, r.created_at " +
                     "FROM review r WHERE r.id_business = ? ORDER BY r.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, businessId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id_review"));
                review.setIdAppointment(rs.getInt("id_appointment"));
                review.setIdUser(rs.getInt("id_user"));
                review.setIdBusiness(rs.getInt("id_business"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                reviews.add(review);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reviews;
    }

    public List<Review> getReviewsByUserId(int userId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT r.id_review, r.id_appointment, r.id_user, r.id_business, r.rating, r.comment, r.created_at " +
                     "FROM review r WHERE r.id_user = ? ORDER BY r.created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review();
                review.setId(rs.getInt("id_review"));
                review.setIdAppointment(rs.getInt("id_appointment"));
                review.setIdUser(rs.getInt("id_user"));
                review.setIdBusiness(rs.getInt("id_business"));
                review.setRating(rs.getInt("rating"));
                review.setComment(rs.getString("comment"));
                reviews.add(review);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return reviews;
    }

    public boolean saveReview(Review review) {
        String sql = "INSERT INTO review (id_appointment, id_user, id_business, rating, comment) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, review.getIdAppointment());
            stmt.setInt(2, review.getIdUser());
            stmt.setInt(3, review.getIdBusiness());
            stmt.setInt(4, review.getRating());
            stmt.setString(5, review.getComment());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public double getAverageRatingByBusinessId(int businessId) {
        String sql = "SELECT AVG(rating) as avg_rating FROM review WHERE id_business = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, businessId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return 0.0;
    }
}