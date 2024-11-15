package apps.seller;

import java.util.Scanner;

public class Seller {
    private int id;
    private String name;

    public Seller(String name) {
        this.name = name;
    }

    int getId() {
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
                    DAOImpl.addingOffers(this, scanner);
                    break;
                case 2:
                    System.out.println("You choose option 2: View offers");
                    DAOImpl.displayAllOffers();
                    break;
                case 3:
                    System.out.println("You choose option 3: Update Offer");
                    System.out.println("What is the id of the offer you would like to update?");
                    int ask_id = scanner.nextInt();
                    System.out.println("What is the name that you would like to change it to?");
                    String asked_name = scanner.nextLine();
                    System.out.println("What is the state you would to change it to?");
                    String asked_state = scanner.nextLine();
                    DAOImpl.updateOffer(new Offer(ask_id, asked_name, asked_state));
                    break;
                case 4:
                    System.out.println("You choose option 4: Delete Offer");
                    System.out.println("What is the id of the offer you would like to delete?");
                    ask_id = scanner.nextInt();
                    DAOImpl.deleteOffer(ask_id);
                    break;
                case 5:
                    System.out.println("You choose option 5: Confirm Order");
                    System.out.println("What is the id of the order you would like to confirm?");
                    ask_id = scanner.nextInt();
                    DAOImpl.confirmOrder(ask_id, this.id);
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
