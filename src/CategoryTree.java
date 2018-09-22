public class CategoryTree {

    private Category root;

    public CategoryTree() {

        root=new Category("");
    }

    public Category getCategory(String categoryPath){

        Category c=root;
        String[] categoryPathValues=categoryPath.split(">");

        for(String s:categoryPathValues){

            c=c.getSubCategory(s);

            if(c==null){
                return null;
            }
        }

        return c;
    }

    public void addCategory(String categoryPath) {

        Category c=root;
        String[] categoryPathValues=categoryPath.split(">");

        for(String s:categoryPathValues){

            if(!c.containsSubCategory(s)){
                c.addSubCategory(s);
            }

            c=c.getSubCategory(s);
        }
    }

    public Product searchProduct(String productName,boolean showPath){

        //String path="";
        PathString path=new PathString();
        //Product p=null;
        ProductReference p=new ProductReference();

        root.searchProduct(productName,path,p);

        if(showPath){
            System.out.println(path);
        }

        return p.getProduct();
    }
}
