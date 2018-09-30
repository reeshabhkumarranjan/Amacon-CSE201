import java.io.File;

public class Main {

    private static void clearDatabase(){
        File file=new File("data");
        if(file.exists()){
            file.delete();
        }
    }

    public static void main(String[] args) {
        clearDatabase();
        System.exit(0);
        ECommerceApp Amacon = new ECommerceApp();
    }
}