package it.claudiostarnoni.util.activeMqProber.app;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainProducer {

    public static void main(String[] args) throws Exception {

        ApplicationContext context = new ClassPathXmlApplicationContext("producer-context.xml");
        CamelContext camelContext = context.getBean("producerContext", CamelContext.class);

        ProducerTemplate producerTemplate = new DefaultProducerTemplate(camelContext);
        producerTemplate.start();
        producerTemplate.send("direct:start", new DefaultExchange(camelContext));


    }
}
