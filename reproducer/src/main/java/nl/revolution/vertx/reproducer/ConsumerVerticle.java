package nl.revolution.vertx.reproducer;

import io.vertx.core.AbstractVerticle;

public class ConsumerVerticle extends AbstractVerticle {

    public static final String ADDRESS = "cluster-test";
    boolean firstMessageReceived;

    @Override
    public void start() throws Exception {
        System.out.println("Starting " + this.getClass().getSimpleName());
        long start = System.currentTimeMillis();

        vertx.eventBus().consumer(ADDRESS).handler(message -> {
            if (!firstMessageReceived) {
                System.out.println("Received first message after " + (System.currentTimeMillis() - start) + " ms");
                firstMessageReceived = true;
            }

            message.reply("OK");
        }).completionHandler(result -> System.out.println("Consumer registered after " + (System.currentTimeMillis() - start) + " ms"));
    }

}