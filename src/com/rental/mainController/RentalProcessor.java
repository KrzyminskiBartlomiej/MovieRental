package com.rental.mainController;

import com.rental.authorization.Register;
import com.rental.authorization.Login;
import com.rental.utils.Communicator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class RentalProcessor {
    public static void main(String[] args) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
        Document productsBase = documentBuilder.parse("resources/products.xml");
        Document usersBase = documentBuilder.parse("resources/users.xml");
        NodeList productList = productsBase.getElementsByTagName("product");
        NodeList userList = usersBase.getElementsByTagName("user");
        Element rootUser = usersBase.getDocumentElement();
        Element rootElementUser = usersBase.getDocumentElement();

        switch (Communicator.enterAuthorizationOption()) {
            case 1: {
                Login.login(userList);
                Panel.runPanel(productsBase, usersBase, userList, productList);
                break;
            }
            case 2: {
                Register.registration(usersBase, rootElementUser, rootUser, userList);
                break;
            }
            default: {
                Communicator.enteredDifferentOption();
            }
        }
    }
}