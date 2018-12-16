package test;

//在流水线上需要被加工的产品，create作为产品制作的说明书
public abstract class IntroductionBook {
    public final void create() {
        this.firstProcess();
        this.secondProcess();
    }

    protected abstract void firstProcess();

    protected abstract void secondProcess();
}
