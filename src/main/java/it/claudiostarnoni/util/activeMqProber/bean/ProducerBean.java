package it.claudiostarnoni.util.activeMqProber.bean;


import org.apache.camel.Body;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

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


    public void startProducing(@Body String body) throws InterruptedException {
        LOG.debug("PRODUCING BEAN INVOKED AT {} WITH TOTAL NUMBER OF MESSAGE TO SEND {}", new Date(), numberOfMessage);
        final Random random = new Random();

        for (long i = 0; i < numberOfMessage; i++) {
            if (i % burstSize == 0) {
                long delay;
                if (interBurstRandom) {
                    delay = random.nextInt(interBurstDelay);
                } else {
                    delay = interBurstDelay;
                }
                if (delay!=0) {
                    Thread.sleep(delay);
                }
            }

            threadPoolTaskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    producer.sendBody(msgIndexer.incrementAndGet() + "," + UUID.randomUUID() + "," + System.currentTimeMillis());
                }
            });

        }
        LOG.debug("Async message send finished at {}", new Date());
    }


}
