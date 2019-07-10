package com.rental.utils;

import com.rental.controller.RentalProcessor;

import java.sql.*;

public class SqlWorker implements Worker {
    private final static String PRODUCT_ID_FIELD = "product_id";
    private final static String PRODUCT_CATEGORY_FIELD = "category_name";
    private final static String PRODUCT_COUNT_FIELD = "in_stock";
    private final static String PRODUCT_REVIEWS_FIELD = "user_reviews";
    private final static String PRODUCT_NAME_FIELD = "product_name";
    private final static String USER_NAME_FIELD = "user_name";
    private final static String USER_PASSWORD_FIELD = "password";
    private final static String USER_ROLE_FIELD = "user_role";
    private final static String SQL_CREATE_PRODUCT = "insert into products(product_name, category_name, in_stock, user_reviews) values(?, ?, ?, ?)";
    private final static String SQL_CREATE_USER = "insert into users(user_name, password, role, rented_products) values(?, ?, ?, ?)";
    private final static String SQL_RENT_PRODUCT = "update users set rented_products=concat(ifnull(rented_products, ''), ?) WHERE user_name = ?";
    private final static String SQL_DELETE_PRODUCT = "delete from products where product_id = ?";
    private final static String PRODUCTS_QUERY = "SELECT * FROM products";
    private final static String PRODUCTS_NAME_QUERY = "SELECT product_name, product_id FROM products";
    private final static String USERS_QUERY = "SELECT * FROM users";
    private PreparedStatement addProductStatement;
    private PreparedStatement createUserStatement;
    private PreparedStatement deleteProductStatement;
    private PreparedStatement rentProductStatement;
    private ResultSet usersRole;
    private ResultSet products_rs;
    private ResultSet products_name_rs;
    private ResultSet users_rs;

    SqlWorker() {
        try {
            DatabaseConnector connector = new DatabaseConnector(); //dependency injection
            addProductStatement = connector.connect.prepareStatement(SQL_CREATE_PRODUCT);
            rentProductStatement = connector.connect.prepareStatement(SQL_RENT_PRODUCT);
            createUserStatement = connector.connect.prepareStatement(SQL_CREATE_USER);
            deleteProductStatement = connector.connect.prepareStatement(SQL_DELETE_PRODUCT);
            Statement statementProduct = connector.connect.createStatement();
            Statement statementProductName = connector.connect.createStatement();
            Statement statementUserRole = connector.connect.createStatement();
            Statement statement = connector.connect.createStatement();
            products_rs = statementProduct.executeQuery(PRODUCTS_QUERY);
            products_name_rs = statementProductName.executeQuery(PRODUCTS_NAME_QUERY);
            users_rs = statement.executeQuery(USERS_QUERY);
            usersRole = statementUserRole.executeQuery(USERS_QUERY);
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
            while (products_rs.next()) {
                int productId = products_rs.getInt(PRODUCT_ID_FIELD);
                String productName = products_rs.getString(PRODUCT_NAME_FIELD);
                String productCategory = products_rs.getString(PRODUCT_CATEGORY_FIELD);
                String productCount = products_rs.getString(PRODUCT_COUNT_FIELD);
                String productReviews = products_rs.getString(PRODUCT_REVIEWS_FIELD);
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

    private static String nameOdLoggedUser;

    public void login() {
        boolean temp = true;
        try {
            while (temp) {
                String enterLogin = Communicator.enterLoginField();
                String enterPassword = Communicator.enterPasswordField();
                while (users_rs.next()) {
                    if (users_rs.getString(USER_NAME_FIELD).equals(enterLogin) && users_rs.getString(USER_PASSWORD_FIELD).equals(enterPassword)) {
                        Communicator.correctDataInfo();
                        nameOdLoggedUser = enterLogin;
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
            createUserStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void rentProduct() {
        StringBuilder buffer = new StringBuilder();
        int productId = Communicator.enterProductId();
        try {
            while (products_name_rs.next()) {
                if (products_name_rs.getInt(PRODUCT_ID_FIELD) == productId) {
                    buffer.append(products_name_rs.getString(PRODUCT_NAME_FIELD));
                    break;
                }
            }
            rentProductStatement.setString(1, buffer.toString() + " ");
            rentProductStatement.setString(2, nameOdLoggedUser);
            rentProductStatement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void returnRentedProduct() {
    }

    public String getUserRole() {
        StringBuilder buffer = new StringBuilder();
        try {
            while (usersRole.next()) {
                if (usersRole.getString(USER_NAME_FIELD).equals(nameOdLoggedUser)) {
                    buffer.append(usersRole.getString(USER_ROLE_FIELD));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return buffer.toString();
    }
}
