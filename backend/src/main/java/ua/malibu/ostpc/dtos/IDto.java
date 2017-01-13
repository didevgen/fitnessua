package ua.malibu.ostpc.dtos;

public interface IDto<T> {

    <C extends IDto<T>> C convert(T object);

}
