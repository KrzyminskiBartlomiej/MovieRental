package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * Allows to use panel with available actions to logged in user with role marked as "user"(in users.xml).
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
