package ring;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class PolynomialRingTests {
    Ring<Integer> integerRing = new IntegerRing();
    PolynomialRing<Integer> polynomialRing = PolynomialRing.instance(integerRing);

    Polynomial<Integer> empty = Polynomial.from(new ArrayList<Integer>());
    Polynomial<Integer> full = Polynomial.from(new ArrayList<Integer>(){{add(1); add(2); add(3);}});
    Polynomial<Integer> fullTwo = Polynomial.from(new ArrayList<Integer>(){{add(4); add(5); add(6);}});
    Polynomial<Integer> fullShort = Polynomial.from(new ArrayList<Integer>(){{add(4); add(5);}});

    @Test
    public void instance() {
        assertThrows(NullPointerException.class, () -> PolynomialRing.instance(null));
    }

    @Test
    public void zero() {
        assertEquals(polynomialRing.zero(), Polynomial.from(new ArrayList<Integer>(){{add(0);}}));
    }

    @Test
    public void identity() {
        assertEquals(polynomialRing.identity(), Polynomial.from(new ArrayList<Integer>(){{add(1);}}));
    }

    @Test
    public void sum() {
        assertThrows(NullPointerException.class, () -> polynomialRing.sum(null, full));
        assertThrows(NullPointerException.class, () -> polynomialRing.sum(full, null));

        assertEquals(polynomialRing.sum(empty, empty), empty);
        assertEquals(polynomialRing.sum(empty, full), full);
        assertEquals(polynomialRing.sum(empty, fullShort), fullShort);

        assertEquals(polynomialRing.sum(full, empty), full);
        assertEquals(polynomialRing.sum(full, fullTwo), Polynomial.from(new ArrayList<Integer>(){{add(5); add(7); add(9);}}));
        assertEquals(polynomialRing.sum(full, fullShort), Polynomial.from(new ArrayList<Integer>(){{add(5); add(7); add(3);}}));

        assertEquals(polynomialRing.sum(fullShort, empty), fullShort);
        assertEquals(polynomialRing.sum(fullShort, full), Polynomial.from(new ArrayList<Integer>(){{add(5); add(7); add(3);}}));
        assertEquals(polynomialRing.sum(fullShort, fullTwo), Polynomial.from(new ArrayList<Integer>(){{add(8); add(10); add(6);}}));
    }

    @Test
    public void product() {
        assertThrows(NullPointerException.class, () -> polynomialRing.product(null, full));
        assertThrows(NullPointerException.class, () -> polynomialRing.product(full, null));

        assertEquals(polynomialRing.product(empty, empty), empty);
        assertEquals(polynomialRing.product(empty, full), empty);
        assertEquals(polynomialRing.product(empty, fullShort), empty);

        assertEquals(polynomialRing.product(full, empty), empty);
        assertEquals(polynomialRing.product(full, fullTwo), Polynomial.from(new ArrayList<Integer>(){{add(4); add(13); add(28); add(27); add(18);}}));
        assertEquals(polynomialRing.product(full, fullShort), Polynomial.from(new ArrayList<Integer>(){{add(4); add(13); add(22); add(15);}}));

        assertEquals(polynomialRing.product(fullShort, empty), empty);
        assertEquals(polynomialRing.product(fullShort, full), Polynomial.from(new ArrayList<Integer>(){{add(4); add(13); add(22); add(15);}}));
        assertEquals(polynomialRing.product(fullShort, fullTwo), Polynomial.from(new ArrayList<Integer>(){{add(16); add(29); add(30); add(15);}}));
    }
}
