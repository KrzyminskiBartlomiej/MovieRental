package com.rental.utils;

import com.rental.controller.RentalProcessor;

import java.sql.*;

public class SqlWorker implements Worker {
    private static final String SQL_PRODUCT_ID = "product_id";
    private static final String SQL_PRODUCT_CATEGORY = "category_name";
    private static final String SQL_PRODUCT_COUNT = "in_stock";
    private static final String SQL_PRODUCT_REVIEWS = "user_reviews";
    private static final String SQL_PRODUCT_NAME = "product_name";
    private static final String SQL_USER_NAME = "user_name";
    private static final String SQL_USER_PASSWORD = "password";
    private static final String SQL_USER_ROLE = "user_role";
    private static final String SQL_NUMBER_OF_RENTALS = "number_of_rentals";
    private static final int SQL_MAX_QUANTITY_OF_PRODUCTS = 3;
    private static final String CREATE_PRODUCT_QUERY = "insert into products(product_name, category_name, in_stock, user_reviews) values(?, ?, ?, ?)";
    private static final String CREATE_USER_QUERY = "insert into users(user_name, password, user_role, rented_products, number_of_rentals) values(?, ?, ?, ?, ?)";
    private static final String RENT_PRODUCT_QUERY = "update users set rented_products=concat(ifnull(rented_products, ' '), ?) WHERE user_name = ?";
    private static final String DELETE_PRODUCT_QUERY = "delete from products where product_id = ?";
    private static final String PRODUCTS_QUERY = "SELECT * FROM products";
    private static final String USERS_QUERY = "SELECT * FROM users";
    private static final String PRODUCTS_NAME_QUERY = "SELECT product_name, product_id FROM products";
    private static final String PRODUCT_STOCK_QUERY = "update products set in_stock=in_stock+1 where product_name = ?";
    private static final String USERS_STOCK_QUERY = "update users set number_of_rentals=number_of_rentals-1 where user_name = ?";
    private static final String ADD_STOCK_QUERY = "update users set number_of_rentals=number_of_rentals+1 where user_name = ?";
    private static final String RETURN_PRODUCT_QUERY = "update users set rented_products = replace (rented_products, ?, '') WHERE user_name = ?";
    private static final String QUANTITY_OD_PRODUCTS_QUERY = "select number_of_rentals, user_name from users";
    private PreparedStatement addProductStatement;
    private PreparedStatement createUserStatement;
    private PreparedStatement deleteProductStatement;
    private PreparedStatement addProductToStockStatement;
    private PreparedStatement rentProductStatement;
    private PreparedStatement deleteProductNameStatement;
    private PreparedStatement productStockStatement;
    private PreparedStatement userStockStatement;
    private ResultSet checkQuantityThresholdRs;
    private ResultSet usersRoleRs;
    private ResultSet rentRS;
    private ResultSet productsNameRs;
    private ResultSet usersRs;

    SqlWorker() {
        try {
            DatabaseConnector connector = new DatabaseConnector(); //dependency injection
            addProductStatement = connector.connect.prepareStatement(CREATE_PRODUCT_QUERY);
            rentProductStatement = connector.connect.prepareStatement(RENT_PRODUCT_QUERY);
            createUserStatement = connector.connect.prepareStatement(CREATE_USER_QUERY);
            deleteProductStatement = connector.connect.prepareStatement(DELETE_PRODUCT_QUERY);
            addProductToStockStatement = connector.connect.prepareStatement(ADD_STOCK_QUERY);
            Statement statementProductName = connector.connect.createStatement();
            Statement statementUserRole = connector.connect.createStatement();
            Statement statementThreshold = connector.connect.createStatement();
            Statement statement = connector.connect.createStatement();
            Statement statementRent = connector.connect.createStatement();
            rentRS = statementRent.executeQuery(PRODUCTS_QUERY);
            productsNameRs = statementProductName.executeQuery(PRODUCTS_NAME_QUERY);
            usersRs = statement.executeQuery(USERS_QUERY);
            usersRoleRs = statementUserRole.executeQuery(USERS_QUERY);
            productStockStatement = connector.connect.prepareStatement(PRODUCT_STOCK_QUERY);
            userStockStatement = connector.connect.prepareStatement(USERS_STOCK_QUERY);
            deleteProductNameStatement = connector.connect.prepareStatement(RETURN_PRODUCT_QUERY);
            checkQuantityThresholdRs = statementThreshold.executeQuery(QUANTITY_OD_PRODUCTS_QUERY);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void createNewProduct() {
        try {
            addProductStatement.setString(1, Communicator.enterProductName());
            addProductStatement.setString(2, Communicator.enterCategory());
            addProductStatement.setInt(3, Communicator.enterSqlProductCount());
            addProductStatement.setInt(4, 0);
            addProductStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void showAllMovie() {
        try {
            while (rentRS.next()) {
                int productId = rentRS.getInt(SQL_PRODUCT_ID);
                String productName = rentRS.getString(SQL_PRODUCT_NAME);
                String productCategory = rentRS.getString(SQL_PRODUCT_CATEGORY);
                String productCount = rentRS.getString(SQL_PRODUCT_COUNT);
                String productReviews = rentRS.getString(SQL_PRODUCT_REVIEWS);
                System.out.println(productId + " " + productName + " " + productCategory + " " + productCount + " " + productReviews);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteProduct() {
        try {
            deleteProductStatement.setInt(1, Communicator.productToDelete());
            deleteProductStatement.executeUpdate();
            deleteProductStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    private void checkRentedQuantity(String username) {
        try {
            while (checkQuantityThresholdRs.next()) {
                if (checkQuantityThresholdRs.getString(SQL_USER_NAME).equals(username)) {
                    if (checkQuantityThresholdRs.getInt(SQL_NUMBER_OF_RENTALS) >= SQL_MAX_QUANTITY_OF_PRODUCTS) {
                        Communicator.rentRequirement();
                        System.exit(0);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private String nameOfLoggedUser;

    public void login() {
        boolean temp = true;
        try {
            while (temp) {
                String enterLogin = Communicator.enterLoginField();
                String enterPassword = Communicator.enterPasswordField();
                while (usersRs.next()) {
                    if (usersRs.getString(SQL_USER_NAME).equals(enterLogin) && usersRs.getString(SQL_USER_PASSWORD).equals(enterPassword)) {
                        Communicator.correctDataInfo();
                        this.nameOfLoggedUser = enterLogin;
                        temp = false;
                        break;
                    }
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void registration() {
        try {
            createUserStatement.setString(1, Communicator.enterLoginField());
            createUserStatement.setString(2, Communicator.enterPasswordField());
            createUserStatement.setString(3, RentalProcessor.USER_ROLE);
            createUserStatement.setString(4, "");
            createUserStatement.setInt(5, 0);
            createUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void rentProduct() {
        try {
            checkRentedQuantity(nameOfLoggedUser);
            int productId = Communicator.enterProductId();
            while (productsNameRs.next()) {
                if (productsNameRs.getInt(SQL_PRODUCT_ID) == productId) {
                    rentProductStatement.setString(1, productsNameRs.getString(SQL_PRODUCT_NAME));
                    rentProductStatement.setString(2, nameOfLoggedUser);
                    rentProductStatement.executeUpdate();
                    break;
                }
            }
            addProductToStockStatement.setString(1, nameOfLoggedUser);
            addProductToStockStatement.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void returnProductStock(String productName) {
        try {
            productStockStatement.setString(1, productName);
            productStockStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void subtractUserStock() {
        try {
            userStockStatement.setString(1, nameOfLoggedUser);
            userStockStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void returnRentedProduct() {
        String nameOfProduct = Communicator.productToReturn();
        returnProductStock(nameOfProduct);
        subtractUserStock();
        try {
            deleteProductNameStatement.setString(1, nameOfProduct);
            deleteProductNameStatement.setString(2, nameOfLoggedUser);
            deleteProductNameStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public String getUserRole() {
        StringBuilder buffer = new StringBuilder();
        try {
            while (usersRoleRs.next()) {
                if (usersRoleRs.getString(SQL_USER_NAME).equals(nameOfLoggedUser)) {
                    buffer.append(usersRoleRs.getString(SQL_USER_ROLE));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return buffer.toString();
    }
}
