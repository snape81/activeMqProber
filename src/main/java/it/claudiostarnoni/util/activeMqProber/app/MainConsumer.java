package it.claudiostarnoni.util.activeMqProber.app;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainConsumer {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("consumers-context.xml");
        CamelContext camelContext = context.getBean("consumerContext", CamelContext.class);

    }
}
