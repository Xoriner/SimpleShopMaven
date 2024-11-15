package apps.organizer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrganizerDAOImpl {
    private Connection connection = null;

    static {
        try {

            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.out.println("Did not connect to database");
            System.out.println(e.getMessage());
        }
    }

    public OrganizerDAOImpl() {
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


    public void addEvent(String event_name, int event_max_clients, int organizer_id) {
        String sql = "INSERT INTO events (name, max_clients, organizer_id) VALUES (?, ?, ?)";
        try {
            setConnection(); // Establish the database connection
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, event_name);
            ps.setInt(2, event_max_clients);
            ps.setInt(3, organizer_id);

            // Execute the INSERT statement
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Event added successfully.");
            } else {
                System.out.println("Failed to add the event.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while adding the event: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                closeConnection(); // Close the connection after the operation
            } catch (SQLException e) {
                System.out.println("Failed to close the connection: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addOrganizer(Organizer organizer) {
        String sql = "INSERT INTO organizers (name) VALUES (?)";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, organizer.getName());
            ps.executeUpdate();
            ps.close();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Event> getAllYourEvents(int organizer_id) {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT id, name, max_clients FROM events WHERE organizer_id = ?";

        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, organizer_id);

            ResultSet rs = ps.executeQuery();
            // Iterate over the result set and retrieve each event's details
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int maxClients = rs.getInt("max_clients");

                // Add each event to the list
                events.add(new Event(id, name, maxClients, organizer_id));
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

        for(Event event : events) {
        System.out.println(event);

        }
        return events;
    }

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT id, name, max_clients, organizer_id FROM events";
        try {
            setConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();
            // Iterate over the result set and retrieve each event's details
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int maxClients = rs.getInt("max_clients");
                int organizer_id = rs.getInt("organizer_id");

                // Add each event to the list
                events.add(new Event(id, name, maxClients, organizer_id));
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


        return events;
    }

}
