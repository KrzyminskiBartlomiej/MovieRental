import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

class XmlWorker {
    static void saveChangesInXmlFiles(Document productBase, Document usersBase) {
        DOMSource productSource = new DOMSource(productBase);
        DOMSource usersSource = new DOMSource(usersBase);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult usersResult = new StreamResult("C:/Project MR/MovieRental/resources/users.xml");
            StreamResult productsResult = new StreamResult("C:/Project MR/MovieRental/resources/products.xml");
            transformer.transform(usersSource, usersResult);
            transformer.transform(productSource, productsResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    static void saveChangesInXmlUsersFiles(Document usersBase) {
        DOMSource usersSource = new DOMSource(usersBase);
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult usersResult = new StreamResult("C:/Project MR/MovieRental/resources/users.xml");
            transformer.transform(usersSource, usersResult);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}