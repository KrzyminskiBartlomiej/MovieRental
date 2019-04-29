import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

class Login {
    static String nameLoggedUser = "";

    void login(Document document) {
        NodeList nodeList = document.getElementsByTagName("user");
        String passwordValue;
        label:
        while (true) {
            String log = Communicator.loginField();
            String pass = Communicator.passwordField();
            for (int x = 0; x < nodeList.getLength(); x++) {
                String name = nodeList.item(x).getAttributes().getNamedItem("name").getNodeValue();
                passwordValue = nodeList.item(x).getAttributes().getNamedItem("password").getNodeValue();
                if (log.equals(name) && pass.equals(passwordValue)) {
                    Communicator.correctDataInfo();
                    nameLoggedUser = log;
                    break label;
                }
            }
            Communicator.wrongDataInfo();
        }
    }

    String getNameLoggedUser() {
        return nameLoggedUser;
    }


}