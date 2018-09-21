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

    public void insert(String categoryPath,String productName){

        categoryTree.addCategory(categoryPath);
        Category c=categoryTree.getCategory(categoryPath);

        if(!c.containsProduct(productName)){

            System.out.println("Enter the number of units available: ");
            int count=Integer.parseInt(read.next());
            c.addProduct(new Product(productName,count));
        }
    }

    public void delete(String productPath){


    }
}
