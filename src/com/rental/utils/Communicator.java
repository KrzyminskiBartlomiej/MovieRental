package com.rental.utils;

import java.util.Scanner;

/**
 * Provides for system/user input and output.<p>
 * Aggregates messages for communication functions between user and app.
 *
 * @author Piotr Nawrocki
 */
public class Communicator {

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    static String enterLoginField() {
        System.out.println("Enter login:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    static String enterPasswordField() {
        System.out.println("Enter password:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    public static int enterAuthorizationOption() {
        System.out.println("Choose options: [1].Login [2].Registration");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    public static int userPanelOptions() {
        System.out.println("Choose options[USER]: [1].Rent movie [2].Return movie [3].Show product [4].Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    public static int adminPanelOptions() {
        System.out.println("Choose options[ADMIN]: [1].Show Products [2].Delete Product [3].Create Product [4].Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    static int enterProductId() {
        System.out.println("Please enter ID of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Special message displayed for the user.
     */
    static void correctDataInfo() {
        System.out.println("Correct data! Welcome to your account!");
    }

    /**
     * Displays special message and returns the value provided by the user.
     */
    static int idProductToReturn() {
        System.out.println("Please enter id of product to return:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Special message displayed for the user.
     */
    static void outOfStock() {
        System.out.println("Remember - sometimes products can be out of stock. Try next time. Bye!");
    }

    /**
     * Special message displayed for the user.
     */
    public static void successfullyRented() {
        System.out.println("Thank you for using our services!");
    }

    /**
     * Displays special messages and returns the value provided by the user.
     */
    public static void successfullyReturnProduct() {
        System.out.println("Thank you for returning this product!");
    }

    /**
     * Special message displayed for the user.
     */
    static void rentRequirement() {
        System.out.println("Remember! You can rent only " + XmlWorker.MAX_QUANTITY_OF_PRODUCTS + " products. - You need to return one of the product, before you would like to get new one.");
    }

    /**
     * Special message displayed for the user.
     */
    public static void enteredDifferentOption() {
        System.out.println("You have chosen an unavailable option.");
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    static int productToDelete() {
        System.out.println("Which product would you like to delete? Type Id of product.");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    static String enterCategory() {
        System.out.println("Type category of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Displays special message and returns the value provided by the user. Works for determine how many products we have(in stock).
     *
     * @return user input
     */
    static String enterXmlProductCount() {
        System.out.println("Type count of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static Integer enterSqlProductCount() {
        System.out.println("Type count of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    static String enterProductName() {
        System.out.println("Type name of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static Integer selectDatabase() {
        System.out.println("On which database do you want to work? [1] .XML [2] .SQL");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
