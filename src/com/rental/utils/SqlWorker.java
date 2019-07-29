package com.rental.utils;

import com.rental.controller.RentalProcessor;

import java.sql.*;

/**
 * Uses mySql database.<p>
 * Contains all data and parameters to provide full functionality for user.
 *
 * @author Piotr Nawrocki
 */
public class SqlWorker implements Worker {
    private static final String SQL_PRODUCT_ID = "product_id";
    private static final String SQL_PRODUCT_CATEGORY = "category_name";
    private static final String SQL_PRODUCT_COUNT = "in_stock";
    private static final String SQL_PRODUCT_REVIEWS = "user_reviews";
    private static final String SQL_PRODUCT_NAME = "product_name";
    private static final String SQL_USER_NAME = "user_name";
    private static final String SQL_USER_PASSWORD = "password";
    private static final String SQL_USER_ROLE = "user_role";
    private static final String SQL_RENTED_PRODUCTS = "rented_products";
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
    private static final String RETURN_PRODUCT_QUERY = "update users set rented_products = replace (rented_products, ?, ' ') WHERE user_name = ?";
    private static final String QUANTITY_OD_PRODUCTS_QUERY = "select number_of_rentals, user_name from users";
    private DatabaseConnector databaseConnector = new DatabaseConnector();

    public void createNewProduct() {
        try {
            PreparedStatement addProductToStockStatement = databaseConnector.connect.prepareStatement(CREATE_PRODUCT_QUERY);
            addProductToStockStatement.setString(1, Communicator.enterProductName());
            addProductToStockStatement.setString(2, Communicator.enterCategory());
            addProductToStockStatement.setInt(3, Communicator.enterSqlProductCount());
            addProductToStockStatement.setInt(4, 0);
            addProductToStockStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void showAllMovie() {
        try {
            Statement statementShowProducts = databaseConnector.connect.createStatement();
            ResultSet showProductsRS = statementShowProducts.executeQuery(PRODUCTS_QUERY);
            while (showProductsRS.next()) {
                int productId = showProductsRS.getInt(SQL_PRODUCT_ID);
                String productName = showProductsRS.getString(SQL_PRODUCT_NAME);
                String productCategory = showProductsRS.getString(SQL_PRODUCT_CATEGORY);
                String productCount = showProductsRS.getString(SQL_PRODUCT_COUNT);
                String productReviews = showProductsRS.getString(SQL_PRODUCT_REVIEWS);
                System.out.println(productId + " " + productName + " " + productCategory + " " + productCount + " " + productReviews);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteProduct() {
        try {
            PreparedStatement deleteProductStatement = databaseConnector.connect.prepareStatement(DELETE_PRODUCT_QUERY);
            deleteProductStatement.setInt(1, Communicator.productToDelete());
            deleteProductStatement.executeUpdate();
            deleteProductStatement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets value from users table to check if required quantity of rented products did'nt cross threshold.
     */
    private void checkRentedQuantity(String username) {
        try {
            Statement statementThreshold = databaseConnector.connect.createStatement();
            ResultSet checkQuantityThresholdRs = statementThreshold.executeQuery(QUANTITY_OD_PRODUCTS_QUERY);
            while (checkQuantityThresholdRs.next()) {
                if (checkQuantityThresholdRs.getString(SQL_USER_NAME).equals(username) && checkQuantityThresholdRs.getInt(SQL_NUMBER_OF_RENTALS) >= SQL_MAX_QUANTITY_OF_PRODUCTS) {
                    Communicator.rentRequirement();
                    System.exit(0);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Functions as a buffer. Contains information, what name logged user have.
     */
    private String nameOfLoggedUser;

    public void login() {
        boolean temp = true;
        try {
            Statement statement = databaseConnector.connect.createStatement();
            ResultSet loginUserRs = statement.executeQuery(USERS_QUERY);
            while (temp) {
                String enterLogin = Communicator.enterLoginField();
                String enterPassword = Communicator.enterPasswordField();
                while (loginUserRs.next()) {
                    if (loginUserRs.getString(SQL_USER_NAME).equals(enterLogin) && loginUserRs.getString(SQL_USER_PASSWORD).equals(enterPassword)) {
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
            PreparedStatement createUserStatement = databaseConnector.connect.prepareStatement(CREATE_USER_QUERY);
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
        checkRentedQuantity(nameOfLoggedUser);
        try {
            PreparedStatement rentProductStatement = databaseConnector.connect.prepareStatement(RENT_PRODUCT_QUERY);
            PreparedStatement addProductStatement = databaseConnector.connect.prepareStatement(ADD_STOCK_QUERY);
            Statement statementProductName = databaseConnector.connect.createStatement();
            ResultSet productToRentRs = statementProductName.executeQuery(PRODUCTS_NAME_QUERY);
            int productId = Communicator.enterProductId();
            while (productToRentRs.next()) {
                if (productToRentRs.getInt(SQL_PRODUCT_ID) == productId && checkDuplicateProducts(productToRentRs.getString(SQL_PRODUCT_NAME))) {
                    if (checkDuplicateProducts(productToRentRs.getString(SQL_PRODUCT_NAME))) {
                        rentProductStatement.setString(1, productToRentRs.getString(SQL_PRODUCT_NAME) + " ");
                        rentProductStatement.setString(2, nameOfLoggedUser);
                        rentProductStatement.executeUpdate();
                        addProductStatement.setString(1, nameOfLoggedUser);
                        addProductStatement.executeUpdate();
                        Communicator.successfullyRented();
                    } else {
                        Communicator.productInPossessionAlready();
                        break;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets and return value to change field of products table.
     */
    private void returnProductStock(String productName) {
        try {
            PreparedStatement productStockStatement = databaseConnector.connect.prepareStatement(PRODUCT_STOCK_QUERY);
            productStockStatement.setString(1, productName);
            productStockStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Gets and return value to change field of users table.
     */
    private void subtractUserStock() {
        try {
            PreparedStatement userStockStatement = databaseConnector.connect.prepareStatement(USERS_STOCK_QUERY);
            userStockStatement.setString(1, nameOfLoggedUser);
            userStockStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void returnRentedProduct() {
        try {
            String nameOfProduct = Communicator.productToReturn();
            returnProductStock(nameOfProduct);
            subtractUserStock();
            PreparedStatement deleteProductNameStatement = databaseConnector.connect.prepareStatement(RETURN_PRODUCT_QUERY);
            deleteProductNameStatement.setString(1, nameOfProduct);
            deleteProductNameStatement.setString(2, nameOfLoggedUser);
            deleteProductNameStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public String getUserRole() {
        StringBuilder userRoleBuffer = new StringBuilder();
        try {
            Statement statementUserRole = databaseConnector.connect.createStatement();
            ResultSet usersRoleRs = statementUserRole.executeQuery(USERS_QUERY);
            while (usersRoleRs.next()) {
                if (usersRoleRs.getString(SQL_USER_NAME).equals(nameOfLoggedUser)) {
                    userRoleBuffer.append(usersRoleRs.getString(SQL_USER_ROLE));
                }
            }
            usersRoleRs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userRoleBuffer.toString();
    }

    private boolean checkDuplicateProducts(String productToRent) {
        try {
            Statement statementDuplicateUser = databaseConnector.connect.createStatement();
            ResultSet checkDuplicateUserRS = statementDuplicateUser.executeQuery(USERS_QUERY);
            while (checkDuplicateUserRS.next()) {
                if (checkDuplicateUserRS.getString(SQL_USER_NAME).equals(nameOfLoggedUser)) {
                    if (!checkDuplicateUserRS.getString(SQL_RENTED_PRODUCTS).equals(productToRent))
                        return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
