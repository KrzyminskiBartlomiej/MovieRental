package com.rental.utils;

/**
 * Contains all main functions which user can use. Every single method is available for user depends of roles.
 */
public interface Worker {
    void registration();

    /**
     * Uses user input and gets data from users.xml/mySql users table to checks if it's possible to log user in.
     */
    void login();

    /**
     * Responsible for attaching an appropriate selection to the users.xml file/mySql users table.<p>
     * Additionally, it checks and compares the user input and xml file/mysql table if the value can be added.
     */
    void rentProduct();

    /**
     * Gets a string including value of parameter from users.xml file/mySql users table.<p>
     * In the case of a positive implementation, the data is entered into the appropriate xml file/mySql table.
     */
    void returnRentedProduct();

    /**
     * Allows to delete product from products.xml file or mySql products table. After displaying all products, required to select product to delete.
     */
    void deleteProduct();

    /**
     * Allows to create new product in products.xml or mySql products table. Requires to type all necessary information of new product.
     */
    void createNewProduct();

    /**
     * Gets information from users.xml file or mySql users table about what role logged user have.
     *
     * @return role of user
     */
    String getUserRole();

    /**
     * Displays all available products in database to rent.
     */
    void showAllMovie();
}
