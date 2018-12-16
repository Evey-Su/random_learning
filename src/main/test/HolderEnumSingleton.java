package test;

public final class HolderEnumSingleton {
    private int value;

    private HolderEnumSingleton() {

    }

    private enum HolderEnum {
        INSTANCE;
        private HolderEnumSingleton instance;

        HolderEnum() {
            this.instance = new HolderEnumSingleton();
        }

        private HolderEnumSingleton getSingleton() {
            return instance;
        }
    }

    public static HolderEnumSingleton getInstance() {
        return HolderEnum.INSTANCE.getSingleton();
    }
}
