import java.util.List;

public class Products {

    private int id;
    private String name;
    private int rentalPrice;
    private String category;
    private int publishmentYear;
    private boolean instock;


    Products(int id, String name, int rentalPrice, String category, int publishmentYear, boolean instock) {
        this.name = name;
        this.rentalPrice = rentalPrice;
        this.category = category;
        this.publishmentYear = publishmentYear;
        this.instock = instock;
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPublishmentYear() {
        return publishmentYear;
    }

    public void setPublishmentYear(int publishmentYear) {
        this.publishmentYear = publishmentYear;
    }

    public boolean getIsInstock() {
        return instock;
    }

    public void setInStock(boolean instock) {
        this.instock = instock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Products{" +
                "Name=" + name + " " +
                "RentalPrice=" + rentalPrice + " " +
                "Category=" + category + " " +
                "PublismentYear=" + publishmentYear + " " +
                "InStock=" + instock + "}";
    }
}


