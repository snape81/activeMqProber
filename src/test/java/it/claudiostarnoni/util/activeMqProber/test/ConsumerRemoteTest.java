package it.claudiostarnoni.util.activeMqProber.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = "classpath*:consumers-remote-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ConsumerRemoteTest {


    @Test
         public void routingTest() throws InterruptedException {
         Thread.sleep(600000);
     }

}
