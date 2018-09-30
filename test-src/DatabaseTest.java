import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {

    private InputStream originalIn;

    @Before
    public void setUp(){
        originalIn = System.in;
        System.setIn(originalIn);
        IO.resetScanner(originalIn);
    }

    @Test(expected = ProductAlreadyExistsException.class)
    public void firstTest() throws ProductAlreadyExistsException{
        //ByteArrayInputStream in=new ByteArrayInputStream("1\n10\n".getBytes());
        ByteArrayInputStream in=new ByteArrayInputStream(("1"+System.lineSeparator()+"10"+System.lineSeparator()).getBytes());
        System.setIn(in);
        IO.resetScanner(System.in);

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.insertProduct("a>b","c");

        System.setIn(originalIn);
    }

    @Test(expected = InvalidPathException.class)
    public void secondTest() throws ProductAlreadyExistsException,InvalidPathException{
        //ByteArrayInputStream in=new ByteArrayInputStream("1\n10\n".getBytes());
        ByteArrayInputStream in=new ByteArrayInputStream(("1"+System.lineSeparator()+"10"+System.lineSeparator()).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.delete("a>b>d");

        System.setIn(originalIn);
    }

    @Test(expected = ProductNotFoundException.class)
    public void thirdTest() throws ProductAlreadyExistsException,ProductNotFoundException{
        //ByteArrayInputStream in=new ByteArrayInputStream("1\n10\n".getBytes());
        ByteArrayInputStream in=new ByteArrayInputStream(("1"+System.lineSeparator()+"10"+System.lineSeparator()).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        Database database=new Database();
        database.insertProduct("a>b","c");
        database.searchProduct("d",false);

        System.setIn(originalIn);
    }
}