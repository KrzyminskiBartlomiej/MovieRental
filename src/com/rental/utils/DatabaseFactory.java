package com.rental.utils;

/**
 * Contains the factory method pattern.<p>
 *
 * @author Piotr Nawrocki
 */
public class DatabaseFactory {
    /**
     * Gives user choice of database to use.<p>
     * Included factory method pattern allows to work on XML or mySql database.
     */
    public Worker runDatabase() {
        switch (Communicator.selectDatabase()) {
            case 1: {
                return new XmlWorker();
            }
            case 2: {
                return new SqlWorker();
            }
            default: {
                Communicator.enteredDifferentOption();
            }
        }
        return null;
    }
}
