I assume you have java installed

### Infra:
 - install kafka `brew install kafka`
 - install spark 
 `brew install scala`
 `brew install apache-spark`
 - start kafka `brew services start kafka`
  Or, if you don't want/need a background service you can just run:
  ```zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties```
 - create the topics  `kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test`
 - for verification you can create console producer/consumer:
    in terminal:
    `kafka-console-producer --broker-list localhost:9092 --topic test`
    and in another terminal:
    `kafka-console-consumer --bootstrap-server localhost:9092 --topic test --from-beginning`

### Application:
- build the spark job jar
    - cd tsa-spark-job
    - run  `../mvnw clean install`
- start the producer:
run the main class `com.basharallabadi.dev.tsa.tsakafkaspark.EventsProducerRunner` it from the IDE or you can add maven exec/or package as uber jar
- submit spark job :
   - go to  tsa-spark-job/target dir
   - run : `spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.11:2.4.0 --master local[*] tsa-spark-job-0.0.1-SNAPSHOT.jar`
   
#### references:
```
- https://medium.freecodecamp.org/installing-scala-and-apache-spark-on-mac-os-837ae57d283f
- https://spark.apache.org/docs/2.4.0/streaming-kafka-0-10-integration.html
- https://medium.com/@Ankitthakur/apache-kafka-installation-on-mac-using-homebrew-a367cdefd273
- https://lenadroid.github.io/posts/distributed-data-streaming-action.html
- http://twitter4j.org/en/index.html
```

