import java.util.Scanner;

public class Communicator {

    private String login;
    private String password;
    private int selectedProduct;
    private int selectedOption;

    Scanner scanner = new Scanner(System.in);

    void userInput() {
        System.out.println("Enter your login");
        login = scanner.nextLine();
        System.out.println("Enter your password");
        password = scanner.nextLine();
    }

    void chooseProductId() {
        System.out.println("Which product are you interested?");
        selectedProduct = scanner.nextInt();
    }

    void checkOption(){
        selectedOption = scanner.nextInt();
    }


    String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    int getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(int selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public int getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(int selectedOption) {
        this.selectedOption = selectedOption;
    }

}
