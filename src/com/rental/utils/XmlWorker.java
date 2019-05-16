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

/**
 * This class is using DOM(Document Object Model)
 * representation of XML, HTML documents handles an XML document as a tree structure.
 * The XmlWorker is used to configure connection with XML files.
 *
 * @since 1.0
 * @author Piotr Nawrocki
 */
public class XmlWorker {

    private static final String USER_NAME_ATT = "user_name";
    private static final String USER_PASSWORD_ATT = "password";
    private static final String PRODUCT_IN_STOCK_ATT = "in_stock";
    private static final String RENTED_PRODUCT_ATT = "rented_product";
    private static final String PRODUCT_NAME_ATT = "product_name";
    private static final String USER_ID_ATT = "user_id";
    private static final String USER_BASE_PATH = "resources/users.xml";
    private static final String PRODUCT_BASE_PATH = "resources/products.xml";
    private static final String USER_TAG_NAME = "user";
    private static final String PRODUCT_TAG_NAME = "product";
    private Document productsBase;
    private Document usersBase;
    NodeList productList;
    public NodeList userList;
    private Element rootUser;
    private Element rootElementUser;
    private DOMSource productSource;
    private DOMSource usersSource;
    Element productElement;

    /**
     * The XmlWorker constructor provides information about all xml, specified initialization parameters.
     */
    public XmlWorker() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory
                    .newDocumentBuilder();
            this.productsBase = documentBuilder.parse(PRODUCT_BASE_PATH);
            this.usersBase = documentBuilder.parse(USER_BASE_PATH);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        this.productList = productsBase.getElementsByTagName(PRODUCT_TAG_NAME);
        this.userList = usersBase.getElementsByTagName(USER_TAG_NAME);
        this.rootUser = usersBase.getDocumentElement();
        this.rootElementUser = usersBase.getDocumentElement();
        this.productSource = new DOMSource(this.productsBase);
        this.usersSource = new DOMSource(this.usersBase);
    }

    /**
     * Approves all changes in the products.xml file, made at the stage of application operation.
     */
    private void saveChangesInXmlProductsFiles() {
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
    /**
     * Approves all changes in the users.xml file, made at the stage of application operation.
     */
    private void saveChangesInXmlUsersFiles() {
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
    /**
     * Returns a string including value of parameter from users.xml file.
     * @param userId user ID based on which tries to get proper username
     */
    public String getUserNameFromBase(int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_NAME_ATT).getNodeValue();
    }
    /**
     * Returns a string including value of parameter from users.xml file.
     * @param userId user ID based on which tries to get proper user's password
     */
    public String getUserPasswordFromBase(int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_PASSWORD_ATT).getNodeValue();
    }
    /**
     * Gets and enters new changes attribute of products.xml file.
     * @param userInput data entered by the user to get the appropriate selection of values from product.xml file.
     */
    public void takeProductOutOfStock(int userInput) {
        String getInStock = productList.item(userInput).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock--;
        if (getNewInStock < 0) {
            Communicator.outOfStock();
            System.exit(0);
        }
        productList.item(userInput).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
    }
    /**
     * Gets a string including value of parameter from users.xml file.
     * In the case of a positive implementation, the data is entered into the appropriate xml file.
     * @param userInputSelected data entered by the user to get and change value in product.xml file
     */
    public void returnProductOnStock(int userInputSelected) {
        String getInStock = productList.item(userInputSelected).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock++;
        productList.item(userInputSelected).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
        saveChangesInXmlUsersFiles();
        saveChangesInXmlProductsFiles();
    }

    /**
     * Changes the name of the attribute in the users.xml file to null.
     * Aditionally, it checks and compares the user input and xml file if the value can be added.
     * In the case of a positive implementation, the data is entered into the appropriate xml file.
     * @param login reference to the object created in the RentalProcessor class
     */
    public void deleteProductFromUserBase(Login login) {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(login.getNameOfLoggedUser())) {
                ((Element) tryToFindUser).setAttribute(RENTED_PRODUCT_ATT, null);
            }
        }
        saveChangesInXmlUsersFiles();
        saveChangesInXmlProductsFiles();
    }

    /**
     * This method is responsible for attaching an appropriate selection to the xml file.
     * Aditionally, it checks and compares the user input and xml file if the value can be added.
     * @param userInput data entered by the user to get the appropriate selection of values ti users.xml file.
     * @param login reference to the object created in the RentalProcessor class
     */
    public void enterProductNameToUserBase(int userInput, Login login) {
        String nameOfRentedProduct = productList.item(userInput).getAttributes().getNamedItem(PRODUCT_NAME_ATT).getNodeValue();
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(login.getNameOfLoggedUser())) {
                ((Element) tryToFindUser).setAttribute(RENTED_PRODUCT_ATT, nameOfRentedProduct);
            }
        }
    }

    /**
     * This method allows to create a new user who is required to provide relevant data.
     * In the case of a positive implementation, the data is entered into the appropriate xml file.
     * @param idLengthAsString numerical value(String) of the attribute ID
     */
    public void createUserElementsAndValuesInBase(String idLengthAsString) {
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
        saveChangesInXmlUsersFiles();
    }
}