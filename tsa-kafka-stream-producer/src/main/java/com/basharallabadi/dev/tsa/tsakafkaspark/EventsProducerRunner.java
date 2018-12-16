package com.basharallabadi.dev.tsa.tsakafkaspark;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class EventsProducerRunner {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(new TwitterQueryProducerTask(new EventsProducer()));
        future.get();
    }
}
