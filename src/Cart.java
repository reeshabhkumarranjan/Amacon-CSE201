import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

final class Item implements Serializable {

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

final class ItemListComparator implements Comparator<Item>, Serializable {

    @Override
    public int compare(Item o1, Item o2) {

        if (o1.getProduct().getPrice() * o1.getQty() > o2.getProduct().getPrice() * o2.getQty()) {
            return 1;
        } else if (o1.getProduct().getPrice() * o1.getQty() == o2.getProduct().getPrice() * o2.getQty()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

public final class Cart implements Serializable {

    private transient Database d;
    private ArrayList<Item> itemList;
    private Customer c;

    public Cart(Database d) {

        itemList = new ArrayList<>();
        this.d = d;
    }

    public void setDatabase(Database d){
        this.d=d;
    }

    public void addProduct(String name, int qty) {

        Product p = null;
        try {
            p = d.searchProduct(name, false);
        } catch (ProductNotFoundException e) {
            IO.println(e.getMessage());
            return;
        }
        itemList.add(new Item(p, qty));
    }

    public void setC(Customer c) {
        this.c = c;
    }

    public void checkOut() {

        Collections.sort(itemList, new ItemListComparator());
        ArrayList<Item> checkedOut = new ArrayList<>();

        for (Item i : itemList) {

            try {
                d.sale(i.getProduct(), i.getQty(), c.getFunds());
                c.setFunds(c.getFunds() - i.getQty() * i.getProduct().getPrice());
                checkedOut.add(i);
            } catch (FundsInsufficientException e) {
                IO.println(e.getMessage());

                if (checkedOut.size() > 0) {
                    IO.println("However, the top products adjustable in the budget have been checked out. Add more funds to check out the remaining items.");
                }

                for (Item ic : checkedOut) {
                    itemList.remove(ic);
                }

                return;
            } catch (StockInsufficientException e) {
                IO.println(e.getMessage());

            }
        }
    }
}
