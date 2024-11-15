package apps.seller;

import java.util.Scanner;

public class Seller {
    private int id;
    private String name;

    // Constructor without ID (for new sellers)
    public Seller(String name) {
        this.name = name;
    }

    // For starting multiple instances through intellij
    public static void main(String[] args) {
        Seller seller = new Seller("adam");
        seller.start();
    }

    public String getName() {
        return name;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        //menu
        do {
            System.out.println("Choose an option and press ENTER: ");
            System.out.println("1 - ");
            System.out.println("2 - ");
            System.out.println("3 - ");
            System.out.println("4 - ");
            System.out.println("5 - ");
            System.out.println("6 - ");

            // Choose
            System.out.print("Choose an option and press ENTER: ");
            option = scanner.nextInt();

            switch (option) {
                case 1:
                    System.out.println("You choose option 1");
                    // Dodaj logikę dla opcji 1
                    break;
                case 2:
                    System.out.println("You choose option 2");
                    // Dodaj logikę dla opcji 2
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

        scanner.close();
    }



}
