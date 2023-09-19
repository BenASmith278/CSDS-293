package ring;

class BigIntegerRing implements Ring<java.math.BigInteger> {
    public java.math.BigInteger zero() {
        return java.math.BigInteger.ZERO;
    }

    public java.math.BigInteger identity() {
        return java.math.BigInteger.ONE;
    }

    public java.math.BigInteger sum(java.math.BigInteger x, java.math.BigInteger y) {
        if (x == null || y == null)
            throw new NullPointerException("Arguments cannont be null");
        return x.add(y);
    }

    public java.math.BigInteger product(java.math.BigInteger x, java.math.BigInteger y) {
        if (x == null || y == null)
            throw new NullPointerException("Arguments cannont be null");
        return x.multiply(y);
    }
}
