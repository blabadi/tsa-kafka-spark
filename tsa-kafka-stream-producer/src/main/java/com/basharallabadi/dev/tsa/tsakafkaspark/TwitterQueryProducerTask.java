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
                .setOAuthConsumerKey("oYmuDFcWXgYo6tJZ8cgzl6ghY")
                .setOAuthConsumerSecret("dC1jar0cDD5FO99WuWZwu7mjcabAEkz6QSY4PBWiB3sdxvKSmy")
                .setOAuthAccessToken("1061369819344314375-EMPNi1LHTJRZr1deEf4GLQC6IkLJtm")
                .setOAuthAccessTokenSecret("A8qEH8gjga2dcpRLgW0FN8W0mdQlecvuzUB3BKNlDEcih");

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