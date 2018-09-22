public class Product {

    private final String name;
    private int numberCount;
    private double price;

    public Product(String name, int numberCount, double price) {
        this.name = name;
        this.numberCount = numberCount;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public int getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(int numberCount) {
        this.numberCount = numberCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
