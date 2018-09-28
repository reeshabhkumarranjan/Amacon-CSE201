import java.io.Serializable;
import java.util.ArrayList;

public final class Category implements Serializable {

    private final String name;
    private ArrayList<Product> productList;
    private ArrayList<Category> subCatList;

    public Category(String name) {
        this.name = name;
        productList = new ArrayList<>();
        subCatList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Product getProduct(String name) {

        for (Product p : productList) {

            if (p.getName().equals(name)) {
                return p;
            }
        }

        return null;
    }

    public Category getSubCategory(String name) {

        for (Category c : subCatList) {

            if (c.getName().equals(name)) {
                return c;
            }
        }

        return null;
    }

    public boolean containsSubCategory(String name) {

        for (Category c : subCatList) {

            if (c.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsProduct(String name) {

        for (Product p : productList) {

            if (p.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public void addSubCategory(String name) {

        subCatList.add(new Category(name));
    }

    public void addProduct(Product p) {

        productList.add(p);
    }

    public void deleteProduct(String name) {

        for (int i = 0; i < productList.size(); i++) {

            if (productList.get(i).getName().equals(name)) {
                productList.remove(i);
                break;
            }
        }
    }

    public void deleteSubCategory(String name) {

        for (int i = 0; i < subCatList.size(); i++) {

            if (subCatList.get(i).getName().equals(name)) {
                subCatList.get(i).resetSubCategoryList();
                subCatList.get(i).resetProductList();
            }
        }
    }

    public boolean searchProduct(String productName, PathString path, ProductReference product) {

        if (this.containsProduct(productName)) {

            path.appendRight(this.getName() + ">" + this.getProduct(productName).getName());
            product.setProduct(this.getProduct(productName));
            return true;
        } else {

            boolean b = false;

            for (Category c : subCatList) {

                b = b || c.searchProduct(productName, path, product);
            }

            if (b) {

                if (this.getName() == "") {
                    return true;
                }

                path.appendLeft(this.getName() + ">");
                return true;
            }
        }

        return false;
    }

    public void resetSubCategoryList() {
        this.subCatList = new ArrayList<>();
    }

    public void resetProductList() {
        this.productList = new ArrayList<>();
    }
}
