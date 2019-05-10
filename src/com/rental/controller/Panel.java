package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;
import org.w3c.dom.*;

class Panel {
    Panel() {
    }
    static void runPanel(Document productBase, Document usersBase, NodeList userList, NodeList productList) {
        boolean exit = false;
        do {
            Communicator.rentRequirement();
            switch (Communicator.enterPanelOptions()) {
                case 1: {
                    Communicator.getAndShowProducts(productList);
                    rentProduct(productBase, usersBase, userList, productList);
                    break;
                }
                case 2: {
                    returnProduct(productBase, usersBase, userList, productList);
                    break;
                }
                case 3: {
                    Communicator.getAndShowProducts(productList);
                    break;
                }
                case 4: {
                    exit = true;
                    break;
                }
                default: {
                    Communicator.enteredDifferentOption();
                    break;
                }
            }
        } while (!exit);
    }
    private static int userInputSelected;

    private static void rentProduct(Document productBase, Document usersBase, NodeList userList, NodeList productList) {
        userInputSelected = Communicator.enterProductId();
        XmlWorker.takeProductOutOfStock(productList, userInputSelected);
        XmlWorker.enterProductNameToUserBase(productList, userList, userInputSelected);
        XmlWorker.saveChangesInXmlUsersFiles(usersBase);
        XmlWorker.saveChangesInXmlProductsFiles(productBase);
        Communicator.successfullyRented();
    }

    private static void returnProduct(Document productBase, Document usersBase, NodeList userList, NodeList productList) {
        XmlWorker.returnProductOnStock(productList, userInputSelected);
        XmlWorker.deleteProductFromUserBase(userList);
        XmlWorker.saveChangesInXmlUsersFiles(usersBase);
        XmlWorker.saveChangesInXmlProductsFiles(productBase);
        Communicator.successfullyReturnProduct();
    }
}