import org.w3c.dom.*;

class Panel {
    private static int userInputSelected;

    private static void rentProduct(Document productBase, Document usersBase) {
        NodeList productList = productBase.getElementsByTagName("product");
        NodeList userList = usersBase.getElementsByTagName("user");
        int userInput = Communicator.selectProduct();
        userInputSelected = userInput;
        //deleted product from stock
        String getInStock = productList.item(userInput).getAttributes().getNamedItem("inStock").getNodeValue();
        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock--;
        if (getNewInStock < 0) {
            Communicator.outOfStock();
            System.exit(0);
        }
        productList.item(userInput).getAttributes().getNamedItem("inStock").setNodeValue(Integer.toString(getNewInStock));
        //appended product in user node
        String nameOfRentedProduct = productList.item(userInput).getAttributes().getNamedItem("name").getNodeValue();
        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem("name").getTextContent().equalsIgnoreCase(Login.nameLoggedUser)) {
                ((Element) tryToFindUser).setAttribute("rented", nameOfRentedProduct);
            }
        }
        //accepted this change - transfer for product and user bases.
        XmlWorker.saveChangesInXmlFiles(productBase, usersBase);
        Communicator.successfullyRented();
    }

    private static void returnRentedProduct(Document productBase, Document usersBase) {

        //appended +1 to stock = return product
        NodeList productList = productBase.getElementsByTagName("product");
        NodeList userList = usersBase.getElementsByTagName("user");
        String getInStock = productList.item(getUserInputSelected()).getAttributes().getNamedItem("inStock").getNodeValue();

        int getNewInStock = Integer.parseInt(getInStock);
        getNewInStock++;
        productList.item(getUserInputSelected()).getAttributes().getNamedItem("inStock").setNodeValue(Integer.toString(getNewInStock));
        //deleted node from user node

        for (int i = 0; i < userList.getLength(); i++) {
            Node tryToFindUser = userList.item(i);
            if (tryToFindUser.getAttributes().getNamedItem("name").getTextContent().equalsIgnoreCase(Login.nameLoggedUser)) {
                ((Element) tryToFindUser).setAttribute("rented", null);
            }
        }
        //accepted this change - transfer product/user bases.
        XmlWorker.saveChangesInXmlFiles(productBase, usersBase);
        Communicator.successfullyReturnProduct();
    }

    static void runPanel(Document productBase, Document usersBase) {
        boolean exit = false;
        do {
            Communicator.rentRequirement();
            switch (Communicator.selectPanelOptions()) {
                case 1: {
                    Informer.showProducts(productBase);
                    rentProduct(productBase, usersBase);
                    break;
                }
                case 2: {
                    returnRentedProduct(productBase, usersBase);
                    break;
                }
                case 3: {
                    Informer.showProducts(productBase);
                    break;
                }
                case 4: {
                    exit = true;
                    break;
                }
            }
        } while (!exit);
    }

    private static int getUserInputSelected() {
        return userInputSelected;
    }

    public static void setUserInputSelected(int userInputSelected) {
        Panel.userInputSelected = userInputSelected;
    }
}