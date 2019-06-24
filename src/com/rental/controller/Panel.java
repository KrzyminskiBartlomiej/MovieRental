package com.rental.controller;

import com.rental.utils.Communicator;

public interface Panel {
    void runPanel();

    void deleteMovie();

    void createProduct();

    default void showAllMovie() {
        Communicator.getAndShowProducts();
    }
}
