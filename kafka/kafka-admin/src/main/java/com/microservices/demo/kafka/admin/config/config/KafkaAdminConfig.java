package com.microservices.demo.kafka.admin.config.config;

import com.microservices.demo.config.KafkaConfigData;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;

import java.util.Map;

@EnableRetry
@Configuration
public class KafkaAdminConfig {

     private final KafkaConfigData kafkaConfigData;

     public KafkaAdminConfig(KafkaConfigData kafkaConfigData) {
          this.kafkaConfigData = kafkaConfigData;
     }
     @Bean
     public RetryTemplate retryTemplate(){
          return new RetryTemplate();
     }
     
     @Bean
     public AdminClient adminClient(){
          return AdminClient.create(Map.of(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
              kafkaConfigData.getBootstrapServers()));
     }
}
