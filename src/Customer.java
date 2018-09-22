public class Customer {

    private double funds;
    Cart c;

    public Customer(double funds, Cart c) {
        this.funds = funds;
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
}
