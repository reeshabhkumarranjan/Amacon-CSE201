import java.util.ArrayList;

class Item{

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

public class Cart {

    private ArrayList<Item> itemList;
    private Database d;
    private Customer c;

    public Cart(Database d) {

        itemList=new ArrayList<>();
        this.d=d;
        //this.c=c;
    }

    public void addProduct(String name,int qty){

        Product p=d.searchProduct(name,false);
        itemList.add(new Item(p,qty));
    }

    public void setC(Customer c) {
        this.c = c;
    }

    public void checkOut(){

        for(Item i:itemList){

            d.sale(i.getProduct(),i.getQty(),c.getFunds());
            c.setFunds(c.getFunds()-i.getQty()*i.getProduct().getPrice());
        }
    }
}
