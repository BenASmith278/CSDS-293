package ring;

import java.util.Objects;

class DoubleRing implements Ring<Double> {
    public Double zero() {
        return 0.0;
    }

    public Double identity() {
        return 1.0;
    }

    public Double sum(Double x, Double y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x + y;
    }

    public Double product(Double x, Double y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x * y;
    }
}
