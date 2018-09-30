import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;

//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

    private String giveInput(Object o){

        return o.toString()+"\n";
    }

    private InputStream consoleIn;
    private PrintStream consoleOut;

    @Before
    public void setUp(){
        consoleIn = System.in;
        System.setIn(consoleIn);
        IO.resetScanner(consoleIn);

        consoleOut=System.out;
        System.setOut(consoleOut);
        IO.resetPrinter(consoleOut);
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void insertSameProduct() throws ProductAlreadyExistsException{
        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(1)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        ByteArrayOutputStream out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.insertProduct("a>b","c");

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }

    @Test(expected = InvalidPathException.class)
    public void deleteNonExistentProduct() throws ProductAlreadyExistsException,InvalidPathException{
        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(1)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        ByteArrayOutputStream out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.delete("a>b>d");

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }

    @Test(expected = ProductNotFoundException.class)
    public void searchNonExistentProduct() throws ProductAlreadyExistsException,ProductNotFoundException{
        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(1)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        ByteArrayOutputStream out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.searchProduct("d",false);

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }
}