package com.rental.controller;

import com.rental.authorization.Login;
import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

class Panel {
    private XmlWorker xml = new XmlWorker();
    private Communicator communicator = new Communicator();

    void runPanel(Login login) {
        boolean exit = false;
        do {
            Communicator.rentRequirement();
            switch (Communicator.enterPanelOptions()) {
                case 1: {
                    communicator.getAndShowProducts();
                    rentProduct(login);
                    break;
                }
                case 2: {
                    returnProduct(login);
                    break;
                }
                case 3: {
                    communicator.getAndShowProducts();
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

    private int userInputSelected;

    private void rentProduct(Login login) {
        this.userInputSelected = Communicator.enterProductId();
        xml.takeProductOutOfStock(userInputSelected);
        xml.enterProductNameToUserBase(userInputSelected, login);
        Communicator.successfullyRented();
    }

    private void returnProduct(Login login) {
        xml.returnProductOnStock(this.userInputSelected);
        xml.deleteProductFromUserBase(login);
        Communicator.successfullyReturnProduct();
    }
}