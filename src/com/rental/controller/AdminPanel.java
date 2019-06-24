package com.rental.controller;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

public class AdminPanel implements Panel {
    private XmlWorker xmlWorker = new XmlWorker();

    @Override
    public void deleteMovie() {
        XmlWorker.deleteProduct();
    }

    @Override
    public void createProduct() {
        xmlWorker.createNewProductInBase();
    }

    @Override
    public void runPanel() {
        boolean exit = false;
        do {
            switch (Communicator.adminPanelOptions()) {
                case 1: {
                    showAllMovie();
                    break;
                }
                case 2: {
                    deleteMovie();
                    break;
                }
                case 3: {
                    createProduct();
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
