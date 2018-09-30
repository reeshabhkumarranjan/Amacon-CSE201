import java.io.*;
import java.util.HashSet;
import java.util.Scanner;

public final class IO {

    public static void resetScanner(InputStream in){
        read=new Scanner(in);
    }

    private static Scanner read = new Scanner(System.in);

    public static int nextInt() {

        return Integer.parseInt(read.next());
    }

    public static double nextDouble() {

        return Double.parseDouble(read.next());
    }

    public static String next() {

        return read.next();
    }

    public static String nextLine() {

        return read.nextLine();
    }

    public static void println(Object o) {
        System.out.println(o);
    }

    public static void print(Object o) {
        System.out.print(o);
    }

    public static HashSet deserializeUsernameList() throws IOException, ClassNotFoundException {

        ObjectInputStream in = null;
        String fileName = "data/database/" + "usernameList" + ".txt";

        try {
            in = new ObjectInputStream(new FileInputStream(fileName));
            HashSet usernameList = (HashSet) in.readObject();
            return usernameList;
        } finally {
            in.close();
        }
    }

    public static void serializeUsernameList(HashSet<String> usernamelist) throws IOException {

        ObjectOutputStream out = null;

        try {
            String fileName = "data/database/" + "usernameList" + ".txt";
            File file = new File(fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            out = new ObjectOutputStream(new FileOutputStream(fileName, false));
            out.writeObject(usernamelist);
        } finally {
            out.close();
        }
    }

    public static HashSet updateUsernameList() {

        try {
            return deserializeUsernameList();
        } catch (IOException e) {
            return new HashSet();
        } catch (ClassNotFoundException e) {
            return new HashSet();
        } catch (NullPointerException e) {
            return new HashSet();
        }
    }
}
