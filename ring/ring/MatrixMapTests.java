package ring;

import static org.junit.Assert.*;

import java.math.BigInteger;
import java.util.List;
import java.util.function.BinaryOperator;

import org.junit.Test;

import ring.MatrixMap.InconsistentSizeException;
import ring.MatrixMap.InvalidLengthException;
import ring.MatrixMap.NonSquareException;
import ring.MatrixMap.InvalidLengthException.Cause;

public class MatrixMapTests {
    Indexes zeroIndex = new Indexes(0, 0);
    Indexes oneIndex = new Indexes(1, 1);
    Indexes nonZeroIndex = new Indexes(1, 2);
    Indexes nullIndex = null;

    Polynomial<BigInteger> polynomialOne = Polynomial.from(List.of(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE));
    Polynomial<BigInteger> polynomialTwo = Polynomial.from(List.of(BigInteger.TWO, BigInteger.TWO, BigInteger.TWO));
    Polynomial<BigInteger> polynomialThree = Polynomial.from(List.of(BigInteger.ZERO, BigInteger.ONE));
    Polynomial<BigInteger> polynomialFour = Polynomial.from(List.of(BigInteger.ONE, BigInteger.TWO));
    Polynomial<BigInteger> polynomialNull = null;
    PolynomialRing<BigInteger> polynomialRing = PolynomialRing.instance(new BigIntegerRing());

    List<Polynomial<BigInteger>> polynomialList = List.of(polynomialOne);

    Integer[][] matrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
    Integer[][] onesMatrix = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
    Integer[][] identityMatrix = { { 1, 0, 0, 0, 0 }, { 0, 1, 0, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 0, 1, 0 },
            { 0, 0, 0, 0, 1 } };
    Integer[][] functionMatrix = { { 0, 1, 2 }, { 1, 2, 3 }, { 2, 3, 4 } };
    Integer[][] emptyMatrix = {};
    Integer[][] nullMatrix = null;

    BigInteger[][] bigMatrix = { { BigInteger.ZERO, BigInteger.ONE, BigInteger.TWO },
            { BigInteger.ONE, BigInteger.TWO, BigInteger.valueOf(3) } };
    
    MatrixMap<Integer> matrixMap = MatrixMap.from(matrix);
    MatrixMap<Integer> matrixMapConstant = MatrixMap.constant(2, 1);
    MatrixMap<Integer> matrixMapIdentity = MatrixMap.identity(4, 0, 1);
    MatrixMap<Integer> matrixMapNull = null;

    MatrixMap<BigInteger> bigMatrixMap = MatrixMap.instance(new Indexes(4, 4),
            indexes -> BigInteger.valueOf(indexes.row() + indexes.column()));
    MatrixMap<BigInteger> bigMatrixMapIdentity = MatrixMap.identity(4, BigInteger.ZERO, BigInteger.ONE);
    MatrixMap<BigInteger> bigMatrixMapConstantOne = MatrixMap.constant(2, BigInteger.ONE);
    MatrixMap<BigInteger> bigMatrixMapConstantTwo = MatrixMap.constant(2, BigInteger.TWO);
    MatrixMap<BigInteger> bigMatrixMapRectangular = MatrixMap.from(bigMatrix);
    MatrixMap<BigInteger> bigMatrixMapRectangularTwo = MatrixMap.from(bigMatrix);
    MatrixMap<BigInteger> bigMatrixMapNull = null;

    MatrixMap<Polynomial<BigInteger>> bigPolyMatrixMap = MatrixMap.instance(new Indexes(2, 2),
            indexes -> Polynomial.from(List.of(BigInteger.valueOf(indexes.row() + indexes.column()), BigInteger.ONE)));
    MatrixMap<Polynomial<BigInteger>> bigPolyMatrixMapIdentity = MatrixMap.identity(2, polynomialRing.zero(),
            polynomialRing.identity());
    MatrixMap<Polynomial<BigInteger>> bigPolyMatrixMapConstantOne = MatrixMap.constant(2, polynomialOne);
    MatrixMap<Polynomial<BigInteger>> bigPolyMatrixMapConstantTwo = MatrixMap.constant(2, polynomialTwo);

    InvalidLengthException invalidLength = new InvalidLengthException(InvalidLengthException.Cause.ROW, 0);
    InconsistentSizeException inconsistentSize = new InconsistentSizeException(oneIndex, nonZeroIndex);
    NonSquareException nonSquare = new NonSquareException(nonZeroIndex);

    BinaryOperator<Polynomial<BigInteger>> addPolynomials = (p1, p2) -> p1.plus(p2, new BigIntegerRing());

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
        assertThrows(NullPointerException.class, () -> bigMatrixMap.plus(bigMatrixMap, null));
        assertThrows(NullPointerException.class, () -> bigMatrixMap.plus(bigMatrixMapNull, BigInteger::add));
        assertThrows(IllegalArgumentException.class, () -> bigMatrixMap.plus(bigMatrixMapConstantTwo, BigInteger::add));

        assertEquals(bigMatrixMapConstantTwo, bigMatrixMapConstantOne.plus(bigMatrixMapConstantOne, BigInteger::add));
        assertEquals(bigPolyMatrixMapConstantTwo, bigPolyMatrixMapConstantOne.plus(bigPolyMatrixMapConstantOne, addPolynomials));
    }

    @Test
    public void times() {
        assertThrows(NullPointerException.class, () -> bigMatrixMap.times(bigMatrixMapNull, new BigIntegerRing()));
        assertThrows(NullPointerException.class, () -> bigMatrixMap.times(bigMatrixMapIdentity, null));
        assertThrows(IllegalArgumentException.class,
                () -> bigMatrixMap.times(bigMatrixMapConstantTwo, new BigIntegerRing()));
        assertThrows(IllegalArgumentException.class,
                () -> bigMatrixMapRectangularTwo.times(bigMatrixMapRectangular, new BigIntegerRing()));

        assertEquals(bigMatrixMap, bigMatrixMap.times(bigMatrixMapIdentity, new BigIntegerRing()));
        assertEquals(bigPolyMatrixMap, bigPolyMatrixMap.times(bigPolyMatrixMapIdentity, polynomialRing));
    }
}
