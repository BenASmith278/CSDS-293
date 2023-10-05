package ring;

import static org.junit.Assert.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class IndexesTests {
    Indexes zeroIndex = new Indexes(0, 0);
    Indexes oneIndex = new Indexes(1, 1);
    Indexes nonZeroIndex = new Indexes(1, 2);
    Indexes nullIndex = null;
    Integer[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    Integer[][] emptyMatrix = {};
    Integer[][] nullMatrix = null;
    MatrixMap<Integer> matrixMap = MatrixMap.from(matrix);
    MatrixMap<Integer> nullMatrixMap = null;
    List<Indexes> stream = Stream.of(zeroIndex, new Indexes(0, 1), new Indexes(1, 0), oneIndex)
            .collect(Collectors.toList());

    @Test
    public void value() {
        assertEquals(Integer.valueOf(6), nonZeroIndex.value(matrix));
        assertEquals(Integer.valueOf(1), zeroIndex.value(matrix));
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> zeroIndex.value(emptyMatrix));
        assertThrows(NullPointerException.class, () -> zeroIndex.value(nullMatrix));

        assertEquals(Integer.valueOf(1), zeroIndex.value(matrixMap));
        assertEquals(Integer.valueOf(6), nonZeroIndex.value(matrixMap));
        assertThrows(NullPointerException.class, () -> zeroIndex.value(nullMatrixMap));
    }

    @Test
    public void areDiagonal() {
        assertEquals(true, oneIndex.areDiagonal());
        assertEquals(true, zeroIndex.areDiagonal());
        assertEquals(false, nonZeroIndex.areDiagonal());
    }

    @Test
    public void compareTo() {
        assertThrows(NullPointerException.class, () -> oneIndex.compareTo(nullIndex));
        assertEquals(0, oneIndex.compareTo(oneIndex));
        assertEquals(0, oneIndex.compareTo(oneIndex));
        assertEquals(-1, oneIndex.compareTo(nonZeroIndex));
        assertEquals(1, oneIndex.compareTo(zeroIndex));
    }

    @Test
    public void stream() {
        assertThrows(NullPointerException.class, () -> Indexes.stream(nullIndex, oneIndex));
        assertThrows(NullPointerException.class, () -> Indexes.stream(oneIndex, nullIndex));
        assertEquals(stream, Indexes.stream(zeroIndex, oneIndex).collect(Collectors.toList()));
        assertEquals(stream, Indexes.stream(oneIndex).collect(Collectors.toList()));
        assertEquals(stream, Indexes.stream(1, 1).collect(Collectors.toList()));
    }

    @Test
    public void equals() {
        assertTrue(oneIndex.equals(oneIndex));
        assertTrue(oneIndex.equals(new Indexes(1, 1)));
        assertFalse(zeroIndex.equals(oneIndex));
        assertFalse(zeroIndex.equals(null));
        assertFalse(zeroIndex.equals(matrix));
    }

    @Test
    public void tostring() {
        assertEquals("(0, 0)", zeroIndex.toString());
    }
}
