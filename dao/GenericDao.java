package dao;

import java.sql.Connection;

/**
 * Interfaz genérica para Data Access Objects (DAOs).
 * Define operaciones CRUD estándar con soporte para transacciones.
 */
public interface GenericDao<T> {
    // Métodos estándar (usan conexión propia)
    void crear(T t) throws Exception;
    T leer(long id) throws Exception;
    java.util.List<T> leerTodos() throws Exception;
    void actualizar(T t) throws Exception;
    void eliminar(long id) throws Exception;
    
    // Métodos con Connection externa (para transacciones)
    void crear(T t, Connection conn) throws Exception;
    T leer(long id, Connection conn) throws Exception;
    java.util.List<T> leerTodos(Connection conn) throws Exception;
    void actualizar(T t, Connection conn) throws Exception;
    void eliminar(long id, Connection conn) throws Exception;
}
