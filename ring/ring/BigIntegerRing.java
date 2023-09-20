package ring;

import java.util.Objects;

class BigIntegerRing implements Ring<java.math.BigInteger> {
    public java.math.BigInteger zero() {
        return java.math.BigInteger.ZERO;
    }

    public java.math.BigInteger identity() {
        return java.math.BigInteger.ONE;
    }

    public java.math.BigInteger sum(java.math.BigInteger x, java.math.BigInteger y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x.add(y);
    }

    public java.math.BigInteger product(java.math.BigInteger x, java.math.BigInteger y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x.multiply(y);
    }
}
