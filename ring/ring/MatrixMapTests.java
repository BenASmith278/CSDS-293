package ring;

import static org.junit.Assert.*;

import java.math.BigInteger;

import org.junit.Test;

import ring.MatrixMap.InconsistentSizeException;
import ring.MatrixMap.InvalidLengthException;
import ring.MatrixMap.NonSquareException;
import ring.MatrixMap.InvalidLengthException.Cause;

public class MatrixMapTests {
    public Indexes zeroIndex = new Indexes(0, 0);
    public Indexes oneIndex = new Indexes(1, 1);
    public Indexes nonZeroIndex = new Indexes(1, 2);
    public Indexes nullIndex = null;
    public Integer[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    public Integer[][] onesMatrix = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
    public Integer[][] identityMatrix = { { 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 0, 1, 0 },
            { 0, 0, 0, 0, 1 } };
    public Integer[][] functionMatrix = { { 0, 1, 2 }, { 1, 2, 3 }, { 2, 3, 4 } };
    public Integer[][] emptyMatrix = {};
    public Integer[][] nullMatrix = null;
    public BigInteger[][] bigMatrix = { { BigInteger.ZERO, BigInteger.ONE, BigInteger.TWO },
            { BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3) } };
    public MatrixMap<Integer> matrixMap = MatrixMap.from(matrix);
    public MatrixMap<Integer> matrixMapConstant = MatrixMap.constant(2, 1);
    public MatrixMap<Integer> matrixMapIdentity = MatrixMap.identity(4, 0, 1);
    public MatrixMap<Integer> matrixMapNull = null;
    public MatrixMap<BigInteger> bigMatrixMap = MatrixMap.instance(new Indexes(4, 4),
            indexes -> BigInteger.valueOf(indexes.row() + indexes.column()));
    public MatrixMap<BigInteger> bigMatrixMapIdentity = MatrixMap.identity(4, BigInteger.ZERO, BigInteger.ONE);
    public MatrixMap<BigInteger> bigMatrixMapConstant = MatrixMap.constant(2, BigInteger.TWO);
    public MatrixMap<BigInteger> bigMatrixMapRectangular = MatrixMap.from(bigMatrix);
    public MatrixMap<BigInteger> bigMatrixMapRectangularTwo = MatrixMap.from(bigMatrix);
    public MatrixMap<BigInteger> bigMatrixMapNull = null;
    public InvalidLengthException invalidLength = new InvalidLengthException(InvalidLengthException.Cause.ROW, 0);
    public InconsistentSizeException inconsistentSize = new InconsistentSizeException(oneIndex, nonZeroIndex);
    public NonSquareException nonSquare = new NonSquareException(nonZeroIndex);

    public Integer testFunction(Indexes indexes) {
        return indexes.row() + indexes.column();
    }

    @Test
    public void size() {
        assertEquals(new Indexes(2, 2), matrixMapConstant.size());
        assertEquals(new Indexes(4, 4), matrixMapIdentity.size());
    }

    @Test
    public void equals() {
        assertTrue(matrixMap.equals(matrixMap));
        assertTrue(matrixMapConstant.equals(MatrixMap.from(onesMatrix)));
        assertFalse(matrixMap.equals(null));
        assertFalse(matrixMap.equals(matrixMapConstant));
        assertFalse(matrixMap.equals(onesMatrix));
    }

    @Test
    public void tostring() {
        assertEquals("1, 1, 1\n1, 1, 1\n1, 1, 1\n", matrixMapConstant.toString());
        assertEquals("1, 0, 0, 0, 0\n0, 1, 0, 0, 0\n0, 0, 1, 0, 0\n0, 0, 0, 1, 0\n0, 0, 0, 0, 1\n",
                matrixMapIdentity.toString());
    }

    @Test
    public void value() {
        assertEquals(Integer.valueOf(1), matrixMap.value(zeroIndex));
        assertEquals(Integer.valueOf(0), matrixMapIdentity.value(nonZeroIndex));
        assertEquals(Integer.valueOf(3), matrixMap.value(0, 2));
        assertEquals(Integer.valueOf(1), matrixMapIdentity.value(3, 3));

        assertThrows(NullPointerException.class, () -> matrixMap.value(nullIndex));
    }

    @Test
    public void exceptions() {
        assertEquals(Cause.ROW, invalidLength.cause());
        assertEquals(0, invalidLength.length());
        assertThrows(IllegalArgumentException.class, () -> InvalidLengthException.requireNonEmpty(Cause.ROW, 0));

    }

    @Test
    public void instance() {
        assertThrows(NullPointerException.class, () -> MatrixMap.instance(null, (values) -> 1));
        assertThrows(NullPointerException.class, () -> MatrixMap.instance(oneIndex, null));
        assertThrows(IllegalArgumentException.class, () -> MatrixMap.instance(new Indexes(0, 1), (values) -> 1));
        assertThrows(IllegalArgumentException.class, () -> MatrixMap.instance(new Indexes(1, 0), (values) -> 1));

        assertEquals(Integer.valueOf(3),
                MatrixMap.instance(2, 2, (values) -> testFunction(values)).value(new Indexes(1, 2)));
        assertEquals(Integer.valueOf(4),
                MatrixMap.instance(2, 2, (values) -> testFunction(values)).value(new Indexes(2, 2)));

        assertEquals(Integer.valueOf(3),
                MatrixMap.instance(new Indexes(2, 2), (values) -> testFunction(values)).value(new Indexes(1, 2)));
        assertEquals(Integer.valueOf(4),
                MatrixMap.instance(new Indexes(2, 2), (values) -> testFunction(values)).value(new Indexes(2, 2)));
    }

    // throwing exceptions and building map already tested in instance
    @Test
    public void constant() {
        assertEquals(MatrixMap.from(onesMatrix), matrixMapConstant);
    }

    @Test
    public void identity() {
        assertEquals(MatrixMap.from(identityMatrix), matrixMapIdentity);
    }

    @Test
    public void from() {
        assertEquals(MatrixMap.instance(new Indexes(2, 2), (values) -> testFunction(values)),
                MatrixMap.from(functionMatrix));
    }

    @Test
    public void plus() {
    }

    @Test
    public void times() {
    }
}
