public class Administrator implements User {

    Database d;

    public Administrator(Database d) {
        this.d = d;
    }


    @Override
    public void runSession() {

        IO.println("Logged in as an Administrator.");
        boolean flag=true;

        while(flag){
            showMenu();
            int choice =IO.nextInt();

            switch (choice){

                case 1:
                    IO.println("Enter path of category: ");
                    String catPath=IO.next();
                    IO.println("Enter product name");
                    String prodName=IO.next();
                    d.insertProduct(catPath,prodName);
                    break;

                case 2:
                    IO.println("Enter the complete path of the product/category to be deleted: ");
                    String path=IO.next();
                    d.delete(path);
                    break;

                case 3:
                    IO.println("Enter the name of the product: ");
                    prodName=IO.next();
                    d.searchProduct(prodName,true);
                    break;

                case 4:
                    IO.println("Enter the name of the product: ");
                    prodName=IO.next();
                    d.modifyProduct(prodName);
                    break;

                case 5:
                    IO.println("Exiting...");
                    flag=false;
                    break;

                default:
                    IO.println(d.searchProduct("Oneplus",true));
                    IO.println("Invalid input. Try again.");
            }
        }
    }

    @Override
    public void showMenu() {

        IO.println("1. Insert product/category.");
        IO.println("2. Delete product/category.");
        IO.println("3. Search product.");
        IO.println("4. Modify product.");
        IO.println("5. Exit as Administrator.");
    }
}
