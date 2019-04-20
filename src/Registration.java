import java.io.PrintWriter;
import java.util.Scanner;

class Registration{
    private Communicator communicator = new Communicator();

    void registration(PrintWriter usersFile) {
        usersFile.println(communicator.getLogin());
        usersFile.println(communicator.getPassword());
        usersFile.close();
    }


}
