package com.rental.utils;

import com.rental.controller.RentalProcessor;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
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
import java.io.StringReader;

/**
 * Uses DOM(Document Object Model)
 * representation of XML, HTML documents handles an XML document as a tree structure.<p>
 * The XmlWorker is used to configure connection with XML files.
 *
 * @author Piotr Nawrocki
 */
public class XmlWorker implements Worker {
    private final String USER_NAME_ATT = "user_name";
    private final String USER_PASSWORD_ATT = "password";
    private final String PRODUCT_IN_STOCK_ATT = "in_stock";
    private final String RENTED_PRODUCT_ATT = "rented_product";
    private final String PRODUCT_NAME_ATT = "product_name";
    private final String USER_ID_ATT = "user_id";
    private final String USER_BASE_PATH = "resources/users.xml";
    private final String PRODUCT_BASE_PATH = "resources/products.xml";
    private final String USER_TAG_NAME = "user";
    private final String PRODUCT_TAG_NAME = "product";
    private final String PRODUCT_ID_ATT = "product_id";
    private final String PRODUCT_COUNT_ATT = "in_stock";
    private final String PRODUCT_REVIEW_ATT = "user_reviews";
    private final String PRODUCT_CATEGORY_ATT = "category_name";
    private final String ROLE_ATT = "role";
    static final int XML_MAX_QUANTITY_OF_PRODUCTS = 3;
    private final String INITIAL_REVIEW_ATT = "0";
    private Document productsBase;
    private Document usersBase;
    private NodeList productList;
    private NodeList userList;
    private Element rootUser;
    private DOMSource productSource;
    private DOMSource usersSource;
    private Element productElement;
    private DocumentBuilderFactory documentBuilderFactory;

    /**
     * The XmlWorker constructor provides information about all xml, specified initialization parameters.
     */
    XmlWorker() {
        try {
            documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
            this.productsBase = documentBuilder.parse(new InputSource(PRODUCT_BASE_PATH));
            this.usersBase = documentBuilder.parse(new InputSource(USER_BASE_PATH));
            this.productList = productsBase.getElementsByTagName(PRODUCT_TAG_NAME);
            this.userList = usersBase.getElementsByTagName(USER_TAG_NAME);
            this.rootUser = usersBase.getDocumentElement();
            this.productSource = new DOMSource(productsBase);
            this.usersSource = new DOMSource(usersBase);
            this.documentBuilderFactory.setIgnoringElementContentWhitespace(true);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Approves all changes in the products.xml file, made at the stage of application operation.
     */
    private void saveChangesInXmlFiles(DOMSource source, String path) {
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
            StreamResult result = new StreamResult(path);
            transformer.transform(source, result);
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
    private String getUserNameFromBase(int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_NAME_ATT).getNodeValue();
    }

    /**
     * Returns a string including value of parameter from users.xml file.
     *
     * @param userId based on which tries to get proper user's password
     */
    private String getUserPasswordFromBase(int userId) {
        return userList.item(userId).getAttributes().getNamedItem(USER_PASSWORD_ATT).getNodeValue();
    }

    /**
     * Gets and enters new changes attribute of products.xml file.
     *
     * @param productId data entered by the user to get the appropriate selection of values from product.xml file.
     */
    private void takeProductFromStock(int productId) {
        String getInStock = productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock--;
        if (getNewInStock < 0) {
            Communicator.outOfStock();
            System.exit(0);
        }
        productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
        saveChangesInXmlFiles(productSource, PRODUCT_BASE_PATH);
    }

    /**
     * Gets and return value to change attribute of products.xml file.
     *
     * @param productId data entered by the user to get the appropriate selection of values from product.xml file.
     */
    private void returnProductOnStock(int productId) {
        String getInStock = productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock++;
        productList.item(productId).getAttributes().getNamedItem(PRODUCT_IN_STOCK_ATT).setNodeValue(Integer.toString(getNewInStock));
        saveChangesInXmlFiles(productSource, PRODUCT_BASE_PATH);
    }

    public void rentProduct() {
        int productId = Communicator.enterProductId();
        String nameOfRentedProduct = productList.item(productId).getAttributes().getNamedItem(PRODUCT_NAME_ATT).getNodeValue();
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(nameOfLoggedUser)) {
                NodeList getChildNodesFromUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i).getChildNodes();
                System.out.println(getChildNodesFromUser.getLength());
                if (XML_MAX_QUANTITY_OF_PRODUCTS > getChildNodesFromUser.getLength()) {
                    Element products = usersBase.createElement(RENTED_PRODUCT_ATT);
                    tryToFindUser.appendChild(products);
                    Attr attr = usersBase.createAttribute(PRODUCT_NAME_ATT);
                    attr.setValue(nameOfRentedProduct);
                    products.setAttributeNode(attr);
                    takeProductFromStock(productId);
                    saveChangesInXmlFiles(usersSource, USER_BASE_PATH);
                } else {
                    Communicator.rentRequirement();
                    break;
                }
            }
        }
    }

    public void returnRentedProduct() {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(nameOfLoggedUser)) {
                NodeList getChildNodesFromUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i).getChildNodes();
                for (int j = 0; j < getChildNodesFromUser.getLength(); j++) {
                    System.out.println(j + "." + getChildNodesFromUser.item(j).getAttributes().getNamedItem(PRODUCT_NAME_ATT).getTextContent());
                }
                Node getNodeLoggedUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i);
                int productId = Communicator.idProductToReturn();
                Node getSelectedChildNode = getNodeLoggedUser.getChildNodes().item(productId);
                getNodeLoggedUser.removeChild(getSelectedChildNode);
                returnProductOnStock(productId);
                saveChangesInXmlFiles(usersSource, USER_BASE_PATH);
            }
        }
    }

    public void deleteProduct() {
        showAllMovie();
        int productToDelete = Communicator.productToDelete();
        NodeList products = productsBase.getElementsByTagName(PRODUCT_TAG_NAME);
        Node name = products.item(productToDelete);
        name.getParentNode().removeChild(name);
        saveChangesInXmlFiles(productSource, PRODUCT_BASE_PATH);
    }

    /**
     * Allows to create a new user who is required to provide relevant data.<p>
     * In the case of a positive implementation, the data is entered into the appropriate xml file.
     *
     * @param idLengthAsString numerical value(String) of the attribute ID
     */
    private void createNewUser(String idLengthAsString) {
        Element users = usersBase.createElement(USER_TAG_NAME);
        addUserAtt(users, USER_ID_ATT, idLengthAsString);
        addUserAtt(users, USER_NAME_ATT, Communicator.enterLoginField());
        addUserAtt(users, USER_PASSWORD_ATT, Communicator.enterPasswordField());
        addUserAtt(users, ROLE_ATT, RentalProcessor.USER_ROLE);
        saveChangesInXmlFiles(usersSource, USER_BASE_PATH);
    }

    private void addUserAtt(Element users, String attName, String communicator) {
        Attr attr = usersBase.createAttribute(attName);
        attr.setValue(communicator);
        rootUser.appendChild(users);
        users.setAttributeNode(attr);
    }

    /**
     * Works for determine, what ID for new product will be typed in products.xml file.
     *
     * @return ID for new created product
     */
    private String idForNewProduct() {
        int lastProductId = productList.getLength() + 1;
        return Integer.toString(lastProductId);
    }

    public void createNewProduct() {
        Element products = productsBase.createElement(PRODUCT_TAG_NAME);
        addProductAtt(products, PRODUCT_CATEGORY_ATT, Communicator.enterCategory());
        addProductAtt(products, PRODUCT_ID_ATT, idForNewProduct());
        addProductAtt(products, PRODUCT_NAME_ATT, Communicator.enterProductName());
        addProductAtt(products, PRODUCT_COUNT_ATT, Communicator.enterXmlProductCount());
        addProductAtt(products, PRODUCT_REVIEW_ATT, INITIAL_REVIEW_ATT);
        saveChangesInXmlFiles(productSource, PRODUCT_BASE_PATH);
    }

    private void addProductAtt(Element products, String attName, String communicator) {
        Attr attribute = productsBase.createAttribute(attName);
        attribute.setValue(String.valueOf(communicator));
        productsBase.getDocumentElement().appendChild(products);
        products.setAttributeNode(attribute);
    }

    /**
     * Functions as a buffer. Contains information, what role logged user have.
     */
    private String roleOfLoggedUser;

    public String getUserRole() {
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem(USER_NAME_ATT).getTextContent().equalsIgnoreCase(nameOfLoggedUser)) {
                this.roleOfLoggedUser = usersBase.getElementsByTagName(USER_TAG_NAME).item(i).getAttributes().getNamedItem(ROLE_ATT).getTextContent();
                break;
            }
        }
        return roleOfLoggedUser;
    }

    /**
     * Functions as a buffer. Contains information, what name logged user have.
     */
    private String nameOfLoggedUser;

    public void login() {
        boolean temp = true;
        while (temp) {
            String enteredUserLogin = Communicator.enterLoginField();
            for (int x = 0; x < userList.getLength(); x++) {
                if (enteredUserLogin.equals(getUserNameFromBase(x)) && Communicator.enterPasswordField().equals(getUserPasswordFromBase(x))) {
                    Communicator.correctDataInfo();
                    this.nameOfLoggedUser = enteredUserLogin;
                    temp = false;
                    break;
                }
            }
        }
    }

    public void registration() {
        int idLength = userList.getLength();
        idLength++;
        String idLengthAsString = Integer.toString(idLength);
        createNewUser(idLengthAsString);
    }

    public void showAllMovie() {
        for (int i = 0; i < productList.getLength(); i++) {
            productElement = (org.w3c.dom.Element) productList.item(i);
            System.out.println("Product id : " + productElement.getAttribute(PRODUCT_ID_ATT));
            System.out.println("Name : " + productElement.getAttribute(PRODUCT_NAME_ATT));
            System.out.println("Category : " + productElement.getAttribute(PRODUCT_CATEGORY_ATT));
            System.out.println("In stock : " + productElement.getAttribute(PRODUCT_COUNT_ATT));
            System.out.println("---------");
        }
    }
}
