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
}
