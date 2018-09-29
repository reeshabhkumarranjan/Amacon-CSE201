import java.io.*;

public final class Customer implements User, Serializable {

    private final transient Cart c;
    private double funds;
    private String username;

    public Customer(Cart c) {
        this.c = c;
        c.setC(this);
    }

    public void save() throws IOException {

        ObjectOutputStream out = null;

        try {
            String fileName = "data/customer/" + this.username + ".txt";
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            out = new ObjectOutputStream(new FileOutputStream(fileName, false));
            out.writeObject(this);
        } finally {
            out.close();
        }
    }

    public static Customer deserialize(String username) throws IOException, ClassNotFoundException {

        ObjectInputStream in = null;
        String fileName = "data/customer/" + username + ".txt";

        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
            Customer customer = (Customer) in.readObject();
            return customer;
        } finally {
            in.close();
        }
    }

    public double getFunds() {
        return funds;
    }

    public void setFunds(double funds) {
        this.funds = funds;
    }

    public void addProduct(String productName, int qty) {

        c.addProduct(productName, qty);
    }

    public void checkOut() {
        c.checkOut();
    }

    @Override
    public void runSession() {
        IO.println("Logged in as a Customer.");
        boolean flag = true;
        while (flag) {
            showMenu();
            int choice = 0;
            try {
                choice = IO.nextInt();
            } catch (Exception e) {
                IO.println("Invalid input! Canceling the operation.");
                continue;
            }

            switch (choice) {

                case 1:
                    IO.println("Enter funds: ");
                    double funds = 0;
                    try {
                        funds = IO.nextDouble();
                    } catch (Exception e) {
                        IO.println("Invalid input! Canceling the operation.");
                        break;
                    }
                    this.setFunds(this.getFunds() + funds);
                    break;

                case 2:
                    IO.println("Enter product name: ");
                    String name = IO.next();
                    IO.println("Enter quantity: ");
                    int qty = 0;
                    try {
                        qty = IO.nextInt();
                    } catch (Exception e) {
                        IO.println("Invalid input! Canceling the operation.");
                        break;
                    }
                    this.addProduct(name, qty);
                    break;

                case 3:
                    IO.println("Checking out...");
                    this.checkOut();
                    break;

                case 4:
                    IO.println("Exiting...");
                    flag = false;
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