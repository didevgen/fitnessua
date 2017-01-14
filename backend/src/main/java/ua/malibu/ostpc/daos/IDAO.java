package ua.malibu.ostpc.daos;

/**
 * Created by Eugne on 14.01.2017.
 */
public interface IDAO<T> {
    void insert(T t);
    void update (T t);
    void delete(T t);
    T get(String uuid);
}
