import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class BugDAO {

    public static void addBug(String title, String description) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO bugs(title, description, status) VALUES (?, ?, 'Open')"
            );
            ps.setString(1, title);
            ps.setString(2, description);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void addBugWithDev(String title, String desc, String dev) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO bugs(title, description, status, assigned_to) VALUES (?, ?, 'Open', ?)"
            );
            ps.setString(1, title);
            ps.setString(2, desc);
            ps.setString(3, dev);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ResultSet getBugs() {
        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            return st.executeQuery("SELECT * FROM bugs");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static ResultSet searchBugs(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getBugs();
        }
        
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM bugs WHERE title LIKE ? OR description LIKE ? OR assigned_to LIKE ?"
            );
            ps.setString(1, "%" + keyword + "%");
            ps.setString(2, "%" + keyword + "%");
            ps.setString(3, "%" + keyword + "%");
            return ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void updateStatus(int id, String status) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "UPDATE bugs SET status=? WHERE id=?"
            );
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void deleteBug(int id) {
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(
                "DELETE FROM bugs WHERE id=?"
            );
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
