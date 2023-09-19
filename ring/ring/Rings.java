package ring;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.Objects;

public final class Rings<T> {
    public static <T> T reduce(List<T> args, T zero, BinaryOperator<T> accumulator) {
        Objects.requireNonNull(args);
        Objects.requireNonNull(zero);

        boolean foundAny = false;
        T result = zero;

        for (T element : args) {
            if (!foundAny) {
                foundAny = true;
                result = element;
            } else {
                result = accumulator.apply(element, result);
            }
        }
        return result;
    }

    public static final <T> T sum(List<T> args, Ring<T> ring) {
        if (args.size() == 0)
            return ring.zero();

        return reduce(args, ring.zero(), ring::sum);
    }

    public static final <T> T product(List<T> args, Ring<T> ring) {
        if (args.size() == 0)
            return ring.identity();

        return reduce(args, ring.identity(), ring::product);
    }
}
