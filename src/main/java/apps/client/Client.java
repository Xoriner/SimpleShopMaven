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
        scanner.close();
        client.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int option;
        // menu
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
