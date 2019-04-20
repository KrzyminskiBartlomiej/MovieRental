import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class RentalProcessor {

    public static void main(String[] arg) throws IOException {
        Login tryToLog = new Login();
        Registration tryToReg = new Registration();
        Communicator communicator = new Communicator();
        Panel panel = new Panel();

        String usersBasePath = "users.txt";
        PrintWriter usersFile = new PrintWriter(new FileWriter(usersBasePath, true));

        List<Products> productsList = Arrays.asList(
                new Products(1, "Warcraft", 19, "Fantasy", 2016, true),
                new Products(2, "Hobbit", 12, "Fantasy", 2012, true),
                new Products(3, "Joker", 16, "Fantasy", 2019, true)
        );
        System.out.println("1.[Login] 2.[Registration]");
        communicator.checkOption();
        switch (communicator.getSelectedOption()) {
            case 1: {
                tryToLog.login(usersBasePath);
               panel.userPanel(productsList);
                break;
            }
            case 2: {
                tryToReg.registration(usersFile);
                break;
            }
        }


    }
}
