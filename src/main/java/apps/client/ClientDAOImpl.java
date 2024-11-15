package apps.client;

import apps.seller.Offer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAOImpl implements ClientDAO {
    private Connection connection = null;

    static {
        try {

            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Did not connect to database");
            System.out.println(e.getMessage());
        }
    }


    public ClientDAOImpl() {
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
    public void addClient(Client client) {
        String sql = "INSERT INTO clients (name) VALUES (?)";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, client.getName());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Offer> getAllOffers() {
        List<Offer> offerArrayList = new ArrayList<>();
        String sql = "SELECT id, name, state FROM offers";  // Correct query to select offers

        try {
            setConnection();  // Ensure the connection is established

            // Prepare the SELECT statement to retrieve offers
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                // Iterate over the result set and retrieve each offer's details
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String state = rs.getString("state");

                    // Add each offer to the list
                    offerArrayList.add(new Offer(id, name, state));
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

    public boolean makeOrder(int offerId) {
        String sql = "INSERT INTO Client_orders (status, offer_id) VALUES (?, ?)";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Pending");
            ps.setInt(2, offerId);
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void displayClientOrders(int offerId) {
        String sql = "SELECT * FROM Client_orders WHERE offer_id = ?";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, offerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Order ID: " + rs.getInt("id"));
                    System.out.println("Status: " + rs.getString("status"));
                    System.out.println("Offer ID: " + rs.getInt("offer_id"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching orders: " + e.getMessage());
        }
    }

}


