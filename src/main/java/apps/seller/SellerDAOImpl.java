package apps.seller;


import apps.organizer.Event;
import apps.organizer.OrganizerDAOImpl;
import apps.organizer.Event;
import apps.organizer.OrganizerDAOImpl;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        String sql = "SELECT id, name, state FROM offers";

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
                offer = new Offer(rs.getInt("id"), rs.getString("name"), rs.getString("state"));
            }
            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return offer;
    }

    @Override
    public void addOffer(Event event, Seller seller) {
        // SQL to insert offer linked to an event
        String sql = "INSERT INTO offers (event_id, name, state, seller_id) VALUES (?, ?, ?, ?)";
        try {
            setConnection(); // Establish database connection
            PreparedStatement ps = connection.prepareStatement(sql);

            // Set parameters for the offer
            ps.setInt(1, event.getId()); // Assuming the Event has an ID
            ps.setString(2, event.getName());
            ps.setString(3, "available");
            ps.setInt(4, seller.getId());

            // Execute the statement
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Offer added successfully for event: " + event.getName());
            } else {
                System.out.println("Failed to add the offer for event: " + event.getName());
            }

            ps.close(); // Close the prepared statement
        } catch (SQLException e) {
            System.out.println("An error occurred while adding the offer: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                closeConnection(); // Ensure connection is closed
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateOffer(Offer offer) {
        String sql = "UPDATE offers SET state = ?, name = ? WHERE id = ?";
        try {
            setConnection(); // Assuming this sets up the database connection
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, offer.getState()); // Set the state
            ps.setString(2, offer.getName());  // Set the name
            ps.setInt(3, offer.getId());       // Set the ID to identify the offer

            int rowsUpdated = ps.executeUpdate(); // Execute the update
            if (rowsUpdated > 0) {
                System.out.println("Offer with ID " + offer.getId() + " updated successfully.");
            } else {
                System.out.println("No offer found with ID " + offer.getId() + ".");
            }

            ps.close(); // Close the PreparedStatement
            closeConnection(); // Close the database connection
        } catch (SQLException e) {
            e.printStackTrace(); // Print the error for debugging
        }
    }

    @Override
    public void deleteOffer(int ask_id) {
        String sql = "DELETE FROM offers WHERE id = ?";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, ask_id);
            ps.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // functions for sellers table
    @Override
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

    public void addingOffers(Seller seller, Scanner scanner) {
        OrganizerDAOImpl organizerDAO = new OrganizerDAOImpl(); // Ensure OrganizerDAOImpl is instantiated
        List<Event> events = organizerDAO.getAllEvents();

        if (events.isEmpty()) {
            System.out.println("No events available to add offers.");
            return;
        }

        // Display events to the user
        System.out.println("Available events:");
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            System.out.println((i + 1) + ". " + event.getName() + " (ID: " + event.getId() + ", Max Clients: " + event.getMaxClients() + ")");
        }

        // Prompt user to select an event
        System.out.println("Enter the number of the event for which you want to add an offer:");
        int eventChoice;
        while (true) {
            try {
                eventChoice = Integer.parseInt(scanner.nextLine());
                if (eventChoice < 1 || eventChoice > events.size()) {
                    System.out.println("Invalid choice. Please enter a number between 1 and " + events.size() + ":");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        // Get the selected event
        Event selectedEvent = events.get(eventChoice - 1);

        // Check if the event has space available
        if (selectedEvent.getMaxClients() <= 0) {
            System.out.println("This event is full. No more offers can be added.");
            return;
        }

        // Add the offer for the selected event
        try {
            SellerDAOImpl daoImpl = new SellerDAOImpl(); // Instantiate the correct DAO
            daoImpl.addOffer(selectedEvent, seller);

            // Decrease max_clients by 1
            selectedEvent.setMaxClients(selectedEvent.getMaxClients() - 1);

            // Update max_clients in the database
            organizerDAO.updateEventMaxClients(selectedEvent);

            System.out.println("Offer added successfully. Remaining slots: " + selectedEvent.getMaxClients());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean confirmOrder(int orderId, int clientId) {
        System.out.println("Client with ID " + clientId + " is requesting to confirm order ID " + orderId);
        if (!validateClientBeforeConfirmation(orderId, clientId)) {
            return false; // Validation failed, cannot confirm the order
        }

        String updateOrderSql = "UPDATE Client_orders SET status = 'Confirmed' WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:your-database-file.db");
             PreparedStatement ps = conn.prepareStatement(updateOrderSql)) {

            // Update the order status to 'Confirmed'
            ps.setInt(1, orderId);
            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Order ID " + orderId + " has been confirmed.");
                return true;
            } else {
                System.out.println("Failed to update the order status.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public boolean validateClientBeforeConfirmation(int orderId, int clientId) {
        String checkClientSql = "SELECT COUNT(*) FROM Client_orders WHERE id = ? AND client_id = ?";
        String checkClientStatusSql = "SELECT status FROM Client_orders WHERE id = ?";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:your-database-file.db");
             PreparedStatement checkClientPs = conn.prepareStatement(checkClientSql);
             PreparedStatement checkStatusPs = conn.prepareStatement(checkClientStatusSql)) {

            // Check if the client is associated with this order
            checkClientPs.setInt(1, orderId);
            checkClientPs.setInt(2, clientId);

            try (ResultSet rs = checkClientPs.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // Check the order status to ensure it can be confirmed
                    checkStatusPs.setInt(1, orderId);

                    try (ResultSet statusRs = checkStatusPs.executeQuery()) {
                        if (statusRs.next()) {
                            String currentStatus = statusRs.getString("status");
                            if (currentStatus.equals("Pending")) {
                                // The client is valid and order status is Pending
                                return true; // Valid for confirmation
                            } else {
                                System.out.println("The order is not in a valid state for confirmation.");
                                return false; // Invalid state (already confirmed, cancelled, etc.)
                            }
                        }
                    }
                } else {
                    System.out.println("The client is not associated with this order.");
                    return false; // Client not associated with the order
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }




}
