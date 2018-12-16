package test;


public interface EventExceptionHandler {
    void handle(Throwable cause, EventContext context);
}
