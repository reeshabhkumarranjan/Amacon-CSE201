import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class InputTest {

    private static InputStream consoleIn; // Saves the console's input stream.
    private static PrintStream consoleOut; // Saves the console's output stream.
    private static ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeClass
    public static void setUp() {
        consoleIn = System.in;
        System.setIn(consoleIn);
        IO.resetScanner(consoleIn);

        consoleOut = System.out;

        // To suppress console's output.

        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));
    }

    @AfterClass
    public static void tearDown() {

        // To reset input stream to console input.

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }

    private String giveInput(Object o) {

        return o.toString() + "\n";
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void insertSameProduct() throws ProductAlreadyExistsException {

        // To re-route console's input stream to custom input from string.

        ByteArrayInputStream in = new ByteArrayInputStream((giveInput(1) + giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database = new Database();
        database.insertProduct("a>b", "c");
        database.insertProduct("a>b", "c");


    }

    @Test(expected = InvalidPathException.class)
    public void deleteNonExistentProduct() throws ProductAlreadyExistsException, InvalidPathException {

        // To re-route console's input stream to custom input from string.

        ByteArrayInputStream in = new ByteArrayInputStream((giveInput(1) + giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database = new Database();
        database.insertProduct("a>b", "c");
        database.delete("a>b>d");
    }

    @Test(expected = ProductNotFoundException.class)
    public void searchNonExistentProduct() throws ProductAlreadyExistsException, ProductNotFoundException {

        // To re-route console's input stream to custom input from string.

        ByteArrayInputStream in = new ByteArrayInputStream((giveInput(1) + giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database = new Database();
        database.insertProduct("a>b", "c");
        database.searchProduct("d", false);
    }

    @Test
    public void checkOut_outOfStockProduct() throws ProductAlreadyExistsException {
        ByteArrayInputStream in = new ByteArrayInputStream((giveInput(0) + giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database = new Database();
        database.insertProduct("a>b", "c");

        Cart cart = new Cart(database);

        User customer = new Customer(cart, "testUser");
        ((Customer) customer).setFunds(1000);

        cart.addProduct("c", 1);

        System.setOut(consoleOut);
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        cart.checkOut();
        assertEquals(out.toString().trim(), "Available quantity in the database for c is less than required.\n".trim());
    }

    @Test
    public void checkOut_insufficientFunds() throws ProductAlreadyExistsException {
        ByteArrayInputStream in = new ByteArrayInputStream((giveInput(2) + giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database = new Database();
        database.insertProduct("a>b", "c");

        Cart cart = new Cart(database);

        User customer = new Customer(cart, "testUser");
        ((Customer) customer).setFunds(0);

        cart.addProduct("c", 1);

        System.setOut(consoleOut);
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        cart.checkOut();
        assertEquals(out.toString().trim(), "Funds are insufficient!\n".trim());
    }
}