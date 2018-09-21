public class Product {

    private final String name;
    private int numberCount;

    public Product(String name, int numberCount) {
        this.name = name;
        this.numberCount = numberCount;
    }

    public String getName() {
        return name;
    }

    public int getNumberCount() {
        return numberCount;
    }

}
