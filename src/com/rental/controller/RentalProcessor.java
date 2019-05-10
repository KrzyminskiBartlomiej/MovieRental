package com.rental.controller;

import com.rental.authorization.Registration;
import com.rental.authorization.Login;
import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

public class RentalProcessor {
    public static void main(String[] args) throws Exception {
        XmlWorker xml = new XmlWorker();

        switch (Communicator.enterAuthorizationOption()) {
            case 1: {
                Login.login(xml.userList);
                Panel.runPanel(xml.productsBase, xml.usersBase, xml.userList, xml.productList);
                break;
            }
            case 2: {
                Registration.signUp(xml.usersBase, xml.rootElementUser, xml.rootUser, xml.userList);
                break;
            }
            default: {
                Communicator.enteredDifferentOption();
            }
        }
    }
}