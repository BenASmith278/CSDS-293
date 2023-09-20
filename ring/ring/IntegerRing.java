package ring;

import java.util.Objects;

class IntegerRing implements Ring<Integer> {
    public Integer zero() {
        return 0;
    }

    public Integer identity() {
        return 1;
    }

    public Integer sum(Integer x, Integer y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x + y;
    }

    public Integer product(Integer x, Integer y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x * y;
    }
}