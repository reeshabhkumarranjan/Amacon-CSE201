import org.junit.*;
import org.junit.runners.MethodSorters;

import java.io.*;

public class DatabaseTest {

    private String giveInput(Object o){

        return o.toString()+"\n";
    }

    private static InputStream consoleIn; // Saves the console's input stream.
    private static PrintStream consoleOut; // Saves the console's output stream.

    @BeforeClass
    public static void setUp(){
        consoleIn = System.in;
        System.setIn(consoleIn);
        IO.resetScanner(consoleIn);

        consoleOut=System.out;

        // To suppress console's output.

        ByteArrayOutputStream out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));
    }

    @AfterClass
    public static void tearDown(){

        // To reset input stream to console input.

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void insertSameProduct() throws ProductAlreadyExistsException{

        // To re-route console's input stream to custom input from string.

        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(1)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.insertProduct("a>b","c");


    }

    @Test(expected = InvalidPathException.class)
    public void deleteNonExistentProduct() throws ProductAlreadyExistsException,InvalidPathException{

        // To re-route console's input stream to custom input from string.

        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(1)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.delete("a>b>d");
    }

    @Test(expected = ProductNotFoundException.class)
    public void searchNonExistentProduct() throws ProductAlreadyExistsException,ProductNotFoundException{

        // To re-route console's input stream to custom input from string.

        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(1)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.searchProduct("d",false);
    }
}