import java.util.HashSet;
import java.util.Scanner;

public class Database {

    private CategoryTree categoryTree;
    //HashSet<String> productSet;
    private Scanner read;
    private int revenue;

    public Database() {
        categoryTree=new CategoryTree();
        //productSet=new HashSet<>();
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

            System.out.println("Enter the number of units available: ");
            int count=Integer.parseInt(read.next());
            System.out.println("Enter the price: ");
            double price=Double.parseDouble(read.next());

//            int count=10;
//            double price=50;
            c.addProduct(new Product(productName,count,price));
        }

        else{
            throw new ProductAlreadyExistsException("Product is already in the database!");
        }
    }

    public void delete(String path) throws InvalidPathException {

        int splitIndex=path.lastIndexOf('>');
        String categoryPath=path.substring(0,splitIndex);
        String productName=path.substring(splitIndex+1);

        Category c=categoryTree.getCategory(categoryPath);

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

        System.out.println("Enter new price: ");
        double price=Double.parseDouble(read.next());
        System.out.println("Enter new quantity: ");
        int count=Integer.parseInt(read.next());

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
