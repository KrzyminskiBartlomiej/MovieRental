import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class RentalProcessor {
    public static void main(String[] args) throws Exception {
        Login login = new Login();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory
                .newDocumentBuilder();
        Document productsBase = documentBuilder.parse("C:/Project MR/MovieRental/resources/products.xml");
        Document usersBase = documentBuilder.parse("C:/Project MR/MovieRental/resources/users.xml");
        Element rootUser = usersBase.getDocumentElement();
        Element rootElementUser = usersBase.getDocumentElement();

        switch (Communicator.selectAuthorization()) {
            case 1:
                login.login(usersBase);
                Panel.runPanel(productsBase, usersBase);
                break;
            case 2:
                Registration.registration(usersBase, rootElementUser, rootUser);
                break;
        }
    }
}