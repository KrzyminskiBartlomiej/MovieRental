package com.rental.controller;

import com.rental.authorization.Registration;
import com.rental.authorization.Login;
import com.rental.utils.Communicator;

/**
 * This class contains main program loop i.e. all main features which allow:<p>
 * - sign up to app by providing identity information,<p>
 * - login into app using suitable data, <p>
 * - give access to main user Panel.<p>
 *
 * @author Piotr Nawrocki
 * @since 1.0
 */
public class RentalProcessor {
    /**
     * This is the main method - entry point of app.
     * @see Login#login()
     * @see Panel#runPanel(Login)
     * @see Registration#signUp()
     * @see Communicator#enteredDifferentOption()
     * @see Communicator#enterAuthorizationOption()
     * @param args unused.
     */
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