package test;

public final class LazySingleton {
    private int value;
    private static LazySingleton instance = null;

    private LazySingleton(){

    }

    public static LazySingleton getInstance(){
        if (null==instance){
            instance = new LazySingleton();
        }
        return instance;
    }
}
