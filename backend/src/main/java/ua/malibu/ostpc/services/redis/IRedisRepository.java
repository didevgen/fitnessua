package ua.malibu.ostpc.services.redis;

public interface IRedisRepository<K, V> {
    void insert(K key, V value);
    void delete(K key);
    void refreshExpirationTime(K key);
}
