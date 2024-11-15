package apps.client;

import java.util.Scanner;

public class Client {
    private int id;
    private String name;

    public Client(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Enter your name: ");
        String name = scanner.nextLine();
        Client client = new Client(name);

        ClientDAOImpl DAOImpl = new ClientDAOImpl();
        DAOImpl.addClient(client);

        client.start(DAOImpl, scanner);
        scanner.close();
    }

    public void start(ClientDAOImpl DAOImpl, Scanner scanner) {
        int option;
        // menu
        do {
            System.out.println("Choose an option and press ENTER: ");
            System.out.println("1 - View all offers");
            System.out.println("2 - Make an order");
            System.out.println("3 - Check your orders");
            System.out.println("4 - Ask to confirm participation");
            System.out.println("0 - Quit");

            // Choose
            System.out.print("Choose an option and press ENTER: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You choose option 1: View all offers");
                    DAOImpl.displayAllOffers();
                    break;
                case 2:
                    System.out.println("You chose option 2: Make an order");
                    System.out.print("Enter offer ID to order: ");
                    int offerId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (DAOImpl.makeOrder(offerId)) {
                        System.out.println("Order successfully created!");
                    } else {
                        System.out.println("Failed to create the order. Please check the input and try again.");
                    }
                    break;
                case 3:
                    System.out.println("You choose option 3 - Check your orders");
                    DAOImpl.displayClientOrders(id);
                    break;
                case 4:
                    System.out.println("You choose option 4 - Confirm order");
                    System.out.println("What is the id of the order you would like to confirm?");
                    int ask_id = scanner.nextInt();
                    DAOImpl.clientRequestsOrderConfirmation(ask_id, this.id);
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


    public String getName() {
        return name;
    }
}
