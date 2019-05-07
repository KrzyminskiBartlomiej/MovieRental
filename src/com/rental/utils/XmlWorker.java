package com.rental.utils;

import com.rental.authorization.Login;
import org.w3c.dom.*;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlWorker {
    public static void saveChangesInXmlFiles(Document productBase, Document usersBase) {
        DOMSource productSource = new DOMSource(productBase);
        DOMSource usersSource = new DOMSource(usersBase);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "all");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "all");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult usersResult = new StreamResult("resources/users.xml");
            StreamResult productsResult = new StreamResult("resources/products.xml");
            transformer.transform(usersSource, usersResult);
            transformer.transform(productSource, productsResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static void saveChangesInXmlUsersFiles(Document usersBase) {
        DOMSource usersSource = new DOMSource(usersBase);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "all");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "all");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult usersResult = new StreamResult("resources/users.xml");
            transformer.transform(usersSource, usersResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static String getUserNameFromBase(NodeList userList, int x) {
        return userList.item(x).getAttributes().getNamedItem("name").getNodeValue();
    }

    public static String getUserPasswordFromBase(NodeList userList, int x) {
        return userList.item(x).getAttributes().getNamedItem("password").getNodeValue();
    }

    public static void takeProductOutOfStock(NodeList productList, int userInput) {
        String getInStock = productList.item(userInput).getAttributes().getNamedItem("in_stock").getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock--;
        if (getNewInStock < 0) {
            Communicator.outOfStock();
            System.exit(0);
        }
        productList.item(userInput).getAttributes().getNamedItem("in_stock").setNodeValue(Integer.toString(getNewInStock));
    }

    public static void returnProductOnStock(NodeList productList, int userInputSelected) {
        String getInStock = productList.item(userInputSelected).getAttributes().getNamedItem("in_stock").getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock++;
        productList.item(userInputSelected).getAttributes().getNamedItem("in_stock").setNodeValue(Integer.toString(getNewInStock));
    }

    public static void deleteProductFromUserBase(NodeList userList) {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem("name").getTextContent().equalsIgnoreCase(Login.nameLoggedUser)) {
                ((Element) tryToFindUser).setAttribute("rented", null);
            }
        }
    }

    public static void enterProductNameToUserBase(NodeList productList, NodeList userList, int userInput) {
        String nameOfRentedProduct = productList.item(userInput).getAttributes().getNamedItem("name").getNodeValue();
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem("name").getTextContent().equalsIgnoreCase(Login.nameLoggedUser)) {
                ((Element) tryToFindUser).setAttribute("rented", nameOfRentedProduct);
            }
        }
    }

    public static void createUserElementsAndValuesInBase(Document usersBase, Element rootElementUser, String idLengthAsString, Element rootUser) {
        Element account = usersBase.createElement("user");
        Attr attr = usersBase.createAttribute("id");
        attr.setValue(idLengthAsString);
        rootElementUser.appendChild(account);
        account.setAttributeNode(attr);
        Attr attrLogin = usersBase.createAttribute("name");
        attrLogin.setValue(Communicator.enterLoginField());
        rootElementUser.appendChild(account);
        account.setAttributeNode(attrLogin);
        Attr attrPassword = usersBase.createAttribute("password");
        attrPassword.setValue(Communicator.enterPasswordField());
        rootElementUser.appendChild(account);
        account.setAttributeNode(attrPassword);
        rootUser.appendChild(account);
    }
}