package redis.clients.jedis.tests;

import org.junit.Test;
import redis.clients.jedis.Tuple;

import static org.junit.Assert.assertEquals;

public class TupleTestNew {
    @Test
    public void equals() throws Exception {
        Tuple t1 = new Tuple("T1", 1.00);
        Tuple t2 = new Tuple("T2" , 2.00);
        Tuple t3 = new Tuple("T1", 1.00);

        boolean tmp1 = t1.equals(t2);
        boolean tmp2 = t1.equals(t3);

        assertEquals(tmp1, false);
        assertEquals(tmp2, true);
    }

    @Test
    public void compareTo() throws Exception {
        Tuple t1 = new Tuple("T1", 1.00);
        Tuple t2 = new Tuple("T2" , 2.00);
        Tuple t3 = new Tuple("T3", 3.00);

        int tmp1 = t1.compareTo(t1);
        int tmp2 = t1.compareTo(t2);
        int tmp3 = t3.compareTo(t2);

        assertEquals(0, tmp1);
        assertEquals(-1, tmp2);
        assertEquals(1, tmp3);
    }

    @Test
    public void getScore() {
        Tuple t1 = new Tuple("T1", 1.00);

        assertEquals(1, (int)t1.getScore());
    }

    }
