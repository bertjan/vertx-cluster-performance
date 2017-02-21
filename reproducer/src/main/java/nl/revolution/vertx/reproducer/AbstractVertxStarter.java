package nl.revolution.vertx.reproducer;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.core.logging.SLF4JLogDelegateFactory;

public class AbstractVertxStarter {

    static {
        System.setProperty(LoggerFactory.LOGGER_DELEGATE_FACTORY_CLASS_NAME, SLF4JLogDelegateFactory.class.getName());
    }

    static void deployVerticles(Vertx vertx) {
        vertx.deployVerticle(
                new ConsumerVerticle(),
                new DeploymentOptions().setWorker(true));

        vertx.deployVerticle(
                new ProducerVerticle(),
                new DeploymentOptions().setWorker(true));
    }

}
