import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class SerializationTest {

    private static InputStream consoleIn; // Saves the console's input stream.
    private static PrintStream consoleOut; // Saves the console's output stream.
    private static ByteArrayOutputStream out = new ByteArrayOutputStream();

    @BeforeClass
    public static void setUp() {

        createDirectories();
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
        clearDatabase();
    }

    private static void clearDatabase() {
        File dir = new File("data/customer");

        if (!dir.exists()) {
            return;
        }

        for (File file : dir.listFiles()) {
            file.delete();
        }

        dir = new File("data/database");

        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

    private String giveInput(Object o) {

        return o.toString() + "\n";
    }

    @Test
    public void DatabaseSerializationTest() throws ProductNotFoundException, ProductAlreadyExistsException, IOException, ClassNotFoundException {

        ByteArrayInputStream in = new ByteArrayInputStream(("1\n10\n").getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        clearDatabase();
        Database database1 = new Database();
        database1.insertProduct("a>b", "c");

        database1.serialize();
        database1 = null;
        Database database2 = new Database();
        database2 = database2.update();

        System.setOut(consoleOut);
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        database2.searchProduct("c", true);
        assertEquals(out.toString().trim(), ">a>b>c".trim());
    }

    @Test
    public void CartSerializationTest() throws ProductAlreadyExistsException, IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream((giveInput(2) + giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        clearDatabase();

        Database database = new Database();
        database.insertProduct("a>b", "c");

        User customer = new Customer(new Cart(database), "testUser");
        ((Customer) customer).setFunds(1000);
        ((Customer) customer).addProduct("c", 2);

        ((Customer) customer).serialize();
        customer = null;
        User customer2 = Customer.deserialize("testUser");
        ((Customer) customer2).c.setDatabase(database);
        ((Customer) customer2).checkOut();
        assertEquals(database.getRevenue(), 20);
    }

    private static final void createDirectories() {

        String[] dirList = {"data/customer/", "data/database"};

        for (String dirName : dirList) {
            File f = new File(dirName);

            if (!f.exists()) {
                f.mkdirs();
            }
        }
    }

}