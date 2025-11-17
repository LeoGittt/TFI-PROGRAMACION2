package service;

import java.util.List;

public interface GenericService<T, ID> {
    void insertar(T entity) throws Exception;
    void actualizar(T entity) throws Exception;
    void eliminar(ID id) throws Exception;
    T getById(ID id) throws Exception;
    List<T> getAll() throws Exception;
}