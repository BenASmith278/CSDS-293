package ring;

import java.util.List;
import java.util.ListIterator;

public final class Polynomial<T> {
    // implement iterable interface
    // iterator.next() returns the polynomial coefficients

    private Polynomial(List<T> coefficients) {
        // assert list not null
        // set coefficients
    }

    public static final <S> Polynomial<S> from(List<S> coefficients) {
        // return new polynomial made from an immutable copy of the coefficients
        return null;
    }

    public <S> Polynomial<S> get() {
        // return a mutable list copied from coefficients
        // override toString() to print coefficients
        return null;
    }

    public ListIterator<T> listIterator(int i) {
        // initial call to next returns p_i
        // initial call to previous returns p_i-1
        return null;
    }

    // for times and plus ---
    // do not iterate through padding coefficients
    // efficient for arrays and linked lists
    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {
        return null;
    }

    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        return null;
    }
}