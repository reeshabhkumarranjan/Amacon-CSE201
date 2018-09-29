import java.io.*;
import java.util.Scanner;

public final class Database implements Serializable {

    //TODO add serialize method here.

    private final CategoryTree categoryTree;
    private final transient Scanner read;
    private int revenue;

    public Database() {
        categoryTree = new CategoryTree();
        read = new Scanner(System.in);
        revenue = 0;
    }

    public static Database deserialize() throws IOException, ClassNotFoundException {

        ObjectInputStream in = null;
        String fileName = "data/database/" + "database" + ".txt";

//        File file=new File(fileName);
//
//        if(!file.exists()){
//            file.createNewFile();
//        }

        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
            Database database = (Database) in.readObject();
            return database;
        } finally {
            in.close();
        }

        //return null;
    }

    public Database update() {

        try {
            return deserialize();
        } catch (IOException e) {
            return new Database();
        } catch (ClassNotFoundException e) {
            return new Database();
        } catch (NullPointerException e) {
            return new Database();
        }
    }

    public void serialize() throws IOException {

        ObjectOutputStream out = null;

        try {
            String fileName = "data/database/" + "database" + ".txt";
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            out = new ObjectOutputStream(new FileOutputStream(fileName, false));
            out.writeObject(this);
        } finally {
            out.close();
        }
    }

    public int getRevenue() {
        return revenue;
    }

    public void insertProduct(String categoryPath, String productName) throws ProductAlreadyExistsException {

        categoryTree.addCategory(categoryPath);
        Category c = categoryTree.getCategory(categoryPath);

        if (!c.containsProduct(productName)) {

            int count = 0;
            double price = 0;
            try {
                System.out.println("Enter the number of units available: ");
                count = IO.nextInt();
                System.out.println("Enter the price: ");
                price = IO.nextDouble();
            } catch (Exception e) {
                IO.println("Invalid input! Canceling the operation.");
                return;
            }

            c.addProduct(new Product(productName, count, price));
        } else {
            throw new ProductAlreadyExistsException("Product is already in the database!");
        }
    }

    public void delete(String path) throws InvalidPathException {

        String categoryPath;
        String productName;

        if (path.contains(">")) {
            int splitIndex = path.lastIndexOf('>');
            categoryPath = path.substring(0, splitIndex);
            productName = path.substring(splitIndex + 1);
        } else {
            categoryPath = "";
            productName = path;
        }

        Category c = categoryTree.getCategory(categoryPath);

        if (c == null) {
            throw new InvalidPathException("The provided path is invalid!");
        }

        if (c.containsProduct(productName)) {

            c.deleteProduct(productName);
        } else if (c.containsSubCategory(productName)) {

            c.deleteSubCategory(productName);
        } else {
            throw new InvalidPathException("The provided path is invalid!");
        }
    }

    public Product searchProduct(String name, boolean showPath) throws ProductNotFoundException {

        Product p = null;

        p = categoryTree.searchProduct(name, showPath);

        if (p == null) {
            throw new ProductNotFoundException("Product does not exist in the database!");
        }

        return p;
    }

    public void modifyProduct(String name) {

        Product p = null;
        try {
            p = searchProduct(name, false);
        } catch (ProductNotFoundException e) {
            IO.println(e.getMessage());
            return;
        }

        double price = 0;
        int count = 0;
        try {
            System.out.println("Enter new price: ");
            //price = Double.parseDouble(read.next());
            price = IO.nextDouble();
            System.out.println("Enter new quantity: ");
            count = IO.nextInt();
        } catch (NumberFormatException e) {
            IO.println("Invalid input! Canceling the operation.");
            return;
        }

        p.setNumberCount(count);
        p.setPrice(price);
    }

    public void sale(Product p, int qty, Double fundsRemaining) throws FundsInsufficientException, StockInsufficientException {

        try {
            p=this.searchProduct(p.getName(),false);
        } catch (ProductNotFoundException e) {
            e.printStackTrace();
        }

        if (p.getNumberCount() < qty) {
            throw new StockInsufficientException("Available quantity in the database for " + p + " is less than required.");
        }

        if (fundsRemaining < qty * p.getPrice()) {
            throw new FundsInsufficientException("Funds are insufficient!");
        }

        p.setNumberCount(p.getNumberCount() - qty);
        this.revenue += qty * p.getPrice();
        //p.getDetails();

    }
}
