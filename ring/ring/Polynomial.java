package ring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public final class Polynomial<T> implements Iterable<T> {
    List<T> coefficients;

    private Polynomial(List<T> coefficients) {
        assert coefficients != null : " coefficients cannot be null";
        this.coefficients = coefficients;
    }

    public static final <S> Polynomial<S> from(List<S> coefficients) {
        Objects.requireNonNull(coefficients);
        return new Polynomial<S>(coefficients);
    }

    @Override
    public String toString() {
        return coefficients.toString();
    }

    public Iterator<T> iterator() {
        return new PolynomialIterator<T>(this);
    }

    public List<T> get() {
        return new ArrayList<T>(coefficients);
    }

    public ListIterator<T> listIterator() {
        return listIterator(0);
    }

    public ListIterator<T> listIterator(int i) {
        Objects.requireNonNull(i);
        ListIterator<T> iterator = coefficients.listIterator(i);
        return iterator;
    }

    public Polynomial<T> plus(Polynomial<T> other, Ring<T> ring) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(ring);

        List<T> result = new ArrayList<T>();
        ListIterator<T> thisIterator = this.listIterator();
        ListIterator<T> otherIterator = other.listIterator();
        while (thisIterator.hasNext() && otherIterator.hasNext()) {
            result.add(ring.sum(thisIterator.next(), otherIterator.next()));
        }
        // check that both polynomials have been exhausted
        while (thisIterator.hasNext()) {
            result.add(thisIterator.next());
        }
        while (otherIterator.hasNext()) {
            result.add(otherIterator.next());
        }
        return Polynomial.from(result);
    }

    public Polynomial<T> times(Polynomial<T> other, Ring<T> ring) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(ring);
        if (this.coefficients.size() == 0 || other.coefficients.size() == 0) {
            return Polynomial.from(new ArrayList<T>());
        }

        List<T> result = new ArrayList<T>();
        T currentTotal;
        for (int i = 1; i < this.coefficients.size() + other.coefficients.size(); i++) {
            currentTotal = ring.zero(); // account for ring zero
            // set iterator start to account for padding coefficients
            int thisStart = Math.min(i, this.coefficients.size());
            int otherStart = Math.max(0, i - this.coefficients.size());

            ListIterator<T> thisIterator = this.listIterator(thisStart);
            ListIterator<T> otherIterator = other.listIterator(otherStart);

            while (thisIterator.hasPrevious() && otherIterator.hasNext()) {
                currentTotal = ring.sum(currentTotal, ring.product(thisIterator.previous(), otherIterator.next()));
            }
            result.add(currentTotal);
        }

        return Polynomial.from(result);
    }
}