package test;

import java.lang.reflect.Proxy;

public class RTTITest {
    static void printinfo(Class c) {
        System.out.println("类名：" + c.getName() + ",是否是接口：" + c.isInterface());
    }

    public static void main(String args[]) {
        Class c = null;
        try {
            c= Class.forName("test.Derived");
        }catch (ClassNotFoundException e){
            System.out.println("找不到Derived类");
            System.exit(1);
        }

        printinfo(c);
    }
}

class Base {
    static {
        System.out.println("加载base类");
    }
}

class Derived extends Base {
    static {
        System.out.println("加载derived类");
    }
}


