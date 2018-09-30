import org.junit.*;
import org.junit.Assert.*;
import static org.junit.Assert.*;

import java.io.*;

public class SerializationTest {

    private String giveInput(Object o){

        return o.toString()+"\n";
    }

    private static InputStream consoleIn; // Saves the console's input stream.
    private static PrintStream consoleOut; // Saves the console's output stream.
    private static ByteArrayOutputStream out=new ByteArrayOutputStream();

    @BeforeClass
    public static void setUp(){
        consoleIn = System.in;
        System.setIn(consoleIn);
        IO.resetScanner(consoleIn);

        consoleOut=System.out;

        // To suppress console's output.

        out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));
    }

    @AfterClass
    public static void tearDown(){

        // To reset input stream to console input.

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }

    @Test
    public void DatabaseSerializationTest() throws ProductNotFoundException,ProductAlreadyExistsException, IOException, ClassNotFoundException{

//        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(DatabaseSerializationInput())).getBytes());
//        System.setIn(in);
//        IO.resetScanner(in);

        ByteArrayInputStream in=new ByteArrayInputStream(("1\n10\n").getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        clearDatabase();

        //ECommerceApp testApp=new ECommerceApp();
        Database database1=new Database();
        database1.insertProduct("a>b","c");

//        System.setIn(consoleIn);
//        System.setOut(consoleOut);

        database1.serialize();
        database1=null;
        Database database2=new Database();
        database2=database2.update();

        System.setOut(consoleOut);
        out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        database2.searchProduct("c",true);
        assertEquals(out.toString().trim(),">a>b>c".trim());
    }

    @Test
    public void CartSerializationTest() throws ProductAlreadyExistsException, IOException, ClassNotFoundException {
        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(2)+giveInput(10)).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        clearDatabase();

        Database database=new Database();
        database.insertProduct("a>b","c");

        //Cart cart=new Cart(database);

        User customer=new Customer(new Cart(database),"testUser");
        ((Customer) customer).setFunds(1000);
        ((Customer) customer).addProduct("c",2);

        ((Customer) customer).serialize();
        customer=null;
        User customer2=Customer.deserialize("testUser");
        ((Customer) customer2).c.setDatabase(database);
        ((Customer) customer2).checkOut();
        assertEquals(database.getRevenue(),20);

        //cart.addProduct("c",1);

//        System.setOut(consoleOut);
//        out=new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//        IO.resetPrinter(new PrintStream(out));
//
//        cart.checkOut();
//        assertEquals(out.toString().trim(),"Funds are insufficient!\n".trim());
    }

    private static String DatabaseSerializationInput(){

        String s="";
        s=s+"1\n";
        s=s+"1\n";
        s=s+"a>b\n";
        s=s+"c\n";
        s=s+"1\n10\n";
        s=s+"5\n";
        s=s+"3\n";

//        s=s+"1\n";
//        s=s+"3\n";
//        s=s+"c\n";
//        s=s+"5\n";
//        s=s+"3\n";

        return s;
    }

    private static void clearDatabase(){
        File dir=new File("data/customer");

        if(!dir.exists()){
            return;
        }

        for(File file:dir.listFiles()){
            file.delete();
        }

        dir=new File("data/database");

        for(File file:dir.listFiles()){
            file.delete();
        }
    }
}