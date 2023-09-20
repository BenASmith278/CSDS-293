package ring;

import java.util.Iterator;
import java.util.List;

public class PolynomialIterator<T> implements Iterator<T> {
    List<T> coefficients;
    int index;

    PolynomialIterator(Polynomial<T> polynomial) {
        coefficients = polynomial.coefficients;
        index = 0;
    }

    public boolean hasNext() {
        return index < coefficients.size();
    }

    public T next() {
        return coefficients.get(index++);
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
