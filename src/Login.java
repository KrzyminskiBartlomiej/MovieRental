import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

class Login {
    static private Communicator communicator = new Communicator();

    void login(String path) throws IOException {
        Optional<String> hasLogin;
        Optional<String> hasPassword;
        do{
            communicator.userInput();
            Stream<String> linesLogin = Files.lines(Paths.get(path));
            Stream<String> linesPassword = Files.lines(Paths.get(path));
            hasLogin = linesLogin.filter(s -> s.contains(communicator.getLogin())).findFirst();
            hasPassword=linesPassword.filter(s -> s.contains(communicator.getPassword())).findFirst();
        }
        while (!hasLogin.isPresent() || !hasPassword.isPresent());
        System.out.println("Correct data.");





    }


}
