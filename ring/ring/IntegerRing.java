package ring;

class IntegerRing implements Ring<Integer> {
    public Integer zero() {
        return 0;
    }

    public Integer identity() {
        return 1;
    }

    public Integer sum(Integer x, Integer y) {
        if (x == null || y == null)
            throw new NullPointerException("Arguments cannont be null");
        return x + y;
    }

    public Integer product(Integer x, Integer y) {
        if (x == null || y == null)
            throw new NullPointerException("Arguments cannont be null");
        return x * y;
    }
}