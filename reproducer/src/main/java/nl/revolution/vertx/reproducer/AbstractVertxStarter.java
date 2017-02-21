package nl.revolution.vertx.reproducer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;

public class AbstractVertxStarter {

    static void deployVerticles(Vertx vertx) {
        vertx.deployVerticle(
                new ConsumerVerticle(),
                new DeploymentOptions().setWorker(true));

        vertx.deployVerticle(
                new ProducerVerticle(),
                new DeploymentOptions().setWorker(true));
    }

}
