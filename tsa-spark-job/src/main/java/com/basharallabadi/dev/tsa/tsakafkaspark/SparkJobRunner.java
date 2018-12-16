package com.basharallabadi.dev.tsa.tsakafkaspark;

import org.apache.spark.sql.streaming.StreamingQueryException;

public class SparkJobRunner {
    public static void main(String[] args) throws StreamingQueryException {
        SparkTweetsConsumer sparkTweetsConsumer = new SparkTweetsConsumer();
        sparkTweetsConsumer.consume();
//        CompletableFuture<Void> future2 = CompletableFuture.runAsync(new TwitterQueryConsumerTask(sparkTweetsConsumer));
    }
}

