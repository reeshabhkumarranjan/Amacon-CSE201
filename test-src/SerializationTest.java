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

//        out=new ByteArrayOutputStream();
//        System.setOut(new PrintStream(out));
//        IO.resetPrinter(new PrintStream(out));
    }

    @AfterClass
    public static void tearDown(){

        // To reset input stream to console input.

        System.setIn(consoleIn);
        System.setOut(consoleOut);
    }

    @Test
    public void DatabaseSerializationTest() throws ProductNotFoundException{

        ByteArrayInputStream in=new ByteArrayInputStream((giveInput(DatabaseSerializationInput())).getBytes());
        System.setIn(in);
        IO.resetScanner(in);

        clearDatabase();

        ECommerceApp testApp=new ECommerceApp();
        Database database=new Database();
        database=database.update();

        System.setOut(consoleOut);
        out=new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        IO.resetPrinter(new PrintStream(out));

        database.searchProduct("c",true);
        assertEquals(out.toString().trim(),">a>b>c".trim());
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

        for(File file:dir.listFiles()){
            file.delete();
        }

        dir=new File("data/database");

        for(File file:dir.listFiles()){
            file.delete();
        }
    }
}