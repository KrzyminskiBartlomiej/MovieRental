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
 * Uses DOM(Document Object Model)
 * representation of XML, HTML documents handles an XML document as a tree structure.<p>
 * The XmlWorker is used to configure connection with XML files.
 *
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
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_COUNT = "in_stock";
    private static final String PRODUCT_REVIEW = "user_reviews";
    private static final String PRODUCT_CATEGORY = "category_name";
    private static final String ROLE_ATT = "role";
    private static final String USER_ROLE = "user";
    public static final String ADMIN_ROLE = "admin";
    static final int MAX_QUANTITY_OF_PRODUCTS = 3;
    private static final String INITIAL_REVIEW = "0";
    private static Document productsBase;
    private static Document usersBase;
    static NodeList productList;
    public static NodeList userList;
    private Element rootUser;
    private static DOMSource productSource;
    private static DOMSource usersSource;
    static Element productElement;
    private static DocumentBuilderFactory documentBuilderFactory;

    /**
     * The XmlWorker constructor provides information about all xml, specified initialization parameters.
     */
    public XmlWorker() {
        documentBuilderFactory = DocumentBuilderFactory
            .newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
            productsBase = documentBuilder.parse(PRODUCT_BASE_PATH);
            usersBase = documentBuilder.parse(USER_BASE_PATH);
            productList = productsBase.getElementsByTagName(PRODUCT_TAG_NAME);
            userList = usersBase.getElementsByTagName(USER_TAG_NAME);
            rootUser = usersBase.getDocumentElement();
            productSource = new DOMSource(productsBase);
            usersSource = new DOMSource(usersBase);
            documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Approves all changes in the products.xml file, made at the stage of application operation.
     */
    private static void saveChangesInXmlProductsFiles() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "all");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "all");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-16");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            StreamResult productsResult = new StreamResult(PRODUCT_BASE_PATH);
            transformer.transform(productSource, productsResult);
            documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Approves all changes in the users.xml file, made at the stage of application operation.
     */
    private static void saveChangesInXmlUsersFiles() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "all");
            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "all");
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-16");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            StreamResult usersResult = new StreamResult(USER_BASE_PATH);
            transformer.transform(usersSource, usersResult);
            documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns a string including value of parameter from users.xml file.
     *
     * @param userId based on which tries to get proper username
     */
    public String getUserNameFromBase(int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_NAME_ATT).getNodeValue();
    }

    /**
     * Returns a string including value of parameter from users.xml file.
     *
     * @param userId based on which tries to get proper user's password
     */
    public String getUserPasswordFromBase(int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_PASSWORD_ATT).getNodeValue();
    }

    /**
     * Gets and enters new changes attribute of products.xml file.
     *
     * @param productId data entered by the user to get the appropriate selection of values from product.xml file.
     */
    private static void takeProductFromStock(int productId) {
        String getInStock = productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock--;
        if (getNewInStock < 0) {
            Communicator.outOfStock();
            System.exit(0);
        }
        productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
        saveChangesInXmlProductsFiles();
    }

    /**
     * Gets a string including value of parameter from users.xml file.<p>
     * In the case of a positive implementation, the data is entered into the appropriate xml file.
     *
     * @param productId data entered by the user to get and change value in product.xml file
     */
    private static void returnProductOnStock(int productId) {
        String getInStock = productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock++;
        productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
        saveChangesInXmlProductsFiles();
    }

    /**
     * Responsible for attaching an appropriate selection to the users.xml file.<p>
     * Additionally, it checks and compares the user input and xml file if the value can be added.
     *
     * @param productId data entered by the user to get the appropriate selection of values to users.xml file.
     */
    public static void enterProductNameToUserBase(int productId) {
        String nameOfRentedProduct = productList.item(productId).getAttributes().getNamedItem(PRODUCT_NAME_ATT).getNodeValue();
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(Login.nameOfLoggedUser)) {
                NodeList getChildNodesFromUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i).getChildNodes();
                System.out.println(getChildNodesFromUser.getLength());
                if (MAX_QUANTITY_OF_PRODUCTS > getChildNodesFromUser.getLength()) {
                    Element products = usersBase.createElement(RENTED_PRODUCT_ATT);
                    tryToFindUser.appendChild(products);
                    Attr attr = usersBase.createAttribute(PRODUCT_NAME_ATT);
                    attr.setValue(nameOfRentedProduct);
                    products.setAttributeNode(attr);
                    takeProductFromStock(productId);
                    saveChangesInXmlUsersFiles();
                } else {
                    Communicator.rentRequirement();
                    break;
                }
            }
        }
    }

    /**
     * Responsible for returning an appropriate selection from the users.xml and products.xml files.
     */
    public static void returnRentedProduct() {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(Login.nameOfLoggedUser)) {
                NodeList getChildNodesFromUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i).getChildNodes();
                for (int j = 0; j < getChildNodesFromUser.getLength(); j++) {
                    System.out.println(j + "." + getChildNodesFromUser.item(j).getAttributes().getNamedItem(PRODUCT_NAME_ATT).getTextContent());
                }
                Node getNodeLoggedUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i);
                int productId = Communicator.idProductToReturn();
                Node getSelectedChildNode = getNodeLoggedUser.getChildNodes().item(productId);
                getNodeLoggedUser.removeChild(getSelectedChildNode);
                returnProductOnStock(productId);
                saveChangesInXmlUsersFiles();
            }
        }
    }

    public static void deleteProduct() {
        Communicator.getAndShowProducts();
        int productToDelete = Communicator.productToDelete();
        NodeList products = productsBase.getElementsByTagName("product");
        Node name = products.item(productToDelete);
        name.getParentNode().removeChild(name);
        saveChangesInXmlProductsFiles();
    }

    /**
     * Allows to create a new user who is required to provide relevant data.<p>
     * In the case of a positive implementation, the data is entered into the appropriate xml file.
     *
     * @param idLengthAsString numerical value(String) of the attribute ID
     */
    public void createUserElementsAndValuesInBase(String idLengthAsString) {
        Element users = usersBase.createElement(USER_TAG_NAME);
        Attr attr = usersBase.createAttribute(USER_ID_ATT);
        attr.setValue(idLengthAsString);
        rootUser.appendChild(users);
        users.setAttributeNode(attr);
        Attr attrLogin = usersBase.createAttribute(USER_NAME_ATT);
        attrLogin.setValue(Communicator.enterLoginField());
        rootUser.appendChild(users);
        users.setAttributeNode(attrLogin);
        Attr attrPassword = usersBase.createAttribute(USER_PASSWORD_ATT);
        attrPassword.setValue(Communicator.enterPasswordField());
        rootUser.appendChild(users);
        users.setAttributeNode(attrPassword);
        Attr attRole = usersBase.createAttribute(ROLE_ATT);
        attRole.setValue(USER_ROLE);
        rootUser.appendChild(users);
        users.setAttributeNode(attRole);
        rootUser.appendChild(users);
        saveChangesInXmlUsersFiles();
    }

    private static String idForNewProduct() {
        int lastProductId = productList.getLength() + 1;
        return Integer.toString(lastProductId);
    }

    public void createNewProductInBase() {
        Element products = productsBase.createElement(PRODUCT_TAG_NAME);

        Attr categoryAtt = productsBase.createAttribute(PRODUCT_CATEGORY);
        categoryAtt.setValue(Communicator.enterCategory());
        productsBase.getDocumentElement().appendChild(products);
        products.setAttributeNode(categoryAtt);
        Attr productId = productsBase.createAttribute(PRODUCT_ID);
        productId.setValue(idForNewProduct());
        productsBase.getDocumentElement().appendChild(products);
        products.setAttributeNode(productId);
        Attr productName = productsBase.createAttribute(PRODUCT_NAME_ATT);
        productName.setValue(Communicator.enterProductName());
        productsBase.getDocumentElement().appendChild(products);
        products.setAttributeNode(productName);
        Attr productCount = productsBase.createAttribute(PRODUCT_COUNT);
        productCount.setValue(Communicator.enterProductCount());
        productsBase.getDocumentElement().appendChild(products);
        products.setAttributeNode(productCount);
        Attr productReview = productsBase.createAttribute(PRODUCT_REVIEW);
        productReview.setValue(INITIAL_REVIEW);
        productsBase.getDocumentElement().appendChild(products);
        products.setAttributeNode(productReview);
        saveChangesInXmlProductsFiles();
    }

    private static String roleOfLoggedUser;

    public static String getUserRole() {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(Login.nameOfLoggedUser)) {
                roleOfLoggedUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i).getAttributes().getNamedItem(ROLE_ATT).getTextContent();
                break;
            }
        }
        return roleOfLoggedUser;
    }
}
