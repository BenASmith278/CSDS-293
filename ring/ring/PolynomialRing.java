package ring;

public final class PolynomialRing<T> implements Ring<Polynomial<T>> {
    private PolynomialRing(T type) {
        // throws no exception
        // initialize private Ring of type T
    }

    public static <T> PolynomialRing<T> instance() {
        return null;
    }

    public Polynomial<T> zero() {
        // polynomial with no coefficients
        return null;
    }
    
    public Polynomial<T> identity() {
        // one coefficient equal to identity of base ring
        return null;
    }

    public Polynomial<T> sum(Polynomial<T> x, Polynomial<T> y) {
        // implement polynomial plus
        return null;
    }

    public Polynomial<T> product(Polynomial<T> x, Polynomial<T> y) {
        // implement polynomial times
        return null;
    }
}
