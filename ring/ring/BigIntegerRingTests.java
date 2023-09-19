package ring;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

public class BigIntegerRingTests {
    Ring<BigInteger> ring = new BigIntegerRing();

    @Test
    public void sum() {
        assertEquals(BigInteger.valueOf(3), ring.sum(BigInteger.ONE, BigInteger.valueOf(2)));
        assertEquals(BigInteger.valueOf(-2), ring.sum(BigInteger.ONE, BigInteger.valueOf(-3)));
        assertThrows(NullPointerException.class, () -> ring.sum(null, BigInteger.ONE));
    }

    @Test
    public void product() {
        assertEquals(BigInteger.valueOf(8), ring.product(BigInteger.valueOf(2), BigInteger.valueOf(4)));
        assertEquals(BigInteger.valueOf(-8), ring.product(BigInteger.valueOf(2), BigInteger.valueOf(-4)));
        assertEquals(BigInteger.valueOf(8), ring.product(BigInteger.valueOf(-2), BigInteger.valueOf(-4)));
        assertEquals(BigInteger.valueOf(0), ring.product(BigInteger.valueOf(2), BigInteger.valueOf(0)));
        assertThrows(NullPointerException.class, () -> ring.product(null, BigInteger.ONE));
    }

    @Test
    public void zero() {
        assertEquals(BigInteger.ZERO, ring.zero());
    }

    @Test
    public void identity() {
        assertEquals(BigInteger.ONE, ring.identity());
    }
}
