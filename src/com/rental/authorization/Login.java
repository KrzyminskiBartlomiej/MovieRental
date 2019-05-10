package com.rental.authorization;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;
import org.w3c.dom.NodeList;

public class Login {
    public static String nameOfLoggedUser;
    public Login() {
    }
    public static void login(NodeList userList) {
        boolean temp = true;
        while (temp) {
            String enteredUserLogin = Communicator.enterLoginField();
            for (int x = 0; x < userList.getLength(); x++) {
                if (enteredUserLogin.equals(XmlWorker.getUserNameFromBase(userList, x)) && Communicator.enterPasswordField().equals(XmlWorker.getUserPasswordFromBase(userList, x))) {
                    Communicator.correctDataInfo();
                    nameOfLoggedUser = enteredUserLogin;
                    temp = false;
                    break;
                }
            }
        }
    }
}