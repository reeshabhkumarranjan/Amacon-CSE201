import java.util.ArrayList;

final class Item{

    private Product product;
    private int qty;

    public Item(Product product, int qty) {
        this.product = product;
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }
}

public final class Cart {

    private ArrayList<Item> itemList;
    private final Database d;
    private Customer c;

    public Cart(Database d) {

        itemList=new ArrayList<>();
        this.d=d;
        //this.c=c;
    }

    public void addProduct(String name,int qty){

        Product p= null;
        try {
            p = d.searchProduct(name,false);
        } catch (ProductNotFoundException e) {
            IO.println(e.getMessage());
            return;
        }
        itemList.add(new Item(p,qty));
    }

    public void setC(Customer c) {
        this.c = c;
    }

    public void checkOut(){

        for(Item i:itemList){

            try {
                d.sale(i.getProduct(),i.getQty(),c.getFunds());
            } catch (FundsInsufficientException e) {
                IO.println(e.getMessage());
                return;
            }
            c.setFunds(c.getFunds()-i.getQty()*i.getProduct().getPrice());
        }
    }
}
