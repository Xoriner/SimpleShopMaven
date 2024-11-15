package apps.seller;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOImpl implements DAO {
    private Connection connection = null;

    static {
        try {

            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Did not connect to database");
            System.out.println(e.getMessage());
        }
    }


    public DAOImpl() {
    }

    private void setConnection() throws SQLException {
        if (connection != null) {
            return;
        }
        String url = "jdbc:sqlite:database";
        connection = DriverManager.getConnection(url);
    }

    private void closeConnection() throws SQLException {
        if (connection == null) {
            return;
        }
        connection.close();
        connection = null;
    }


    @Override
    public List<Offer> getAllOffers() {
        List<Offer> offerArrayList = new ArrayList<>();
        String sql = "INSERT INTO offers (name, description, state) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, "name");
            pstmt.setString(2, "description");
            pstmt.setString(3, "state");

            // Execute the insert statement
            int affectedRows = pstmt.executeUpdate();

            // Retrieve the generated ID
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int generatedId = rs.getInt(1);

                        // Add the offer to the ArrayList
                        offerArrayList.add(new Offer(generatedId, "Offer Name", "Offer Description", "Offer State"));
                    }
                }
            } else {
                System.out.println("Insertion failed, no rows affected.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return offerArrayList;
    }

    @Override
    public Offer getOffer(int id) {
        String sql = "SELECT * FROM offers WHERE id = ?";
        Offer offer = null;
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery(sql);
            if (rs.next()) {
                offer = new Offer(rs.getInt("id"), rs.getString("name"),
                        rs.getString("description"), rs.getString("state"));
            }
            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }

    @Override
    public void addOffer(Offer offer) {
        String sql = "INSERT INTO offers (name, description, state) VALUES (?,?,?)";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, offer.getName());
            ps.setString(2, offer.getDescription());
            ps.setString(3, offer.getState());
            ps.executeUpdate();

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateOffer(Offer offer) {
        String sql = "UPDATE offers SET state = ?" + "WHERE id = ?";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, offer.getState());
            ps.setInt(2, offer.getId());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteOffer(Offer offer) {
        String sql = "DELETE FROM offers WHERE id = ?";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, offer.getId());
            ps.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
