package apps.seller;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDAOImpl implements SellerDAO {
    private Connection connection = null;

    static {
        try {

            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Did not connect to database");
            System.out.println(e.getMessage());
        }
    }


    public SellerDAOImpl() {
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



    //functions for offers table

    @Override
    public List<Offer> getAllOffers() {
        List<Offer> offerArrayList = new ArrayList<>();
        String sql = "SELECT id, name, description, state FROM offers";  // Correct query to select offers

        try {
            setConnection();  // Ensure the connection is established

            // Prepare the SELECT statement to retrieve offers
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                // Iterate over the result set and retrieve each offer's details
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String description = rs.getString("description");
                    String state = rs.getString("state");

                    // Add each offer to the list
                    offerArrayList.add(new Offer(id, name, description, state));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection();  // Close connection after the operation
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return offerArrayList;
    }

    @Override
    public void displayAllOffers() {
        List<Offer> offers = getAllOffers();

        if (offers.isEmpty()) {
            System.out.println("No offers available.");
        } else {
            System.out.println("Available Offers:");
            for (Offer offer : offers) {
                System.out.println(offer); // This will call the toString() method of Offer
            }
        }
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

    // functions for sellers table
    public void addSeller(Seller seller) {
        String sql = "INSERT INTO sellers (name) VALUES (?)";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, seller.getName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}