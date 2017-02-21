package nl.revolution.vertx.reproducer;

import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.jgroups.JGroupsClusterManager;

public class WithClusterStarter extends AbstractVertxStarter {

    public static void main(String... args) {
        System.out.println("Starting in clustered mode");
//        ClusterManager mgr = new HazelcastClusterManager();
//        ClusterManager mgr = new IgniteClusterManager();
        ClusterManager mgr = new JGroupsClusterManager();
        VertxOptions options = new VertxOptions().setClusterManager(mgr);
        Vertx.clusteredVertx(options, resultHandler -> {
            Vertx vertx = resultHandler.result();
            deployVerticles(vertx);
        });
    }
}
