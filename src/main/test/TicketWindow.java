package test;

public class TicketWindow extends Thread{
    //柜台名称
    private final String name;

    //最多受理50笔业务
    private static final int MAX=500;

    private static int index=1;
    public TicketWindow(String name){
        this.name = name;
    }

    @Override
    public void run(){
        while (index<=MAX){
            System.out.println("柜台："+name+",当前的号码是："+(index++));
        }
    }

    public static void main(String[] args) {
        TicketWindow window1 = new TicketWindow("一号出机");
        window1.start();
        TicketWindow window2 = new TicketWindow("二号出机");
        window2.start();
        TicketWindow window3 = new TicketWindow("三号出机");
        window3.start();
        TicketWindow window4 = new TicketWindow("四号出机");
        window4.start();
    }
}
