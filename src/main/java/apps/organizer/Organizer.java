package apps.organizer;

import java.util.Scanner;

public class Organizer {
    private int id;
    private String name;

    public Organizer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome Organizer! Enter your name: ");
        String name = scanner.nextLine();
        Organizer organizer = new Organizer(name);

        OrganizerDAOImpl DAOImpl = new OrganizerDAOImpl();
        DAOImpl.addOrganizer(organizer);

        organizer.start(DAOImpl, scanner);
        scanner.close();
    }

    public void start(OrganizerDAOImpl DAOImpl, Scanner scanner) {
        int option;
        // menu
        do {
            System.out.println("Choose an option and press ENTER: ");
            System.out.println("1 - Add event");
            System.out.println("2 - View all your events");
            System.out.println("3 - View all your orders");
            System.out.println("4 - Update given event");
            System.out.println("5 - Update given order");
            System.out.println("6 - Delete given event");
            System.out.println("7 - Delete given order");
            System.out.println("0 - Quit");

            // Choose
            System.out.print("Choose an option and press ENTER: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You choose option 1");
                    System.out.println("What is the event?");
                    String name = scanner.nextLine();
                    System.out.println("What is the maximum number of participants?");
                    int max_clients = scanner.nextInt();
                    DAOImpl.addEvent(name, max_clients, getId());
                    break;
                case 2:
                    System.out.println("You choose option 2");
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
                case 6:
                    System.out.println("You choose option 6");
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
