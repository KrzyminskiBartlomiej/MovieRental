package com.rental.controller;

import com.rental.utils.Communicator;

/**
 * Contains set of method for logged in users. Access to this actions, depend of user roles.
 */
public interface Panel {
    /**
     * Contains set of different methods for logged users with different roles.
     */
    void runPanel();

    /**
     * Allows to delete selected product from products.xml file.Available only for users with suitable role.
     */
    void deleteMovie();

    /**
     * Allows to create product and to place in product.xml file. Available only for users with suitable role.
     */
    void createProduct();

    /**
     * Gets all child nodes of products from products.xml file and displays for users.
     */
    default void showAllMovie() {
        Communicator.getAndShowProducts();
    }
}
