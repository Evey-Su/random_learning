package test;

import com.alibaba.fastjson.JSON;

public class StairsTest {
    public static void main(String[] args) {
//        resolve("a","b","c",4);
//        System.out.println(resolve1(2));
        System.out.println(resuSelect(4));
    }

    public static int select(int n) {
        if (n<1){
            return 0;
        }
        if (n<=2){
            return n;
        }
        int nlese2=1;
        int nless1=2;
        int result=0;
        for (int i=3;i<=n;i++){
            result=nless1+nlese2;
            nlese2=nless1;
            nless1=result;
        }
        return result;
    }

    public static int resuSelect(int n){
        if (n<1){
            return 0;
        }
        //出口
        if (n<=2){
            return n;
        }
        return resuSelect(n-1)+resuSelect(n-2);
    }

    public static int selectli(int n){
        int lastResult=0;
        int result=1;
        for (int i=1;i<=n;i++){
            result = result+lastResult;
            lastResult=result-lastResult;
        }
        return result;
    }


    public static int C(int up, int below)//应用组合数的互补率简化计算量
    {
        int helf = below / 2;
        if (up > helf) {
            System.out.print(up + "---->");
            up = below - up;
            System.out.print(up + "\n");

        }
        int denominator = A(up, up);//A(6,6)就是求6*5*4*3*2*1,也就是求6的阶乘
        //分子
        int numerator = A(up, below);//分子的排列数
        return numerator / denominator;
    }

    public static int A(int up, int bellow) {
        int result = 1;
        for (int i = up; i > 0; i--) {
            result *= bellow;
            bellow--;
        }
        return result;
    }

    public static void resolve(String a, String b, String c, int n) {
        if (n == 1) {
            System.out.println(a + "->" + c);
            return;
        }
        resolve(a, c, b, n - 1);
        System.out.println(a + "->" + c);
        resolve(b, a, c, n - 1);
    }

    public static int resolve1(int n) {
        if (n == 1) {
            return 1;
        }
        return n * resolve1(n - 1);
    }
}
