package nl.revolution.vertx.reproducer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;

import java.util.stream.IntStream;

public class ProducerVerticle extends AbstractVerticle {

    private static final int NUM_MESSAGES = 500000;

    private long replyCount = 0;
    private long start;

    @Override
    public void start() throws Exception {
        System.out.println("Starting " + this.getClass().getSimpleName());
        start = System.currentTimeMillis();

        System.out.println("Starting to send messages.");
        IntStream.range(0, NUM_MESSAGES).forEach(i -> {
            if (i % 20000 == 0) {
                System.out.println(i + " eventbus messages sent in " + (System.currentTimeMillis() - start) + " ms.");
            }

            vertx.eventBus().send(
                    ConsumerVerticle.ADDRESS,
                    "dummy message",
                    this::onReply);
        });
        System.out.println(NUM_MESSAGES + " eventbus messages sent in " + (System.currentTimeMillis() - start) + " ms.");

    }

    private void onReply(AsyncResult<Message<Object>> reply) {
        if (reply.result() != null && "OK".equals(reply.result().body())) {
            replyCount++;

            // log progress for the impatient ;-)
            if (replyCount % 10000 == 0) {
                System.out.println("replyCount: " + replyCount + " after " + (System.currentTimeMillis() - start) + " ms");
            }

            // stop when the number of received replies is equal to the number of sent messages
            if (replyCount == NUM_MESSAGES) {
                System.out.println("all replies received in " + (System.currentTimeMillis() - start) + " ms");
                System.exit(0);
            }
        }
    }
}