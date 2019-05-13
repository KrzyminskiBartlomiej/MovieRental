package com.rental.controller;

import com.rental.authorization.Registration;
import com.rental.authorization.Login;
import com.rental.utils.Communicator;

public class RentalProcessor {
    public static void main(String[] args) {
        Login login = new Login();
        Panel panel = new Panel();
        Registration registration = new Registration();

        switch (Communicator.enterAuthorizationOption()) {
            case 1: {
                login.login();
                panel.runPanel(login);
                break;
            }
            case 2: {
                registration.signUp();
                break;
            }
            default: {
                Communicator.enteredDifferentOption();
            }
        }
    }
}