import java.util.*;

class Panel {
    private Communicator communicator = new Communicator();
    void userPanel(List<Products> productsList) {
        boolean exit = false;
        do {
            System.out.println("1.[Select Products] 2.[Reservations] 3.[Logout]");
            communicator.checkOption();

            switch (communicator.getSelectedOption()) {
                case 1: {
                    Informer.showProducts(productsList);
                    Panel.selectProduct(productsList);
                    break;
                }
                case 2: {
                    Informer.showReservations(productsList);
                    break;
                }
                case 3: {
                    exit = true;
                    break;
                }
            }
        } while (!exit);
    }

    static private void selectProduct(List<Products> productsList) {
        Communicator communicate = new Communicator();
        communicate.chooseProductId();
        productsList.stream()
                .filter(p -> p.getId() == communicate.getSelectedProduct()).filter(p -> !p.getIsInstock());

        System.out.println("Great! - product was added to ur reservations");
    }

}