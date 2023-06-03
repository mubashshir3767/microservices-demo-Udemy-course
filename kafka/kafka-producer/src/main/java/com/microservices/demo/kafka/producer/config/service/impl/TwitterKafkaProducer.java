package com.microservices.demo.kafka.producer.config.service.impl;

import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import com.microservices.demo.kafka.producer.config.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {

     private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaProducer.class);
     private final KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;

     public TwitterKafkaProducer(KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate) {
          this.kafkaTemplate = kafkaTemplate;
     }

     @PreDestroy
     public void close(){
          if (kafkaTemplate != null){
               LOG.info("Closing kafka producer!");
               kafkaTemplate.destroy();
          }
     }
     @Override
     public void send(String topicName, Long key, TwitterAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message,topicName);
          CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture =
              kafkaTemplate.send(topicName,key,message);

          addCallback(topicName, message, kafkaResultFuture);
     }

     private static void addCallback(final String topicName,
                                     final TwitterAvroModel message,
                                     final CompletableFuture<SendResult<Long, TwitterAvroModel>> kafkaResultFuture) {
          kafkaResultFuture.whenComplete((result, throwable)->{
             if (throwable != null){
                  LOG.error("Error while sending message {} to topic {}", message, topicName, throwable);
             }else {
                  final RecordMetadata recordMetadata = result.getRecordMetadata();
                  LOG.debug("Received new Metadata. Topic: {}; Partition: {}; Offset: {}; Timestamp: {}; at Time {}.",
                      recordMetadata.topic(),
                      recordMetadata.partition(),
                      recordMetadata.offset(),
                      recordMetadata.timestamp(),
                      System.nanoTime()
                      );
             }
          });
     }
}
