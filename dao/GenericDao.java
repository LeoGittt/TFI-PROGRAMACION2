package dao;

import java.sql.Connection;
import java.util.List;

/**
 * Interfaz genérica para DAOs.
 *
 * Se definen sobrecargas que aceptan una Connection externa para permitir
 * que los Services orquesten transacciones sobre la misma conexión.
 */
public interface GenericDao<T> {
    void crear(T t) throws Exception;
    void crear(T t, Connection conn) throws Exception;

    T leer(long id) throws Exception;
    T leer(long id, Connection conn) throws Exception;

    List<T> leerTodos() throws Exception;
    List<T> leerTodos(Connection conn) throws Exception;

    void actualizar(T t) throws Exception;
    void actualizar(T t, Connection conn) throws Exception;

    void eliminar(long id) throws Exception;
    void eliminar(long id, Connection conn) throws Exception;
}
