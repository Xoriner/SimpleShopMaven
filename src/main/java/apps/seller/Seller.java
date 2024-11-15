package apps.seller;

import apps.DAOImpl;
import apps.client.Client;
import apps.client.ClientDAOImpl;

import java.util.Scanner;

public class Seller {
    private int id;
    private String name;

    public Seller(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome! Enter your name: ");
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
            System.out.println("1 - View all offers");
            System.out.println("2 - Add offer");
            System.out.println("3 - ");
            System.out.println("4 - ");
            System.out.println("5 - ");
            System.out.println("6 - Quit");

            // Choose
            System.out.print("Choose an option and press ENTER: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You choose option 1");
                    DAOImpl.displayAllOffers();
                    break;
                case 2:
                    System.out.println("You choose option 2");
                    DAOImpl.addOffer(new Offer("Balon", "10pm", "available"));
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
                    System.out.println("Quitting the App. Goodbye!");
                    break;
                default:
                    System.out.println("Incorrect option. Try again.");
                    break;
            }
            System.out.println(); // Better readability

        } while (option != 6);

    }



}
