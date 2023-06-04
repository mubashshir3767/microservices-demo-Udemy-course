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
public class MockKafka implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(MockKafka.class);

    private final TwitterToKafkaServicesConfigData twitterToKafkaServicesConfigData;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private final static Random Random = new Random();

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
    };

    private static final String tweetAsRawJson = "{" +
            "\"created_at\":\"{0}\"," +
            "\"id\":\"{1}\"," +
            "\"text\":\"{2}\"," +
            "\"user\":\"{\"{id}\":\"{3}\"}" +
            "}";

    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    public MockKafka(TwitterToKafkaServicesConfigData twitterToKafkaServicesConfigData, TwitterKafkaStatusListener twitterKafkaStatusListener) {
        this.twitterToKafkaServicesConfigData = twitterToKafkaServicesConfigData;
        this.twitterKafkaStatusListener = twitterKafkaStatusListener;
    }


    @Override
    public void start() throws TwitterException {
        String[] words = twitterToKafkaServicesConfigData.getTwitterKeywords().toArray(new String[0]);
        long mockSleepMs = twitterToKafkaServicesConfigData.getMockSleepMs();
        int mockMinTweetLength = twitterToKafkaServicesConfigData.getMockMinTweetLength();
        int mockMaxTweetLength = twitterToKafkaServicesConfigData.getMockMaxTweetLength();
        LOG.info("Starting mock filtering twitter streams for keywords {}", Arrays.toString(words));
        simulateTwitterStream(words, mockMinTweetLength, mockMaxTweetLength, mockSleepMs);

    }

    private void simulateTwitterStream(String[] words, int mockMinTweetLength, int mockMaxTweetLength, long mockSleepMs) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    String formattedTweetsAsRawJson = getFormattedTweet(words, mockMaxTweetLength, mockMinTweetLength);
                    Status status = TwitterObjectFactory.createStatus(formattedTweetsAsRawJson);
                    twitterKafkaStatusListener.onStatus(status);
                    sleep(mockSleepMs);
                }
            } catch (Exception e) {
                LOG.info("Error creating twitter status!!!", e);
            }
        });
    }

    private void sleep(long mockSleepMs) {
        try {
            Thread.sleep(mockSleepMs);
        } catch (InterruptedException e) {
            throw new TwitterToKafkaServiceException("Error while sleeping for waiting new status to create!!!",e);
        }
    }

    private String getFormattedTweet(String[] words, int mockMaxTweetLength, int mockMinTweetLength) {
      String [] params = new String[]{
              ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
              String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
              getRandomTweetContext(words,mockMaxTweetLength,mockMinTweetLength),
              String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
      };
      return formatTweetAsJsonWithParams(params);
    }

    private String formatTweetAsJsonWithParams(String[] params) {
        return null;
    }

    private String getRandomTweetContext(String[] words, int mockMaxTweetLength, int mockMinTweetLength) {
        return null;
    }
}
