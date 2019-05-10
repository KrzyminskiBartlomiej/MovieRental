package com.rental.utils;
import com.rental.authorization.Login;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;

public class XmlWorker {
    private static final String USER_NAME_ATT = "user_name";
    private static final String USER_PASSWORD_ATT = "password";
    private static final String PRODUCT_IN_STOCK_ATT = "in_stock";
    private static final String RENTED_PRODUCT_ATT = "rented_product";
    private static final String PRODUCT_NAME_ATT = "product_name";
    private static final String USER_ID_ATT = "user_id";
    private static final String USER_BASE_PATH = "resources/users.xml";
    private static final String PRODUCT_BASE_PATH = "resources/products.xml";
    public Document productsBase;
    public Document usersBase;
    public NodeList productList;
    public NodeList userList;
    public Element rootUser;
    public Element rootElementUser;

    public XmlWorker() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
        this.productsBase = documentBuilder.parse(PRODUCT_BASE_PATH);
        this.usersBase = documentBuilder.parse(USER_BASE_PATH);
        this.productList = productsBase.getElementsByTagName("product");
        this.userList = usersBase.getElementsByTagName("user");
        this.rootUser = usersBase.getDocumentElement();
        this.rootElementUser = usersBase.getDocumentElement();
    }

    public static void saveChangesInXmlProductsFiles(Document productBase) {
        DOMSource productSource = new DOMSource(productBase);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "all");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "all");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            StreamResult productsResult = new StreamResult(PRODUCT_BASE_PATH);
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
            StreamResult usersResult = new StreamResult(USER_BASE_PATH);
            transformer.transform(usersSource, usersResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public static String getUserNameFromBase(NodeList userList, int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_NAME_ATT).getNodeValue();
    }

    public static String getUserPasswordFromBase(NodeList userList, int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_PASSWORD_ATT).getNodeValue();
    }

    public static void takeProductOutOfStock(NodeList productList, int userInput) {
        String getInStock = productList.item(userInput).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock--;
        if (getNewInStock < 0) {
            Communicator.outOfStock();
            System.exit(0);
        }
        productList.item(userInput).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
    }

    public static void returnProductOnStock(NodeList productList, int userInputSelected) {
        String getInStock = productList.item(userInputSelected).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock++;
        productList.item(userInputSelected).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
    }

    public static void deleteProductFromUserBase(NodeList userList) {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(Login.nameOfLoggedUser)) {
                ((Element) tryToFindUser).setAttribute(RENTED_PRODUCT_ATT, null);
            }
        }
    }

    public static void enterProductNameToUserBase(NodeList productList, NodeList userList, int userInput) {
        String nameOfRentedProduct = productList.item(userInput).getAttributes().getNamedItem(PRODUCT_NAME_ATT).getNodeValue();
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(Login.nameOfLoggedUser)) {
                ((Element) tryToFindUser).setAttribute(RENTED_PRODUCT_ATT, nameOfRentedProduct);
            }
        }
    }

    public static void createUserElementsAndValuesInBase(Document usersBase, Element rootElementUser, String idLengthAsString, Element rootUser) {
        Element account = usersBase.createElement(USER_NAME_ATT);
        Attr attr = usersBase.createAttribute(USER_ID_ATT);
        attr.setValue(idLengthAsString);
        rootElementUser.appendChild(account);
        account.setAttributeNode(attr);
        Attr attrLogin = usersBase.createAttribute(USER_NAME_ATT);
        attrLogin.setValue(Communicator.enterLoginField());
        rootElementUser.appendChild(account);
        account.setAttributeNode(attrLogin);
        Attr attrPassword = usersBase.createAttribute(USER_PASSWORD_ATT);
        attrPassword.setValue(Communicator.enterPasswordField());
        rootElementUser.appendChild(account);
        account.setAttributeNode(attrPassword);
        rootUser.appendChild(account);
    }
}