package apps.seller;

public class Offer {
    private int id;
    private String name;
    private String state;

    // Constructor without ID (for new offers)
    public Offer(String name, String state) {
        this.name = name;
        this.state = state;
    }

    // Constructor with ID (for existing offers)
    public Offer(int id, String name, String state) {
        this.id = id;
        this.name = name;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    // Override toString to display offer details
    @Override
    public String toString() {
        return "Offer ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "State: " + state + "\n";

    }
}

