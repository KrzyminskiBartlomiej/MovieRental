import java.util.List;

public class Informer {
    RentalProcessor processor = new RentalProcessor();


    static void showReservations(List<Products> productsList){

        productsList.stream().

                filter(e -> !e.getIsInstock()).forEach(System.out::println);
    }
    static void showProducts(List<Products> productsList){
        productsList.stream().
                filter(e -> e.getIsInstock()).forEach(System.out::println);
    }

}
