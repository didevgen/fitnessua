package ua.malibu.ostpc.daos;

public interface IDAO<T> {
    void insert(T t);
    void update (T t);
    void delete(T t);
    T get(String uuid);
}
