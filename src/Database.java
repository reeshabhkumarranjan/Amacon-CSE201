import java.util.Scanner;

public final class Database {

    private final CategoryTree categoryTree;
    private final Scanner read;
    private int revenue;

    public Database() {
        categoryTree=new CategoryTree();
        read=new Scanner(System.in);
        revenue=0;
    }

    public int getRevenue() {
        return revenue;
    }

    public void insertProduct(String categoryPath, String productName) throws ProductAlreadyExistsException {

        categoryTree.addCategory(categoryPath);
        Category c=categoryTree.getCategory(categoryPath);

        if(!c.containsProduct(productName)){

            int count= 0;
            double price= 0;
            try {
                System.out.println("Enter the number of units available: ");
                count = IO.nextInt();
                System.out.println("Enter the price: ");
                price = IO.nextDouble();
            } catch (Exception e) {
                IO.println("Invalid input! Canceling the operation.");
                return;
            }

            c.addProduct(new Product(productName,count,price));
        }

        else{
            throw new ProductAlreadyExistsException("Product is already in the database!");
        }
    }

    public void delete(String path) throws InvalidPathException {

        String categoryPath;
        String productName;

        if(path.contains(">")){
            int splitIndex=path.lastIndexOf('>');
            categoryPath=path.substring(0,splitIndex);
            productName=path.substring(splitIndex+1);
        }

        else{
            categoryPath="";
            productName=path;
        }

        Category c=categoryTree.getCategory(categoryPath);

        if(c==null){
            throw new InvalidPathException("The provided path is invalid!");
        }

        if(c.containsProduct(productName)){

            c.deleteProduct(productName);
        }

        else if (c.containsSubCategory(productName)){

            c.deleteSubCategory(productName);
        }

        else{
            throw new InvalidPathException("The provided path is invalid!");
        }
    }

    public Product searchProduct(String name,boolean showPath) throws ProductNotFoundException {

        Product p=null;

        p=categoryTree.searchProduct(name,showPath);

        if(p==null){
            throw new ProductNotFoundException("Product does not exist in the database!");
        }

        return p;
    }

    public void modifyProduct(String name){

        Product p= null;
        try {
            p = searchProduct(name,false);
        } catch (ProductNotFoundException e) {
            IO.println(e.getMessage());
            return;
        }

        double price= 0;
        int count= 0;
        try {
            System.out.println("Enter new price: ");
            price = Double.parseDouble(read.next());
            System.out.println("Enter new quantity: ");
            count = IO.nextInt();
        } catch (NumberFormatException e) {
            IO.println("Invalid input! Canceling the operation.");
            return;
        }

        p.setNumberCount(count);
        p.setPrice(price);
    }

    public void sale(Product p, int qty, Double fundsRemaining) throws FundsInsufficientException {

        if(p.getNumberCount()>=qty && fundsRemaining>=qty*p.getPrice()){

            p.setNumberCount(p.getNumberCount()-qty);
            this.revenue+=qty*p.getPrice();
        }

        else{
            throw new FundsInsufficientException("Funds are insufficient!");
        }
    }
}
