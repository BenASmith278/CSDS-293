package ring;

import java.util.Objects;
import java.math.BigInteger;

class BigIntegerRing implements Ring<BigInteger> {
    public BigInteger zero() {
        return BigInteger.ZERO;
    }

    public BigInteger identity() {
        return BigInteger.ONE;
    }

    public BigInteger sum(BigInteger x, BigInteger y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x.add(y);
    }

    public BigInteger product(BigInteger x, BigInteger y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x.multiply(y);
    }
}
