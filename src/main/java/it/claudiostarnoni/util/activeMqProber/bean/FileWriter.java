package it.claudiostarnoni.util.activeMqProber.bean;

import org.apache.camel.Body;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

import static it.claudiostarnoni.util.activeMqProber.util.Constants.*;

public class FileWriter {
    private static final Logger LOG = LoggerFactory.getLogger(FileWriter.class);

    @Value("${report.file.path}")
    private String reportFilePath;

    @Value("${consumer.waiting.time}")
    private int waitMillis;

    public void getEnrichAndWriteMessage(@Body String body) {
        try {
            LOG.debug("Received Body .. im gonna sleep");
            Thread.sleep(waitMillis);
        } catch (InterruptedException ignored) {
        }
        try {
            final int noStoreTokenIndex = body.indexOf(NO_STORE_TOKEN);
            StringBuilder sb = new StringBuilder();
            if (noStoreTokenIndex > 0) {
                sb.append(body.substring(0, noStoreTokenIndex));
            } else {
                sb.append(body);
            }
            sb.append(COMMA_SEPARATOR).append(System.currentTimeMillis()).append(NEW_LINE_ESCAPE_STRING);
            LOG.debug("About to writing on file {}",sb);
            FileUtils.writeStringToFile(new File(reportFilePath), sb.toString(), true);
        } catch (IOException e) {
            LOG.error("Error in writing report ", e);
        }
    }


}
