package ring;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public record Indexes(int row, int column) implements Comparable<Indexes> {
    public <S> S value(S[][] matrix) {
        Objects.requireNonNull(matrix);
        return matrix[row][column];
    }

    public <T> T value(MatrixMap<T> matrix) {
        Objects.requireNonNull(matrix);
        return matrix.value(this);
    }

    public boolean areDiagonal() {
        return (row == column);
    }

    public int compareTo(Indexes other) {
        Objects.requireNonNull(other);
        Comparator<Indexes> comparator = Comparator.comparing(Indexes::row)
                .thenComparing(Indexes::column);
        return comparator.compare(this, other);
    }

    public static Stream<Indexes> stream(Indexes from, Indexes to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        Stream.Builder<Indexes> builder = Stream.builder();
        for (int i = from.row; i <= to.row; i++) {
            for (int j = from.column; j <= to.column; j++) {
                builder.accept(new Indexes(i, j));
            }
        }
        return builder.build();
    }

    public static Stream<Indexes> stream(Indexes size) {
        return stream(new Indexes(0, 0), size);
    }

    public static Stream<Indexes> stream(int rows, int columns) {
        return stream(new Indexes(0, 0), new Indexes(rows, columns));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Indexes)) {
            return false;
        }
        Indexes other = (Indexes) obj;
        return compareTo(other) == 0;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, column);
    }
}
