package com.rental.authorization;

import com.rental.utils.Communicator;
import com.rental.utils.XmlWorker;

public class Login {
    private XmlWorker xml = new XmlWorker();
    private String nameOfLoggedUser;

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

    public String getNameOfLoggedUser() {
        return nameOfLoggedUser;
    }

    private void setNameOfLoggedUser(String nameOfLoggedUser) {
        this.nameOfLoggedUser = nameOfLoggedUser;
    }
}