package it.claudiostarnoni.util.activeMqProber.frameworkmock;

import intentfactory.core.communication.dispatchers.Processor;
import intentfactory.core.communication.dispatchers.annotations.Dispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class TestProcessor implements Processor {
    private static final Logger LOG = LoggerFactory.getLogger(TestProcessor.class);

    @Value("${consumer.waiting.time}")
    private int waitingTime;

    @Dispatch(QueueAction.class)
    public void dispatch(QueueAction queueAction) throws InterruptedException {
        LOG.debug("Received an action i'm gonna sleep for {} millis", waitingTime);
        Thread.sleep(waitingTime);
    }
}
