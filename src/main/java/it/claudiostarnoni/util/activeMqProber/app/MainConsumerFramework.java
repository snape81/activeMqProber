package it.claudiostarnoni.util.activeMqProber.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainConsumerFramework {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("consumers-seda-context.xml");
        Thread.sleep(24*60*60*1000);

    }
}
