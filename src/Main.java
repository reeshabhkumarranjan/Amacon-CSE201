public class Main {

    public static void main(String[] args) {
	// write your code here
//	    System.out.println("Hello world!");
//	    System.out.println("Update is working as well!");

	    Database d=new Database();
	    d.insertProduct("Electronics>smartphone","Oneplus");
	    d.insertProduct("Electronics>smartphone","Motorola");
        d.insertProduct("Grocery>soap","Lifebuoy");
	    d.searchProduct("Oneplus",true);
	    d.searchProduct("Motorola",true);
        d.searchProduct("Lifebuoy",true);

        d.delete("Electronics>smartphone>Oneplus");
        d.delete("Electronics>smartphone>Motorola");

        d.searchProduct("Oneplus",true);
        d.searchProduct("Motorola",true);
        d.searchProduct("Lifebuoy",true);
    }
}