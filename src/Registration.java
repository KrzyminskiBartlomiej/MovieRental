import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

class Registration {
    static void registration(Document usersBase, Element rootElementUser, Element rootUser) {
        NodeList userList = usersBase.getElementsByTagName("user");
        int idLength = userList.getLength();
        idLength++;
        String idLengthAsString = Integer.toString(idLength);

        org.w3c.dom.Element account = usersBase.createElement("user");
        Attr attr = usersBase.createAttribute("id");
        attr.setValue(idLengthAsString);
        rootElementUser.appendChild(account);
        account.setAttributeNode(attr);

        Attr attrLogin = usersBase.createAttribute("name");
        attrLogin.setValue(Communicator.loginField());
        rootElementUser.appendChild(account);
        account.setAttributeNode(attrLogin);

        Attr attrPassword = usersBase.createAttribute("password");
        attrPassword.setValue(Communicator.passwordField());
        rootElementUser.appendChild(account);
        account.setAttributeNode(attrPassword);
        rootUser.appendChild(account);

        XmlWorker.saveChangesInXmlUsersFiles(usersBase);
    }
}