package ring;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class PolynomialRing<T> implements Ring<Polynomial<T>> {
    private Ring<T> baseRing;

    private PolynomialRing(Ring<T> ring) {
        assert ring != null : " ring cannot be null";
        this.baseRing = ring;
    }

    public static <T> PolynomialRing<T> instance(Ring<T> ring) {
        Objects.requireNonNull(ring);
        return new PolynomialRing<T>(ring);
    }

    public Polynomial<T> zero() {
        return Polynomial.from(new ArrayList<T>(){{add(baseRing.zero());}});
    }

    public Polynomial<T> identity() {
        List<T> identity = new ArrayList<T>();
        identity.add(baseRing.identity());
        return Polynomial.from(identity);
    }

    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x.plus(y, baseRing);
    }

    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        Objects.requireNonNull(x);
        Objects.requireNonNull(y);
        return x.times(y, baseRing);
    }
}
