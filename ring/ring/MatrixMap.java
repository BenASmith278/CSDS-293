package ring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import ring.MatrixMap.InvalidLengthException.Cause;

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

    public static <S> MatrixMap<S> instance(int rows, int columns, Function<Indexes, S> valueMapper) {
        Indexes size = new Indexes(rows, columns);
        return instance(size, valueMapper);
    }

    public static <S> MatrixMap<S> instance(Indexes size, Function<Indexes, S> valueMapper) {
        Objects.requireNonNull(size);
        Objects.requireNonNull(valueMapper);
        InvalidLengthException.requireNonEmpty(Cause.ROW, size.row());
        InvalidLengthException.requireNonEmpty(Cause.COLUMN, size.column());

        Map<Indexes, S> matrix = Indexes.stream(size)
                .collect(Collectors.toMap(Indexes -> Indexes, values -> valueMapper.apply(values)));
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

    public static void main(String[] args) {
        Integer[][] arr = { { 0, 1, 2 }, { 1, 2, 3 }, { 2, 3, 4} };
        MatrixMap<Integer> one = MatrixMap.from(arr);
        MatrixMap<Integer> two = MatrixMap.from(arr);
        System.out.println(one.equals(two));
    }
}
