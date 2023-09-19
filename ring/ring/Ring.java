package ring;

public interface Ring<T> {
    public T zero();

    public T identity();

    public T sum(T x, T y);

    public T product(T x, T y);
}
