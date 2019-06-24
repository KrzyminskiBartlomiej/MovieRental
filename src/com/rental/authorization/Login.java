package com.rental.authorization;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

/**
 * Represents a single login module which authenticates and provides a way to access to next application functionality.<p>
 * Controls user input and users.xml file.
 *
 * @author Piotr Nawrocki
 */
public class Login {
    private XmlWorker xml = new XmlWorker();
    public static String nameOfLoggedUser;

    /**
     * Uses user input and gets data from users.xml to checks if it's possible to log user in.
     */
    public void login() {
        boolean temp = true;
        while (temp) {
            String enteredUserLogin = Communicator.enterLoginField();
            for (int x = 0; x < XmlWorker.userList.getLength(); x++) {
                if (enteredUserLogin.equals(xml.getUserNameFromBase(x)) && Communicator.enterPasswordField().equals(xml.getUserPasswordFromBase(x))) {
                    Communicator.correctDataInfo();
                    nameOfLoggedUser = enteredUserLogin;
                    temp = false;
                    break;
                }
            }
        }
    }
}
