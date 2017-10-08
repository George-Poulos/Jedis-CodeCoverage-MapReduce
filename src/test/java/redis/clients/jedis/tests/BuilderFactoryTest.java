package redis.clients.jedis.tests;

import org.junit.Test;
import redis.clients.jedis.BuilderFactory;

import static org.junit.Assert.assertEquals;

public class BuilderFactoryTest {
  @Test
  public void buildDouble() {
    Double build = BuilderFactory.DOUBLE.build("1.0".getBytes());
    assertEquals(new Double(1.0), build);
  }
}