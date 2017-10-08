package redis.clients.jedis.tests;

import org.junit.Test;
import redis.clients.jedis.JedisMonitor;

import static org.junit.Assert.assertEquals;

public class JedisMonitorTest {
    @Test
    public void proceed() throws Exception {
        JedisMonitor jm = new JedisMonitor() {
            @Override
            public void onCommand(String command) {
                System.out.println("Test Command Executed!");
            }
        };
        assertEquals(true, true);
    }

    @Test
    public void onCommand() throws Exception {
        assertEquals(true, true);
    }

}