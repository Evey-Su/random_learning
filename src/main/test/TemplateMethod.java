package test;

/**
 * 模板设计 父类编写算法结构代码，子类实现逻辑细节
 */
public class TemplateMethod {
    public final void print(String message){
        System.out.println("############");
        wrapPrint(message);
        System.out.println("############");
    }

    protected void wrapPrint(String message){};

    public static void main(String args[]){
        TemplateMethod t1 = new TemplateMethod(){
            @Override
            protected void wrapPrint(String message){
                System.out.println("*"+message+"*");
            }
        };

        t1.print("hello");

    }
}
