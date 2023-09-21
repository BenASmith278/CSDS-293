package ring;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.ListIterator;

import org.junit.Test;

public class PolynomialTests {
    Polynomial<Integer> empty = Polynomial.from(new ArrayList<Integer>());
    Polynomial<Integer> full = Polynomial.from(new ArrayList<Integer>(){{add(1); add(2); add(3);}});
    Polynomial<Integer> fullTwo = Polynomial.from(new ArrayList<Integer>(){{add(4); add(5); add(6);}});
    Polynomial<Integer> fullShort = Polynomial.from(new ArrayList<Integer>(){{add(4); add(5);}});

    @Test
    public void from() {
        assertThrows(NullPointerException.class, () -> Polynomial.from(null));
    }

    @Test
    public void tostring() {
        assertEquals(empty.toString(), "[]");
        assertEquals(full.toString(), "[1, 2, 3]");
    }

    @Test
    public void equals() {
        assertEquals(empty, empty);
        assertEquals(full, full);
        assertEquals(full, Polynomial.from(new ArrayList<Integer>(){{add(1); add(2); add(3);}}));
        assertNotEquals(empty, full);
        assertNotEquals(full, empty);
    }

    @Test
    public void get() {
        assertEquals(new ArrayList<Integer>(), empty.get());
        assertEquals(new ArrayList<Integer>(){{add(1); add(2); add(3);}}, full.get());
    }

    @Test
    public void listIterator() {
        ListIterator<Integer> iterator = full.listIterator(0);
        assertEquals(iterator.next(), Integer.valueOf(1));
        assertEquals(iterator.hasNext(), true);
        assertEquals(iterator.hasPrevious(), true);
        assertEquals(iterator.previous(), Integer.valueOf(1));
        
        // check overloaded method
        iterator = full.listIterator();
        assertEquals(iterator.next(), Integer.valueOf(1));
    }

    @Test
    public void plus() {
        assertThrows(NullPointerException.class, () -> full.plus(null, new IntegerRing()));
        assertThrows(NullPointerException.class, () -> full.plus(fullTwo, null));

        assertEquals(empty.plus(empty, new IntegerRing()), empty);
        assertEquals(empty.plus(full, new IntegerRing()), full);
        assertEquals(empty.plus(fullShort, new IntegerRing()), fullShort);

        assertEquals(full.plus(empty, new IntegerRing()), full);
        assertEquals(full.plus(fullTwo, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(5); add(7); add(9);}}));
        assertEquals(full.plus(fullShort, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(5); add(7); add(3);}}));

        assertEquals(fullShort.plus(empty, new IntegerRing()), fullShort);
        assertEquals(fullShort.plus(full, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(5); add(7); add(3);}}));
        assertEquals(fullShort.plus(fullTwo, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(8); add(10); add(6);}}));
    }

    @Test
    public void times() {
        assertThrows(NullPointerException.class, () -> full.times(null, new IntegerRing()));
        assertThrows(NullPointerException.class, () -> full.times(fullTwo, null));

        assertEquals(empty.times(empty, new IntegerRing()), empty);
        assertEquals(empty.times(full, new IntegerRing()), empty);
        assertEquals(empty.times(fullShort, new IntegerRing()), empty);

        assertEquals(full.times(empty, new IntegerRing()), empty);
        assertEquals(full.times(fullTwo, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(4); add(13); add(28); add(27); add(18);}}));
        assertEquals(full.times(fullShort, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(4); add(13); add(22); add(15);}}));

        assertEquals(fullShort.times(empty, new IntegerRing()), empty);
        assertEquals(fullShort.times(full, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(4); add(13); add(22); add(15);}}));
        assertEquals(fullShort.times(fullTwo, new IntegerRing()), Polynomial.from(new ArrayList<Integer>(){{add(16); add(40); add(49); add(30);}}));
    }
}
