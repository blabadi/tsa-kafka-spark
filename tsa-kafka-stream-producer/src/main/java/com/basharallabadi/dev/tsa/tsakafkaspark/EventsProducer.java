package com.basharallabadi.dev.tsa.tsakafkaspark;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.Future;

class EventsProducer {
    private final String BROKERS = "localhost:9092";
    private org.apache.kafka.clients.producer.KafkaProducer<String, String> producer;

     EventsProducer() {
         Properties props = new Properties();
         props.put("bootstrap.servers", BROKERS);
         props.put("acks", "all");
         props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
         props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
         this.producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);
    }

    Future<RecordMetadata> sendMessage(String topic, String message) {
        String key = java.util.UUID.randomUUID().toString();
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        return this.producer.send(record);
    }
}
