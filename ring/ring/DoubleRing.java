package ring;

class DoubleRing implements Ring<Double> {
    public Double zero() {
        return 0.0;
    }

    public Double identity() {
        return 1.0;
    }

    public Double sum(Double x, Double y) {
        if (x == null || y == null)
            throw new NullPointerException("Arguments cannont be null");
        return x + y;
    }

    public Double product(Double x, Double y) {
        if (x == null || y == null)
            throw new NullPointerException("Arguments cannont be null");
        return x * y;
    }
}
