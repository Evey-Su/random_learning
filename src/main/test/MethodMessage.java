package test;

import java.util.Map;

public abstract class MethodMessage {
    //用于收集方法参数，如果又返回future则一并收集
    protected final Map<String, Object> params;
    protected final OrderService orderService;

    public MethodMessage(Map<String, Object> params, OrderService orderService) {
        this.params = params;
        this.orderService = orderService;
    }

    //抽象方法，扮演work thread的说明书
    public abstract void execute();
}
