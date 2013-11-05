package it.claudiostarnoni.util.activeMqProber.test;


import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath*:producer-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ProducerTest {

    @EndpointInject(uri = "direct:start")
    private ProducerTemplate directStart;

    @Test
         public void routingTest() throws InterruptedException {
         directStart.sendBody("Start");
         Thread.sleep(600000);
     }

}
