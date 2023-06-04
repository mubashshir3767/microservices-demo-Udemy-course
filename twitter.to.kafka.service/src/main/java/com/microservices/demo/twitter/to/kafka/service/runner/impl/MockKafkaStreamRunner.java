package com.microservices.demo.twitter.to.kafka.service.runner.impl;

import com.microservices.demo.config.TwitterToKafkaServicesConfigData;
import com.microservices.demo.twitter.to.kafka.service.exception.TwitterToKafkaServiceException;
import com.microservices.demo.twitter.to.kafka.service.listener.TwitterKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "true")
public class MockKafkaStreamRunner implements StreamRunner {

     private static final Logger LOG = LoggerFactory.getLogger(MockKafkaStreamRunner.class);
     private final TwitterToKafkaServicesConfigData twitterToKafkaServicesConfigData;
     private final TwitterKafkaStatusListener twitterKafkaStatusListener;
     private static final Random Random = new Random();
     private static final String[] WORDS = new String[]{
         "LOREM",
         "ipsum",
         "dolor",
         "sit",
         "amet",
         "cosectuter",
         "massa",
         "fucce",
         "LOREMa",
         "LOREMaa",
         "LOREMsasd",
         "LOREMsasd1",
         "LOREMsasd2",
         "LOREMsasd3",
         "LOREMsasd4",
         "LOREMsasd5",
         "LOREMsasd6",
         "LOREMsasd7",
     };

     private static final String tweetAsRawJson = "{" +
         "\"created_at\":\"{0}\"," +
         "\"id\":\"{1}\"," +
         "\"text\":\"{2}\"," +
         "\"user\": {\"{id}\":\"{3}\"}" +
         "}";

     private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

     public MockKafkaStreamRunner(TwitterToKafkaServicesConfigData configData, TwitterKafkaStatusListener statusListener) {
          this.twitterToKafkaServicesConfigData = configData;
          this.twitterKafkaStatusListener = statusListener;
     }

     @Override
     public void start() throws TwitterException {
          String[] keywords = twitterToKafkaServicesConfigData.getTwitterKeywords().toArray(new String[0]);
          int minTweetLength = twitterToKafkaServicesConfigData.getMockMinTweetLength();
          int maxTweetLength = twitterToKafkaServicesConfigData.getMockMaxTweetLength();
          long sleepTimeMs = twitterToKafkaServicesConfigData.getMockSleepMs();
          LOG.info("Starting mock filtering twitter streams for keywords {}", Arrays.toString(keywords));
          simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepTimeMs);
     }

     
     private void simulateTwitterStream(String[] keywords, int minTweetLength, int maxTweetLength, long sleepTimeMs) throws TwitterException {
          Executors.newSingleThreadExecutor().submit(()->{
           try {
                while (true) {
                     String formattedTweetAsRawJson = getFormattedTweet(keywords, maxTweetLength, minTweetLength);
                     Status status = TwitterObjectFactory.createStatus(formattedTweetAsRawJson);
                     twitterKafkaStatusListener.onStatus(status);
                     sleep(sleepTimeMs);
                }
           }catch (Exception e){
                LOG.info("ERROR creating twitter status!!",e);
           }
          });
     }

     private void sleep(long sleepTimeMs) {
          try {
               Thread.sleep(sleepTimeMs);
          } catch (InterruptedException e) {
               throw new TwitterToKafkaServiceException("ERROR while sleeping for waiting new status to create!!");
          }
     }

     private String getFormattedTweet(String[] keywords, int maxTweetLength, int minTweetLength) {
         String[] params = new String[]{
             ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
             String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
             getRandomTweetContent(keywords,maxTweetLength,minTweetLength),
             String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
         };
          return formatTweetAsJsonWithParams(params);
     }

     private static String formatTweetAsJsonWithParams(String[] params) {
          String tweet = tweetAsRawJson;

          for (int i = 0; i< params.length; i++){
               tweet = tweet.replace("{"+i+"}", params[i]);
          }
          return tweet;
     }

     private String getRandomTweetContent(String[] keywords, int maxTweetLength, int minTweetLength) {
          StringBuilder tweet = new StringBuilder();
          int tweetLength = Random.nextInt(maxTweetLength-minTweetLength+1)+minTweetLength;
          return constructRandomTweet(keywords, tweet, tweetLength);
     }

     private static String constructRandomTweet(String[] keywords, StringBuilder tweet, int tweetLength) {
          for (int i = 0; i< tweetLength; i++){
               tweet.append(WORDS[Random.nextInt(WORDS.length)]).append(" ");
               if (i== tweetLength /2){
                    tweet.append(keywords[Random.nextInt(keywords.length)]).append(" ");
               }
          }
          return tweet.toString().trim();
     }
}
