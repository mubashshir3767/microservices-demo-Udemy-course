package com.microservices.demo.twitter.to.kafka.service;

import com.microservices.demo.config.TwitterToKafkaServicesConfigData;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafkaService implements CommandLineRunner {

     private final Logger Log = LoggerFactory.getLogger(TwitterToKafkaService.class);
     private final StreamRunner streamRunner;
     private final TwitterToKafkaServicesConfigData twitterToKafkaServicesConfigData;

     public TwitterToKafkaService(StreamRunner streamRunner, TwitterToKafkaServicesConfigData twitterToKafkaServicesConfigData) {
          this.streamRunner = streamRunner;
          this.twitterToKafkaServicesConfigData = twitterToKafkaServicesConfigData;
     }



     public static void main(String[] args) {
          SpringApplication.run(TwitterToKafkaService.class, args);
     }

     @Override
     public void run(String... args) throws Exception {
          Log.info("app starts... ");
          Log.info(Arrays.toString(twitterToKafkaServicesConfigData.getTwitterKeywords().toArray(new String[]{})));
          Log.info(twitterToKafkaServicesConfigData.getWelcomeMessage());
          streamRunner.start();
     }
}
