package it.claudiostarnoni.util.activeMqProber.bean;

import org.apache.camel.Body;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

public class FileWriter {
    private static final Logger LOG = LoggerFactory.getLogger(FileWriter.class);

    @Value("${report.file.path}")
    private String reportFilePath;

    @Value("${consumer.waiting.time}")
    private int waitMillis;

    public void getEnrichAndWriteMessage(@Body String body) {
        try {
            LOG.debug("Received Body {}",body);
            Thread.sleep(waitMillis);
        } catch (InterruptedException ignored) {
        }
        try {
            FileUtils.writeStringToFile(new File(reportFilePath), body + "," + System.currentTimeMillis()+"\n", true);
        } catch (IOException e) {
            LOG.error("Error in writing report ",e);
        }
    }


}
