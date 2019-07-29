package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.Worker;

/**
 * Allows to use panel with available actions to logged in user with role marked as "admin"(in users.xml).
 *
 * @author Piotr Nawrocki
 */
public class AdminPanel implements Panel {
    
    @Override
    public void runPanel(Worker worker) {
        boolean exit = false;
        do {
            switch (Communicator.adminPanelOptions()) {
                case 1: {
                    worker.showAllMovie();
                    break;
                }
                case 2: {
                    worker.deleteProduct();
                    break;
                }
                case 3: {
                    worker.createNewProduct();
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
}
