import java.util.Scanner;

public final class IO {

    private static Scanner read=new Scanner(System.in);

    public static int nextInt(){

        return Integer.parseInt(read.next());
    }

    public static double nextDouble(){

        return Double.parseDouble(read.next());
    }

    public static String next(){

        return read.next();
    }

    public static String nextLine(){

        return read.nextLine();
    }

    public static void println(Object o){
        System.out.println(o);
    }

    public static void print(Object o){
        System.out.print(o);
    }


}
