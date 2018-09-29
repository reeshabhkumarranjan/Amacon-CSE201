import java.io.Serializable;

public final class Product implements Serializable {

    private final String name;
    private int numberCount;
    private double price;

    public Product(String name, int numberCount, double price) {
        this.name = name;
        this.numberCount = numberCount;
        this.price = price;
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

    @Override
    public String toString() {
        return this.getName();
    }

    public void getDetails(){

        IO.println("name: "+this.name);
        IO.println("count: "+this.numberCount);
        IO.println("price: "+this.price);
    }
}
