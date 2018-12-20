package com.basharallabadi.dev.tsa.tsakafkaspark;

import org.apache.kafka.clients.producer.RecordMetadata;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.concurrent.Future;

class TwitterQueryProducerTask implements Runnable {
    private Twitter twitter;
    private EventsProducer producer;

    TwitterQueryProducerTask(final EventsProducer producer) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("oYmu...")
                .setOAuthConsumerSecret("dC1j..")
                .setOAuthAccessToken("1061369..")
                .setOAuthAccessTokenSecret("A8qEH8...");

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
        this.producer = producer;
    }

    @Override
    public void run() {
        Query query = new Query(" #Toronto ");
        query.lang("en");
        query.count(10);
        QueryResult result = null;
        Long lowestStatusId = Long.MAX_VALUE;
        int i = 0;


        while(i < 30) {
            try {
                result = twitter.search(query);

                for (Status status : result.getTweets()) {
                    if(!status.isRetweet()){
                        Future<RecordMetadata> f = producer.sendMessage("test", status.getText());
                    }
                    lowestStatusId = Math.min(status.getId(), lowestStatusId);
                }
                query.setMaxId(lowestStatusId - 1);
                Thread.sleep(5000L);
            } catch (Exception e) {
                System.err.println(e);
                return;
            }
            i++;
        }
    }
}