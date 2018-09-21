import java.util.ArrayList;

public final class Category {

    private final String name;
    private ArrayList<Product> productList;
    private ArrayList<Category> subCatList;

    public Category(String name) {
        this.name = name;
        productList=new ArrayList<>();
        subCatList=new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Product getProduct(String name){

        for(Product p:productList){

            if(p.getName().equals(name)){
                return p;
            }
        }

        return null;
    }

    public Category getSubCategory(String name){

        for(Category c:subCatList){

            if(c.getName().equals(name)){
                return c;
            }
        }

        return null;
    }

    public boolean containsSubCategory(String name){

        for(Category c:subCatList){

            if(c.getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    public boolean containsProduct(String name){

        for(Product p:productList){

            if(p.getName().equals(name)){
                return true;
            }
        }

        return false;
    }

    public void addSubCategory(String name){

        subCatList.add(new Category(name));
    }

    public void addProduct(Product p){

        productList.add(p);
    }
}
