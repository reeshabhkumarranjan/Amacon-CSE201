public class Customer implements User{

    private double funds;
    Cart c;

    public Customer(Cart c) {
        //this.funds = funds;
        this.c=c;
        c.setC(this);
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void addProduct(String productName, int qty){

        c.addProduct(productName,qty);
    }

    public void checkOut(){
        c.checkOut();
    }

    @Override
    public void runSession() {
        IO.println("Logged in as a Customer.");
        boolean flag=true;
        while(flag){
            showMenu();
            int choice= IO.nextInt();

            switch (choice){

                case 1:
                    IO.println("Enter funds: ");
                    double funds=IO.nextDouble();
                    this.setFunds(funds);
                    break;

                case 2:
                    IO.println("Enter product name: ");
                    String name=IO.next();
                    IO.println("Enter quantity: ");
                    int qty=IO.nextInt();
                    this.addProduct(name,qty);
                    break;

                case 3:
                    IO.println("Checking out...");
                    this.checkOut();
                    break;

                case 4:
                    IO.println("Exiting...");
                    flag=false;
                    break;

                default:
                    IO.println("Invalid input. Try again.");
            }
        }
    }

    @Override
    public void showMenu() {

        IO.println("1. Add funds.");
        IO.println("2. Add products to cart.");
        IO.println("3. Check-out cart.");
        IO.println("4. Exit as Customer.");
    }
}