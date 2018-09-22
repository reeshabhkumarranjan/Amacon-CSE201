import java.util.HashSet;
import java.util.Scanner;

public class Database {

    private CategoryTree categoryTree;
    HashSet<String> productSet;
    Scanner read;

    public Database() {
        categoryTree=new CategoryTree();
        productSet=new HashSet<>();
        read=new Scanner(System.in);
    }

    public void insertProduct(String categoryPath,String productName){

        categoryTree.addCategory(categoryPath);
        Category c=categoryTree.getCategory(categoryPath);

        if(!c.containsProduct(productName)){

            System.out.println("Enter the number of units available: ");
            int count=Integer.parseInt(read.next());
            System.out.println("Enter the price: ");
            double price=Double.parseDouble(read.next());
            c.addProduct(new Product(productName,count,price));
        }
    }

    public void delete(String path){

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
    }

    public Product searchProduct(String name,boolean showPath){

        Product p=null;

        p=categoryTree.searchProduct(name,showPath);

        return p;
    }

    public void modifyProduct(String name){

        Product p=searchProduct(name,false);

        System.out.println("Enter new price: ");
        double price=Double.parseDouble(read.next());
        System.out.println("Enter new quantity: ");
        int count=Integer.parseInt(read.next());

        p.setNumberCount(count);
        p.setPrice(price);
    }

    public void sale(Product p, int qty, double fundsRemaining){

        if(p.getNumberCount()>=qty && fundsRemaining>=qty*p.getPrice()){

            p.setNumberCount(p.getNumberCount()-qty);
        }
    }
}
