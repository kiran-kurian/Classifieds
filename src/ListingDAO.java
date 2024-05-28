import java.sql.*;
import java.util.*;

public class ListingDAO {
    public List<Listing> getListings() {
        List<Listing> listings = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM listings";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Listing listing = new Listing();
                listing.setId(rs.getInt("id"));
                listing.setUserId(rs.getInt("user_id"));
                listing.setTitle(rs.getString("title"));
                listing.setDescription(rs.getString("description"));
                listings.add(listing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listings;
    }

    public boolean createListing(int userId, String title, String description) {
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO listings (user_id, title, description) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setString(2, title);
            stmt.setString(3, description);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteListing(int listingId, int userId) {
        try (Connection conn = Database.getConnection()) {
            String sql = "DELETE FROM listings WHERE id = ? AND user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, listingId);
            stmt.setInt(2, userId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateListing(int listingId, int userId, String title, String description) {
        String query = "UPDATE listings SET title = ?, description = ? WHERE id = ? AND user_id = ?";
        try (Connection con = Database.getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setInt(3, listingId);
            ps.setInt(4, userId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
