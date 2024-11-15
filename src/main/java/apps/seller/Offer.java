package apps.seller;

public class Offer {
    private int id;
    private String name;
    private String description;
    private String state;

    // Constructor without ID (for new offers)
    public Offer(String name, String description, String state) {
        this.name = name;
        this.description = description;
        this.state = state;
    }

    // Constructor with ID (for existing offers)
    public Offer(int id, String name, String description, String state) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
    }


    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }
}
