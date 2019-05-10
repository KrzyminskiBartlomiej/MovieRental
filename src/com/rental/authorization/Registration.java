package com.rental.authorization;

import com.rental.utils.XmlWorker;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Registration {
    public static void signUp(Document usersBase, Element rootElementUser, Element rootUser, NodeList userList) {
        int idLength = userList.getLength();
        idLength++;
        String idLengthAsString = Integer.toString(idLength);
        XmlWorker.createUserElementsAndValuesInBase(usersBase, rootElementUser, idLengthAsString, rootUser);
        XmlWorker.saveChangesInXmlUsersFiles(usersBase);
    }
}