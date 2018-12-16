package com.basharallabadi.dev.tsa.tsakafkaspark;

import org.apache.spark.sql.streaming.StreamingQueryException;

public class TwitterQueryConsumerTask implements Runnable {

    private SparkTweetsConsumer sparkTweetsConsumer;

    public TwitterQueryConsumerTask(final SparkTweetsConsumer consumer) {
        this.sparkTweetsConsumer = consumer;
    }

    @Override
    public void run() {
        try {
            sparkTweetsConsumer.consume();
        } catch (StreamingQueryException e) {
            System.err.println(e);
            return;
        }
    }
}
