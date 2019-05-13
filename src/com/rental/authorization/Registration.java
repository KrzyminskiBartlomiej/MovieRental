package com.rental.authorization;

import com.rental.utils.XmlWorker;

public class Registration {
    private XmlWorker xml = new XmlWorker();

    public void signUp() {
        int idLength = xml.userList.getLength();
        idLength++;
        String idLengthAsString = Integer.toString(idLength);
        xml.createUserElementsAndValuesInBase(idLengthAsString);
        xml.saveChangesInXmlUsersFiles();
    }
}