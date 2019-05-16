package com.rental.controller;

import com.rental.authorization.Login;
import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * This class allows and shows user, useful panel content after logged in.<p>
 *
 * @since 1.0
 * @author Piotr Nawrocki
 */
class Panel {
    private XmlWorker xml = new XmlWorker();
    private Communicator communicator = new Communicator();

    /**
     * This method displays available features for logged users.
     * Successful execution of the runPanel method displays options and allows access to use all of them.
     * @param login reference to the object created in the RentalProcessor class
     */
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

    /**
     * Enters changes to users.xml and products.xml files.
     * Depending on user input, the method performs proper action.
     * @param login reference to the object created in the RentalProcessor class
     * @see RentalProcessor
     */
    private void rentProduct(Login login) {
        this.userInputSelected = Communicator.enterProductId();
        xml.takeProductOutOfStock(userInputSelected);
        xml.enterProductNameToUserBase(userInputSelected, login);
        Communicator.successfullyRented();
    }

    /**
     * Enters changes to users.xml and products.xml files.
     * Depending on user input, the method performs appropriate action.
     * @param login reference to the object created in the RentalProcessor class
     */
    private void returnProduct(Login login) {
        xml.returnProductOnStock(this.userInputSelected);
        xml.deleteProductFromUserBase(login);
        Communicator.successfullyReturnProduct();
    }
}