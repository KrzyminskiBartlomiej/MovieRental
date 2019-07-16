package com.rental.utils;

public interface Worker {
    void registration();
    void login();
    void rentProduct();
    void returnRentedProduct();
    void deleteProduct();
    void createNewProduct();
    String getUserRole();
    void showAllMovie();
}
