package com.rental.authorization;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * This class represents a single login module which authenticates and provides a way to access to next app functionality.
 * Controls user input and xml file.
 *
 * @since 1.0
 * @author Piotr Nawrocki
 */
public class Login {
    private XmlWorker xml = new XmlWorker();
    /**
     * Name of the logged user.
     */
    private String nameOfLoggedUser;

    /**
     * The permission which this method will check/compare if user input is correct with data in xml file.
     * @see XmlWorker#getUserNameFromBase(int)
     * @see XmlWorker#getUserPasswordFromBase(int)
     * @see Communicator#enterLoginField()
     * @see Communicator#enterPasswordField()
     * @see Communicator#correctDataInfo()
     */
    public void login() {
        boolean temp = true;
        while (temp) {
            String enteredUserLogin = Communicator.enterLoginField();
            for (int x = 0; x < xml.userList.getLength(); x++) {
                if (enteredUserLogin.equals(xml.getUserNameFromBase(x)) && Communicator.enterPasswordField().equals(xml.getUserPasswordFromBase(x))) {
                    Communicator.correctDataInfo();
                    setNameOfLoggedUser(enteredUserLogin);
                    temp = false;
                    break;
                }
            }
        }
    }

    /**
     * Name of the user.
     * @return The appropriate username selected.
     */
    public String getNameOfLoggedUser() {
        return nameOfLoggedUser;
    }

    /**
     * Set the found, correct username.
     * @param nameOfLoggedUser Name of the user.
     */
    private void setNameOfLoggedUser(String nameOfLoggedUser) {
        this.nameOfLoggedUser = nameOfLoggedUser;
    }
}