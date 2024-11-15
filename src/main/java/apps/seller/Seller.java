package apps.seller;

import apps.organizer.Event;
import apps.organizer.OrganizerDAOImpl;

import java.util.List;
import java.util.Scanner;

public class Seller {
    private int id;
    private String name;

    public Seller(String name) {
        this.name = name;
    }

    private int getId() {
        return id;
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Seller! Enter your name: ");
        String name = scanner.nextLine();
        Seller seller = new Seller(name);

        SellerDAOImpl DAOImpl = new SellerDAOImpl();
        DAOImpl.addSeller(seller);

        seller.start(DAOImpl, scanner);
        scanner.close();
    }

    public String getName() {
        return name;
    }

    public void start(SellerDAOImpl DAOImpl, Scanner scanner) {
        int option;
        // menu
        do {
            System.out.println("Choose an option and press ENTER: ");
            System.out.println("1 - Add offer");
            System.out.println("2 - View all (your) offers");
            System.out.println("3 - Update offer");
            System.out.println("4 - Delete offer");
            System.out.println("5 - Confirm order");
            System.out.println("0 - Quit");

            // Choose
            System.out.print("Choose an option and press ENTER: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You chose option 1: Add Offer");

                    // Use OrganizerDAOImpl to fetch all events for the organizer
                    OrganizerDAOImpl organizerDAO = new OrganizerDAOImpl(); // Ensure OrganizerDAOImpl is instantiated
                    List<Event> events = organizerDAO.getAllYourEvents(getId()); // Assuming getId() gets the organizer's ID

                    if (events.isEmpty()) {
                        System.out.println("No events available to add offers.");
                        break;
                    }

                    // Display events to the user
                    System.out.println("Available events:");
                    for (int i = 0; i < events.size(); i++) {
                        Event event = events.get(i);
                        System.out.println((i + 1) + ". " + event.getName() + " (ID: " + event.getId() + ")");
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
                            System.out.println("Invalid input. Please enter a valid number:");
                        }
                    }

                    // Get the selected event
                    Event selectedEvent = events.get(eventChoice - 1);

                    // Add the offer for the selected event
                    try {
                        SellerDAOImpl daoImpl = new SellerDAOImpl(); // Instantiate the correct DAO
                        daoImpl.addOffer(selectedEvent);
                        System.out.println("Offer added successfully for event: " + selectedEvent.getName());
                    } catch (Exception e) {
                        System.out.println("An error occurred while adding the offer: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.println("You choose option 2");
                    DAOImpl.displayAllOffers();
                    break;
                case 3:
                    System.out.println("You choose option 3");
                    // Dodaj logikę dla opcji 3
                    break;
                case 4:
                    System.out.println("You choose option 4");
                    // Dodaj logikę dla opcji 4
                    break;
                case 5:
                    System.out.println("You choose option 5");
                    // Dodaj logikę dla opcji 5
                    break;
                case 0:
                    System.out.println("Quitting the App. Goodbye!");
                    break;
                default:
                    System.out.println("Incorrect option. Try again.");
                    break;
            }
            System.out.println(); // Better readability

        } while (option != 0);

    }

}
