package service;

// Gonza: Interfaz genérica para servicios
public interface GenericService<T> {
    void insertar(T t) throws Exception;
    void actualizar(T t) throws Exception;
    void eliminar(long id) throws Exception;
    T getById(long id) throws Exception;
    java.util.List<T> getAll() throws Exception;
}
