package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * Allows and shows user, useful panel content after logged in.
 *
 * @author Piotr Nawrocki
 */
public class UserPanel implements Panel {

    public void deleteMovie() {
        Communicator.userNotHavePermission();
    }

    public void createProduct() {
        Communicator.userNotHavePermission();
    }

    /**
     * Displays available features for logged users.<p>
     * Successful execution of the runPanel method displays options and allows access to use all of them.
     */
    @Override
    public void runPanel() {
        boolean exit = false;
        do {
            switch (Communicator.userPanelOptions()) {
                case 1: {
                    Communicator.getAndShowProducts();
                    XmlWorker.enterProductNameToUserBase(Communicator.enterProductId());
                    Communicator.successfullyRented();
                    break;
                }
                case 2: {
                    XmlWorker.returnRentedProduct();
                    Communicator.successfullyReturnProduct();
                    break;
                }
                case 3: {
                    showAllMovie();
                    break;
                }
                case 4: {
                    deleteMovie();
                    break;
                }
                case 5: {
                    createProduct();
                    break;
                }
                case 6: {
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
}
