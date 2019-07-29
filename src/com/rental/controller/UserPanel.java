package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.Worker;

/**
 * Allows to use panel with available actions to logged in user with role marked as "user"(in users.xml).
 *
 * @author Piotr Nawrocki
 */
public class UserPanel implements Panel {

    @Override
    public void runPanel(Worker worker) {
        boolean exit = true;
        while (exit) {

            switch (Communicator.userPanelOptions()) {
                case 1:
                    worker.showAllMovie();
                    worker.rentProduct();
                    break;
                case 2:
                    worker.returnRentedProduct();
                    Communicator.successfullyReturnProduct();
                    break;
                case 3:
                    worker.showAllMovie();
                    break;
                case 4:
                    exit = false;
                    break;
                default: {
                    Communicator.enteredDifferentOption();
                    break;
                }
            }
        }
    }
}
