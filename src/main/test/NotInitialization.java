package test;

public class NotInitialization {
    /**
     * 通过其子类来引用父类中定义的静态字段，只会触发子类的初始化
     */
    public static void main(String args[]){
        System.out.println(SubClass.value);
    }

}

class SuperClass {
    static {
        System.out.println("superClass init!");
    }

    public static int value = 123;
}

class SubClass extends SuperClass {
    static {
        System.out.println("subClass init");
    }
}

