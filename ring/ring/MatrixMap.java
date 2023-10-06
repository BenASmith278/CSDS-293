package ring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MatrixMap<T> {
    private final Map<Indexes, T> matrix;

    private MatrixMap(Map<Indexes, T> matrix) {
        assert (matrix != null);
        this.matrix = matrix;
    }

    public Indexes size() {
        return Collections.max(matrix.keySet());
    }

    @Override
    public String toString() {
        // print matrix values in shape of matrix
        StringBuilder out = new StringBuilder();
        int size = size().row();
        // sort matrix for proper printing
        ArrayList<Indexes> sortedMatrix = new ArrayList<>(matrix.keySet());
        Collections.sort(sortedMatrix);
        for (Indexes indexes : sortedMatrix) {
            out.append(value(indexes));
            if (indexes.column() < size) {
                out.append(", ");
            } else {
                out.append("\n");
            }
        }

        return out.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MatrixMap)) {
            return false;
        }

        MatrixMap<T> other = (MatrixMap<T>) obj;
        return other.toString().equals(this.toString());
    }

    @Override
    public int hashCode() {
        return matrix.hashCode();
    }

    public T value(Indexes indexes) {
        Objects.requireNonNull(indexes);
        return matrix.get(indexes);
    }

    public T value(int column, int row) {
        Indexes indexes = new Indexes(column, row);
        return value(indexes);
    }

    static class InvalidLengthException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private Cause cause;
        private int length;

        public enum Cause {
            ROW, COLUMN
        };

        public InvalidLengthException(Cause cause, int length) {
            this.cause = cause;
            this.length = length;
        }

        public Cause cause() {
            return cause;
        }

        public int length() {
            return length;
        }

        public static int requireNonEmpty(Cause cause, int length) {
            if (length <= 0) {
                throw new IllegalArgumentException(new InvalidLengthException(cause, length));
            }

            return length;
        }
    }

    static class InconsistentSizeException extends RuntimeException {
        private Indexes thisIndexes;
        private Indexes otherIndexes;

        public InconsistentSizeException(Indexes thisIndexes, Indexes otherIndexes) {
            this.thisIndexes = thisIndexes;
            this.otherIndexes = otherIndexes;
        }

        public Indexes getThisIndexes() {
            return thisIndexes;
        }

        public Indexes getOtherIndexes() {
            return otherIndexes;
        }

        public static <T> Indexes requireMatchingSize(MatrixMap<T> thisMatrix, MatrixMap<T> otherMatrix) {
            if (thisMatrix.size() != otherMatrix.size()) {
                throw new IllegalArgumentException(
                        new InconsistentSizeException(thisMatrix.size(), otherMatrix.size()));
            }
            return thisMatrix.size();
        }
    }

    static class nonSquareException extends RuntimeException {
        private final Indexes indexes;

        public nonSquareException(Indexes indexes) {
            this.indexes = indexes;
        }

        public Indexes getIndexes() {
            return indexes;
        }

        public static Indexes requireDiagonal(Indexes indexes) {
            if (!(indexes.areDiagonal())) {
                throw new IllegalArgumentException(new nonSquareException(indexes));
            }
            return indexes;
        }
    }

    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {
        Indexes size = new Indexes(rows, columns);
        return instance(size, valueMapper);
    }

    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        Objects.requireNonNull(size);
        Objects.requireNonNull(valueMapper);
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.ROW, size.row());
        InvalidLengthException.requireNonEmpty(InvalidLengthException.Cause.COLUMN, size.column());

        Map<Indexes, S> matrix = Indexes.stream(size)
                .collect(Collectors.toMap(indexes -> indexes, indexes -> valueMapper.apply(indexes)));
        return new MatrixMap<S>(matrix);
    }

    public static <S> MatrixMap<S> constant(int size, S value) {
        return instance(size, size, (indexes) -> value);
    }

    public static <S> MatrixMap<S> identity(int size, S zero, S identity) {
        return instance(size, size, (indexes) -> {
            if (indexes.areDiagonal()) {
                return identity;
            }
            return zero;
        });
    }

    public static <S> MatrixMap<S> from(S[][] matrix) {
        Indexes indexes = new Indexes(matrix.length - 1, matrix[0].length - 1);
        return instance(indexes, (values) -> matrix[values.row()][values.column()]);
    }

    public MatrixMap<T> plus(MatrixMap<T> other, BinaryOperator<T> plus) {
        Objects.requireNonNull(other);
        Objects.requireNonNull(plus);
        InconsistentSizeException.requireMatchingSize(this, other);

        return instance(this.size(), indexes -> plus.apply(this.value(indexes), other.value(indexes)));
    }

    public MatrixMap<T> times(MatrixMap<T> other, Ring<T> ring) {
        Objects.requireNonNull(ring);
        Objects.requireNonNull(other);
        InconsistentSizeException.requireMatchingSize(this, other);
        nonSquareException.requireDiagonal(this.size());
        nonSquareException.requireDiagonal(other.size());

        return instance(this.size(), indexes -> ring.product(this.value(indexes), other.value(indexes)));
    }
}
