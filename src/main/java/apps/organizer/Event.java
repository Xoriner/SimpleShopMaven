package apps.organizer;

public class Event {
    private int id;
    private String name;
    private int max_clients;
    private int organizer_id;

    public Event(String name, int max_clients, int organizer_id) {
        this.name = name;
        this.max_clients = max_clients;
        this.organizer_id = organizer_id;
    }

    public Event(int id, String name, int max_clients, int organizer_id) {
        this.id = id;
        this.name = name;
        this.max_clients = max_clients;
        this.organizer_id = organizer_id;
    }

    // Override toString to display offer details
    @Override
    public String toString() {
        return "Event Id: " + id + "\n" +
                "Name: " + name + "\n" +
                "Max_clients: " + max_clients + "\n" +
                "Organizer Id: " + organizer_id + "\n";

    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getMaxClients() {
        return max_clients;
    }

    public void setMaxClients(int i) {
        this.max_clients = i;
    }
}
