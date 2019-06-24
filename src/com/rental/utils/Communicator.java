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
    public static String enterLoginField() {
        System.out.println("Enter login:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    public static String enterPasswordField() {
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
        System.out.println("Choose options: [1].Rent movie [2].Return movie [3].Show product [4].Delete Movie [5].Create Product [6].Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    public static int adminPanelOptions() {
        System.out.println("Choose options: [1].Show Products [2].Delete Product [3].Create Product [4].Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Displays special message and returns the value provided by the user.
     *
     * @return user input
     */
    public static int enterProductId() {
        System.out.println("Please enter id of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Special message displayed for the user.
     */
    public static void correctDataInfo() {
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

    public static boolean successfullyAction() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you sure? Type yes or no.");
        if (!scanner.nextLine().equals("yes")) {
            System.out.println("System logout. Probably roleOfLoggedUser goes wrong.");
            System.exit(0);
            return false;
        }
        System.out.println("It went well");
        return true;
    }

    /**
     * Special message displayed for the user.
     */
    static void rentRequirement() {
        System.out.println("Remember! You can rent only " + XmlWorker.MAX_QUANTITY_OF_PRODUCTS + " products. - You need to return one of the product, before you would like to get new one.");
    }

    /**
     * Displays the set of information in the products.xml file.
     */
    static public void getAndShowProducts() {
        for (int i = 0; i < XmlWorker.productList.getLength(); i++) {
            XmlWorker.productElement = (org.w3c.dom.Element) XmlWorker.productList.item(i);
            System.out.println("Product id : " + XmlWorker.productElement.getAttribute("product_id"));
            System.out.println("Name : " + XmlWorker.productElement.getAttribute("product_name"));
            System.out.println("Category : " + XmlWorker.productElement.getAttribute("category_name"));
            System.out.println("In stock : " + XmlWorker.productElement.getAttribute("in_stock"));
            System.out.println("---------");
        }
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
     * Special message displayed for the user.
     */
    public static void userNotHavePermission() {
        System.out.println("You do not have permission to perform this action");
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
    static String enterProductCount() {
        System.out.println("Type count of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
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
}
