import java.util.Scanner;

class Communicator {

    static String loginField() {
        System.out.println("Enter login:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static String passwordField() {
        System.out.println("Enter password:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    static int selectAuthorization() {
        System.out.println("Choose options: [1].Login [2].Registration");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static int selectPanelOptions() {
        System.out.println("Choose options: [1].Rent movie [2].Return movie [3].Show product [4].Exit");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static int selectProduct() {
        System.out.println("Please enter id of product:");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    static void correctDataInfo() {
        System.out.println("Correct data! Welcome to your account!");
    }

    static void wrongDataInfo() {
        System.out.println("Wrong data! Try it again:");
    }

    static void outOfStock() {
        System.out.println("Remember - sometimes products can be out of stock. Try next time. Bye!");
    }

    static void successfullyRented() {
        System.out.println("Thank you for using our services!");
    }

    static void successfullyReturnProduct() {
        System.out.println("Thank you for returning this product!");
    }

    static void rentRequirement() {
        System.out.println("Remember! You can rent only 1 product. - You need to return current product, before you would like to get new one.");
    }
}