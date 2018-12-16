package test;

public class ClassInit {
    static {
        x=10;
    }
    private static int x=10;

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.ext.dirs"));
    }
}
