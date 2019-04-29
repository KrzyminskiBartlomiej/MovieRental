import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

class Informer {
    static void showProducts(Document productBase) {
        NodeList productList = productBase.getElementsByTagName("product");

        for (int i = 0; i < productList.getLength(); i++) {
            Node product = productList.item(i);
            Element element = (Element) product;
            System.out.println("Product id : " + element.getAttribute("productId"));
            System.out.println("Name : " + element.getAttribute("name"));
            System.out.println("Category : " + element.getAttribute("category"));
            System.out.println("In stock : " + element.getAttribute("inStock"));
            System.out.println("---------");
        }
    }
}