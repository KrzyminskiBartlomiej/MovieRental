package com.rental.controller;

import com.rental.authorization.Registration;
import com.rental.authorization.Login;
import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * Contains main program loop i.e. all main features which allow:<p>
 * - sign up to application by providing identity information,<p>
 * - login into application using suitable data, <p>
 * - give access to main user Panel.<p>
 *
 * @author Piotr Nawrocki
 */
public class RentalProcessor {
    /**
     * Entry point of application.
     */
    public static void main(String[] args) {
        Login login = new Login();
        Panel userPanel = new UserPanel();
        Panel adminPanel = new AdminPanel();
        Registration registration = new Registration();

        switch (Communicator.enterAuthorizationOption()) {
            case 1: {
                login.login();
                if (XmlWorker.getUserRole().equals(XmlWorker.ADMIN_ROLE)) {
                    adminPanel.runPanel();
                } else {
                    userPanel.runPanel();
                }
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
