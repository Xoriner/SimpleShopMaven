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

    }

    @Override
    public List<Offer> getAllOffers() {
        List<Offer> offerArrayList = new ArrayList<>();
        String sql = "INSERT INTO offers (name, description, state) VALUES (?, ?, ?)";
        try {
            // Ensure the connection is established
            setConnection();

            // Use the connection to prepare the statement
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, "name");
                ps.setString(2, "description");
                ps.setString(3, "state");

                // Execute the insert statement
                int affectedRows = ps.executeUpdate();

                // Retrieve the generated ID
                if (affectedRows > 0) {
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            int generatedId = rs.getInt(1);

                            // Add the offer to the ArrayList
                            offerArrayList.add(new Offer(generatedId, "Offer Name", "Offer Description", "Offer State"));
                        }
                    }
                } else {
                    System.out.println("Insertion failed, no rows affected.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnection(); // Close the connection after the operation
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


}


