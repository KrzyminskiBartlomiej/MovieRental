package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * Allows and shows user, useful panel content after logged in.
 *
 * @author Piotr Nawrocki
 */
class Panel {
    private Communicator communicator = new Communicator();

    /**
     * Displays available features for logged users.<p>
     * Successful execution of the runPanel method displays options and allows access to use all of them.
     */
    void runPanel() {
        boolean exit = false;
        do {
            switch (Communicator.enterPanelOptions()) {
                case 1: {
                    communicator.getAndShowProducts();
                    rentProduct();
                    break;
                }
                case 2: {
                    XmlWorker.returnRentedProduct();
                    Communicator.successfullyReturnProduct();
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


    /**
     * Enters changes to users.xml and products.xml files.
     * Depending on user input, the method performs proper action.
     */
    private void rentProduct() {
        XmlWorker.enterProductNameToUserBase(Communicator.enterProductId());
        Communicator.successfullyRented();
    }
}
