package ring;

import static org.junit.Assert.*;

import org.junit.Test;

public class IntegerRingTests {
    Ring<Integer> ring = new IntegerRing();

    @Test
    public void sum() {
        assertEquals(new Integer(3), ring.sum(1, 2));
        assertEquals(new Integer(-2), ring.sum(1, -3));
        assertEquals(Integer.MIN_VALUE, ring.sum(Integer.MAX_VALUE, 1).intValue());
    }

    @Test
    public void product() {
        assertEquals(new Integer(8), ring.product(4, 2));
        assertEquals(new Integer(-6), ring.product(2, -3));
        assertEquals(new Integer(0), ring.product(0, 1));
        assertEquals(new Integer(6), ring.product(-2, -3));
    }

    @Test
    public void zero() {
        assertEquals(new Integer(0), ring.zero());
    }

    @Test
    public void identity() {
        assertEquals(new Integer(1), ring.identity());
    }
}
