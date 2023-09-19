package ring;

import static org.junit.Assert.*;

import org.junit.Test;

public class DoubleRingTests {
    Ring<Double> ring = new DoubleRing();

    @Test
    public void sum() {
        assertEquals(new Double(2.5), ring.sum(1.0, 1.5));
        assertEquals(new Double(-2.5), ring.sum(0.5, -3.0));
        assertThrows(NullPointerException.class, () -> ring.sum(null, 2.5));
    }

    @Test
    public void product() {
        assertEquals(new Double(9.0), ring.product(4.5, 2.0));
        assertEquals(new Double(-7), ring.product(2.0, -3.5));
        assertEquals(new Double(0.0), ring.product(0.0, 1.5));
        assertEquals(new Double(7.0), ring.product(-2.0, -3.5));
        assertThrows(NullPointerException.class, () -> ring.product(null, 2.5));
    }

    @Test
    public void zero() {
        assertEquals(new Double(0.0), ring.zero());
    }

    @Test
    public void identity() {
        assertEquals(new Double(1.0), ring.identity());
    }
}
