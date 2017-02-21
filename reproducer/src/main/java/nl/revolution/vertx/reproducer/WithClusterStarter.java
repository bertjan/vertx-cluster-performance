package nl.revolution.vertx.reproducer;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

public class WithClusterStarter extends AbstractVertxStarter {

    public static void main(String... args) {
        System.out.println("Starting in clustered mode");
        VertxOptions options = new VertxOptions();
        Vertx.clusteredVertx(options, resultHandler -> {
            Vertx vertx = resultHandler.result();
            deployVerticles(vertx);
        });
    }
}
