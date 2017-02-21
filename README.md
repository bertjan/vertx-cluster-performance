# vertx-cluster-performance
Reproducer for performance degradation when running Vert.x in clustered mode

Setup:  
  
ConsumerVerticle listens on the eventbus and replies to messages it receives.  
ProducerVerticle sends 500k eventbus messages and logs the duration of replies.  
WithClusterStarter starts Vert.x in clustered mode and deploys both verticles.  
WithoutClusterStarter starts Vert.x in non clustered mode and deploys both verticles.    
  
The only difference between the two scenario's is Vert.x is started with or without cluster.
Both verticles are deployed as worker verticles to rule out eventloop blocking issues.  

Tried with different vert.x versions (3.3.x and 3.4.0.Beta1, no noticeable differences)  
  
Timings (on my 2014 MBP):  
- without cluster: first message received after 10ms, done after 5200 - 5500ms  
- with cluster: first message received after 15000ms, done after 17000ms  
- with cluster, local consumer: first message received after 13000ms, done after 15000ms
  
Findings:
- JGroupsClusterManager seems to be faster than HazelcastClusterManager (JGroups: first message after 4780 ms, done after 7000ms)
- IgniteClusterManager seems to be faster too, but not faster than JGroups (first message after 9863 ms, done after 12000ms)
