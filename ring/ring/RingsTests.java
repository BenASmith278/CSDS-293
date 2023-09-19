package ring;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class RingsTests {
    @Test
    public void reduce() {
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertThrows(NullPointerException.class, () -> Rings.reduce(null, 0, (a, b) -> a + b));
        assertThrows(NullPointerException.class, () -> Rings.reduce(list, null, (a, b) -> a + b));
    }

    @Test
    public void sum() {
        Ring<Integer> ring = new IntegerRing();
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(new Integer(6), Rings.sum(list, ring));
        assertEquals(new Integer(0), Rings.sum(Arrays.asList(), ring));
    }

    @Test
    public void product() {
        Ring<Integer> ring = new IntegerRing();
        List<Integer> list = Arrays.asList(1, 2, 3);
        assertEquals(new Integer(6), Rings.product(list, ring));
        assertEquals(new Integer(1), Rings.product(Arrays.asList(), ring));
    }
}
