package com.rental.utils;

import java.util.Scanner;

/**
 * Provides for system/user input and output.
 * This class aggregates messages for communication functions between user and app.
 *
 * @since 1.0
 * @author Piotr Nawrocki
 */
public class Communicator {
    private XmlWorker xml = new XmlWorker();

    /**
     * Returns the value provided by the user.
     * @return user input
     */
    public static String enterLoginField() {
        System.out.println("Enter login:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    /**
     * Returns the value provided by the user.
     * @return user input
     */
    public static String enterPasswordField() {
        System.out.println("Enter password:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    /**
     * Returns the value provided by the user.
     * @return user input
     */
    public static int enterAuthorizationOption() {
        System.out.println("Choose options: [1].Login [2].Registration");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    /**
     * Returns the value provided by the user.
     * @return user input
     */
    public static int enterPanelOptions() {
        System.out.println("Choose options: [1].Rent movie [2].Return movie [3].Show product [4].Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    /**
     * Returns the value provided by the user.
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
     * Special message displayed for the user.
     */
    public static void successfullyReturnProduct() {
        System.out.println("Thank you for returning this product!");
    }
    /**
     * Special message displayed for the user.
     */
    public static void rentRequirement() {
        System.out.println("Remember! You can rent only 1 product. - You need to return current product, before you would like to get new one.");
    }
    /**
     * Displays the set of information in the products.xml file.
     */
    public void getAndShowProducts() {
        for (int i = 0; i < xml.productList.getLength(); i++) {
            xml.productElement = (org.w3c.dom.Element) xml.productList.item(i);
            System.out.println("Product id : " + xml.productElement.getAttribute("product_id"));
            System.out.println("Name : " + xml.productElement.getAttribute("product_name"));
            System.out.println("Category : " + xml.productElement.getAttribute("category_name"));
            System.out.println("In stock : " + xml.productElement.getAttribute("in_stock"));
            System.out.println("---------");
        }
    }
    /**
     * Special message displayed for the user.
     */
    public static void enteredDifferentOption() {
        System.out.println("You have chosen an unavailable option.");
    }
}