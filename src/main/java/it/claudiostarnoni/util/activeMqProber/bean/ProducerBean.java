package it.claudiostarnoni.util.activeMqProber.bean;


import org.apache.camel.Body;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.lf5.util.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static it.claudiostarnoni.util.activeMqProber.util.Constants.COMMA_SEPARATOR;
import static it.claudiostarnoni.util.activeMqProber.util.Constants.NO_STORE_TOKEN;
import static it.claudiostarnoni.util.activeMqProber.util.Constants.QUE_ACTION_PLACEHOLDER;

public class ProducerBean {
    private static final Logger LOG = LoggerFactory.getLogger(ProducerBean.class);

    @EndpointInject(ref = "testQueueEndpoint")
    ProducerTemplate producer;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    private AtomicLong msgIndexer = new AtomicLong();

    @Value("${total.message.number}")
    private long numberOfMessage;

    @Value("${producer.burst.size}")
    private long burstSize;

    @Value("${producer.interburst.delay}")
    private int interBurstDelay;

    @Value("${producer.interburst.random}")
    private boolean interBurstRandom;

    @Value("${message.length.with.padding.active}")
    private boolean paddingLengthActive;

    @Value("${message.with.framework.action}")
    private boolean frameworkActionUsed;

    @Value("${message.length.byte}")
    private int padingLength;

    @Value("QueueAction.txt")
    private Resource actionTemplate;

    private String randomPadding;
    private String actionTemplateString;

    @PostConstruct
    public void performPaddingGeneration() throws IOException {
        if (paddingLengthActive) {
            randomPadding = RandomStringUtils.randomAlphanumeric(padingLength);
        }
        InputStream is = actionTemplate.getInputStream();
        actionTemplateString = IOUtils.toString(is);

        LOG.debug("actionTemplateString {}", actionTemplateString);
    }

    public void startProducing(@Body String body) throws InterruptedException {
        LOG.debug("PRODUCING BEAN INVOKED AT {} WITH TOTAL NUMBER OF MESSAGE TO SEND {}", new Date(), numberOfMessage);
        final Random random = new Random();

        for (long i = 0; i < numberOfMessage; i++) {
            if (i != 0 && i % burstSize == 0) {
                long delay;
                if (interBurstRandom) {
                    delay = random.nextInt(interBurstDelay);
                } else {
                    delay = interBurstDelay;
                }
                if (delay != 0) {
                    Thread.sleep(delay);
                }
            }

            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    LOG.debug("SENDING .... ");

                    StringBuilder sb = new StringBuilder();
                    sb.append(msgIndexer.incrementAndGet()).append(COMMA_SEPARATOR).append(UUID.randomUUID()).append(COMMA_SEPARATOR).append(System.currentTimeMillis());
                    if (paddingLengthActive) {
                        sb.append(NO_STORE_TOKEN).append(randomPadding);
                    }

                    if (frameworkActionUsed) {
                        producer.sendBody(actionTemplateString.replaceAll(QUE_ACTION_PLACEHOLDER, sb.toString()));
                    } else {
                        producer.sendBody(sb.toString());
                    }
                }
            });

        }
        LOG.debug("Async message send finished at {}", new Date());
    }


}
