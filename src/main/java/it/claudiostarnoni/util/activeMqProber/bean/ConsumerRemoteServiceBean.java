package it.claudiostarnoni.util.activeMqProber.bean;

import org.apache.camel.Body;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class ConsumerRemoteServiceBean  {
    private static final Logger LOG = LoggerFactory.getLogger(ConsumerRemoteServiceBean.class);

    @Value("${consumer.waiting.time}")
    private int waitMillis;

    @Value("${remote.service.host}")
    private String host;

    @Value("${socket.timeout.millis}")
    private int readTimeout;


    public void getEnrichAndWriteMessage(@Body String body) {
            try {
                URLConnection urlConnection = new URL("http://" + host).openConnection();
                LOG.debug("Received Body .. im gonna contact " + urlConnection.getURL());
                urlConnection.setReadTimeout(readTimeout);
                urlConnection.connect();
                LOG.debug("CONNECTED!");

                Thread.sleep(waitMillis);
            } catch (InterruptedException e) {
                LOG.error("ERROR: socket " + e.getMessage());
            } catch (UnknownHostException e) {
               LOG.error("ERROR: socket " + e.getMessage());
            } catch (IOException e) {
                LOG.error("ERROR: socket " + e.getMessage());
            }

    }

}
