package dao;

// Fede: Interfaz genérica para DAOs
public interface GenericDao<T> {
    void crear(T t) throws Exception;
    T leer(long id) throws Exception;
    java.util.List<T> leerTodos() throws Exception;
    void actualizar(T t) throws Exception;
    void eliminar(long id) throws Exception;
    // TODO: Métodos deben aceptar Connection externa si es necesario
}
