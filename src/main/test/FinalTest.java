package test;

public class FinalTest {
    private final int i=1;

    public static void main(String[] args){
        FinalTest test = new FinalTest();
        System.out.println(test.get());
        System.out.println(test.getValue(10));
    }

    private int get(){
        return i;
    }

    private int getValue(final int i){
        int j=i+1;
        return j;
    }

}
