package service;

import entities.EscrituraNotarial;
import dao.EscrituraNotarialDao;
import dao.PropiedadDao;
import config.DatabaseConnection;
import java.sql.Connection;
import java.util.List;

/**
 * Service para gestionar Escrituras Notariales con transacciones y validaciones de negocio.
 */
public class EscrituraNotarialService implements GenericService<EscrituraNotarial> {
    
    private EscrituraNotarialDao escrituraDao;
    private PropiedadDao propiedadDao;
    
    public EscrituraNotarialService() {
        this.escrituraDao = new EscrituraNotarialDao();
        this.propiedadDao = new PropiedadDao();
    }
    
    @Override
    public void insertar(EscrituraNotarial escritura) throws Exception {
        if (escritura.getPropiedadId() == null) {
            throw new IllegalArgumentException("La escritura debe estar asociada a una propiedad (propiedad_id es obligatorio)");
        }
        
        // Verificar que la propiedad existe
        if (propiedadDao.leer(escritura.getPropiedadId()) == null) {
            throw new IllegalArgumentException("No se encontró la propiedad con ID: " + escritura.getPropiedadId());
        }
        
        validarEscritura(escritura);
        
        // Validar regla 1→1: no puede haber otra escritura para esta propiedad
        EscrituraNotarial existente = escrituraDao.buscarPorPropiedadId(escritura.getPropiedadId());
        if (existente != null) {
            throw new IllegalStateException("La propiedad ya tiene una escritura asociada (relación 1→1). No se puede crear otra.");
        }
        
        // Validar unicidad de nroEscritura
        if (escritura.getNroEscritura() != null && !escritura.getNroEscritura().trim().isEmpty()) {
            EscrituraNotarial otra = escrituraDao.buscarPorNumero(escritura.getNroEscritura());
            if (otra != null) {
                throw new IllegalArgumentException("Ya existe una escritura con el número: " + escritura.getNroEscritura());
            }
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            escrituraDao.crear(escritura, conn);
            
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    @Override
    public void actualizar(EscrituraNotarial escritura) throws Exception {
        if (escritura.getId() == null) {
            throw new IllegalArgumentException("El ID de la escritura es requerido para actualizar");
        }
        
        // Verificar que existe
        EscrituraNotarial existente = getById(escritura.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró la escritura con ID: " + escritura.getId());
        }
        
        validarEscritura(escritura);
        
        // Si cambió el número de escritura, validar unicidad
        if (escritura.getNroEscritura() != null && !escritura.getNroEscritura().trim().isEmpty()) {
            if (!escritura.getNroEscritura().equals(existente.getNroEscritura())) {
                EscrituraNotarial otra = escrituraDao.buscarPorNumero(escritura.getNroEscritura());
                if (otra != null) {
                    throw new IllegalArgumentException("Ya existe otra escritura con el número: " + escritura.getNroEscritura());
                }
            }
        }
        
        // Mantener el propiedad_id original
        if (escritura.getPropiedadId() == null) {
            escritura.setPropiedadId(existente.getPropiedadId());
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            escrituraDao.actualizar(escritura, conn);
            
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    @Override
    public void eliminar(long id) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            escrituraDao.eliminar(id, conn);
            
            conn.commit();
        } catch (Exception e) {
            if (conn != null) {
                conn.rollback();
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }
    
    @Override
    public EscrituraNotarial getById(long id) throws Exception {
        return escrituraDao.leer(id);
    }
    
    @Override
    public List<EscrituraNotarial> getAll() throws Exception {
        return escrituraDao.leerTodos();
    }
    
    // Método auxiliar para búsqueda por número de escritura
    public EscrituraNotarial buscarPorNumero(String nroEscritura) throws Exception {
        return escrituraDao.buscarPorNumero(nroEscritura);
    }
    
    // Método auxiliar para buscar por propiedad
    public EscrituraNotarial buscarPorPropiedadId(long propiedadId) throws Exception {
        return escrituraDao.buscarPorPropiedadId(propiedadId);
    }
    
    private void validarEscritura(EscrituraNotarial escritura) throws IllegalArgumentException {
        if (escritura.getFecha() == null) {
            throw new IllegalArgumentException("La fecha de la escritura es obligatoria");
        }
        
        if (escritura.getNroEscritura() != null && escritura.getNroEscritura().length() > 30) {
            throw new IllegalArgumentException("El número de escritura no puede exceder 30 caracteres");
        }
        
        if (escritura.getNotaria() != null && escritura.getNotaria().length() > 120) {
            throw new IllegalArgumentException("El nombre de la notaría no puede exceder 120 caracteres");
        }
        
        if (escritura.getTomo() != null && escritura.getTomo().length() > 10) {
            throw new IllegalArgumentException("El tomo no puede exceder 10 caracteres");
        }
        
        if (escritura.getFolio() != null && escritura.getFolio().length() > 10) {
            throw new IllegalArgumentException("El folio no puede exceder 10 caracteres");
        }
        
        if (escritura.getObservaciones() != null && escritura.getObservaciones().length() > 255) {
            throw new IllegalArgumentException("Las observaciones no pueden exceder 255 caracteres");
        }
    }
}

