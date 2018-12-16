package test;

public final class HolderSingleton {
    private int value;

    private HolderSingleton() {
    }

    private static class Holder {
        private static HolderSingleton instance = new HolderSingleton();
    }

    public static HolderSingleton getInstance() {
        return Holder.instance;
    }
}
