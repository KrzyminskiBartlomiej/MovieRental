package com.rental.controller;

import com.rental.utils.*;

/**
 * Contains main program loop i.e. all main features which allow:<p>
 * - sign up to application by providing identity information,<p>
 * - login into application using suitable data, <p>
 * - give access to main user Panel.<p>
 *
 * @author Piotr Nawrocki
 */
public class RentalProcessor {
    private static Panel userPanel = new UserPanel();
    private static Panel adminPanel = new AdminPanel();
    private static final String ADMIN_ROLE = "admin";
    public static final String USER_ROLE = "user";

    /**
     * Entry point of application.
     */
    public static void main(String[] args) {
        DatabaseFactory databaseFactory = new DatabaseFactory();
        Worker worker;
        worker = databaseFactory.runDatabase();
        runProcessor(worker);
    }

    private static void runProcessor(Worker worker) {
        switch (Communicator.enterAuthorizationOption()) {
            case 1: {
                worker.login();
                if (worker.getUserRole().equals(ADMIN_ROLE)) {
                    adminPanel.runPanel(worker);
                } else {
                    userPanel.runPanel(worker);
                }
            }
            case 2: {
                worker.registration();
                break;
            }
            default: {
                Communicator.enteredDifferentOption();
            }
        }
    }
}
