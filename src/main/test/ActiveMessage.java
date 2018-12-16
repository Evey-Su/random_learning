package test;

import java.lang.reflect.Method;

class ActiveMessage {
    //接口方法参数
    private final Object[] objects;
    //接口方法
    private final Method method;

    private final ActiveFuture<Object> future;

    //具体的service
    private final Object service;

    public ActiveMessage(Builder builder) {
        this.objects = builder.objects;
        this.method = builder.method;
        this.future = builder.future;
        this.service = builder.service;
    }

    static class Builder {
        //接口方法参数
        private Object[] objects;
        //接口方法
        private Method method;

        private ActiveFuture<Object> future;

        //具体的service
        private Object service;

        public Builder userMethod(Method method) {
            this.method = method;
            return this;
        }


        public Builder returnFuture(ActiveFuture<Object> future) {
            this.future = future;
            return this;
        }

        public Builder withObjects(Object[] objects) {
            this.objects = objects;
            return this;
        }

        public Builder forService(Object service) {
            this.service = service;
            return this;
        }

        //构建ActiveMessage实例
        public ActiveMessage build() {
            return new ActiveMessage(this);
        }
    }
}
