package service;

import entities.Propiedad;
import entities.EscrituraNotarial;
import dao.PropiedadDao;
import dao.EscrituraNotarialDao;
import config.DatabaseConnection;
import java.sql.Connection;
import java.math.BigDecimal;
import java.util.List;

// Gonza: Service para Propiedad con transacciones y validaciones
public class PropiedadService implements GenericService<Propiedad> {
    
    private PropiedadDao propiedadDao;
    private EscrituraNotarialDao escrituraDao;
    
    public PropiedadService() {
        this.propiedadDao = new PropiedadDao();
        this.escrituraDao = new EscrituraNotarialDao();
    }
    
    @Override
    public void insertar(Propiedad propiedad) throws Exception {
        validarPropiedad(propiedad);
        
        // Validar que no exista otra propiedad con el mismo padrón catastral
        Propiedad existente = propiedadDao.buscarPorPadron(propiedad.getPadronCatastral());
        if (existente != null) {
            throw new IllegalArgumentException("Ya existe una propiedad con el padrón catastral: " + propiedad.getPadronCatastral());
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Crear la propiedad primero
            propiedadDao.crear(propiedad, conn);
            
            // Si tiene escritura asociada, crearla
            if (propiedad.getEscrituraNotarial() != null) {
                EscrituraNotarial escritura = propiedad.getEscrituraNotarial();
                
                // Validar que la propiedad no tenga ya una escritura
                EscrituraNotarial escrituraExistente = escrituraDao.buscarPorPropiedadId(propiedad.getId(), conn);
                if (escrituraExistente != null) {
                    throw new IllegalStateException("La propiedad ya tiene una escritura asociada (relación 1→1)");
                }
                
                // Validar escritura
                validarEscritura(escritura);
                
                // Validar unicidad de nroEscritura
                if (escritura.getNroEscritura() != null && !escritura.getNroEscritura().trim().isEmpty()) {
                    EscrituraNotarial escrituraConMismoNumero = escrituraDao.buscarPorNumero(escritura.getNroEscritura(), conn);
                    // ============================================================
                    // PARA DEMO DE ROLLBACK: Comentar las siguientes 3 líneas
                    // PARA CÓDIGO NORMAL: Descomentar las siguientes 3 líneas
                    // ============================================================
                    if (escrituraConMismoNumero != null) {
                        throw new IllegalArgumentException("Ya existe una escritura con el número: " + escritura.getNroEscritura());
                    }
                }
                
                // Asociar la escritura con la propiedad
                escritura.setPropiedadId(propiedad.getId());
                
                // ============================================================
                // PARA DEMO DE ROLLBACK: Descomentar la siguiente línea
                // PARA CÓDIGO NORMAL: Comentar o eliminar la siguiente línea
                // ============================================================
                // throw new RuntimeException("Error simulado para demostrar rollback");
                
                escrituraDao.crear(escritura, conn);
                
                // Actualizar la referencia en la propiedad (opcional, ya está en memoria)
                propiedad.setEscrituraNotarial(escritura);
            }
            
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
    public void actualizar(Propiedad propiedad) throws Exception {
        if (propiedad.getId() == null) {
            throw new IllegalArgumentException("El ID de la propiedad es requerido para actualizar");
        }
        
        // Verificar que existe
        Propiedad existente = getById(propiedad.getId());
        if (existente == null) {
            throw new IllegalArgumentException("No se encontró la propiedad con ID: " + propiedad.getId());
        }
        
        validarPropiedad(propiedad);
        
        // Validar unicidad de padrón catastral (si cambió)
        if (!existente.getPadronCatastral().equals(propiedad.getPadronCatastral())) {
            Propiedad otra = propiedadDao.buscarPorPadron(propiedad.getPadronCatastral());
            if (otra != null) {
                throw new IllegalArgumentException("Ya existe otra propiedad con el padrón catastral: " + propiedad.getPadronCatastral());
            }
        }
        
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);
            
            // Actualizar propiedad
            propiedadDao.actualizar(propiedad, conn);
            
            // Si hay escritura asociada, actualizarla o crearla
            if (propiedad.getEscrituraNotarial() != null) {
                EscrituraNotarial escritura = propiedad.getEscrituraNotarial();
                validarEscritura(escritura);
                
                EscrituraNotarial escrituraExistente = escrituraDao.buscarPorPropiedadId(propiedad.getId(), conn);
                
                if (escrituraExistente != null) {
                    // Actualizar escritura existente
                    if (escritura.getId() == null) {
                        escritura.setId(escrituraExistente.getId());
                    }
                    escritura.setPropiedadId(propiedad.getId());
                    escrituraDao.actualizar(escritura, conn);
                } else {
                    // Crear nueva escritura
                    if (escritura.getNroEscritura() != null && !escritura.getNroEscritura().trim().isEmpty()) {
                        EscrituraNotarial otra = escrituraDao.buscarPorNumero(escritura.getNroEscritura(), conn);
                        if (otra != null) {
                            throw new IllegalArgumentException("Ya existe una escritura con el número: " + escritura.getNroEscritura());
                        }
                    }
                    escritura.setPropiedadId(propiedad.getId());
                    escrituraDao.crear(escritura, conn);
                }
            }
            
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
            
            // Eliminar lógicamente la propiedad
            propiedadDao.eliminar(id, conn);
            
            // Eliminar lógicamente la escritura asociada si existe
            EscrituraNotarial escritura = escrituraDao.buscarPorPropiedadId(id, conn);
            if (escritura != null) {
                escrituraDao.eliminar(escritura.getId(), conn);
            }
            
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
    public Propiedad getById(long id) throws Exception {
        Propiedad propiedad = propiedadDao.leer(id);
        if (propiedad != null) {
            // Cargar escritura asociada
            EscrituraNotarial escritura = escrituraDao.buscarPorPropiedadId(id);
            propiedad.setEscrituraNotarial(escritura);
        }
        return propiedad;
    }
    
    @Override
    public List<Propiedad> getAll() throws Exception {
        List<Propiedad> propiedades = propiedadDao.leerTodos();
        // Cargar escrituras asociadas
        for (Propiedad propiedad : propiedades) {
            EscrituraNotarial escritura = escrituraDao.buscarPorPropiedadId(propiedad.getId());
            propiedad.setEscrituraNotarial(escritura);
        }
        return propiedades;
    }
    
    // Método auxiliar para búsqueda por padrón catastral
    public Propiedad buscarPorPadron(String padronCatastral) throws Exception {
        Propiedad propiedad = propiedadDao.buscarPorPadron(padronCatastral);
        if (propiedad != null) {
            EscrituraNotarial escritura = escrituraDao.buscarPorPropiedadId(propiedad.getId());
            propiedad.setEscrituraNotarial(escritura);
        }
        return propiedad;
    }
    
    private void validarPropiedad(Propiedad propiedad) throws IllegalArgumentException {
        if (propiedad.getPadronCatastral() == null || propiedad.getPadronCatastral().trim().isEmpty()) {
            throw new IllegalArgumentException("El padrón catastral es obligatorio");
        }
        if (propiedad.getPadronCatastral().length() > 30) {
            throw new IllegalArgumentException("El padrón catastral no puede exceder 30 caracteres");
        }
        
        if (propiedad.getDireccion() == null || propiedad.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }
        if (propiedad.getDireccion().length() > 150) {
            throw new IllegalArgumentException("La dirección no puede exceder 150 caracteres");
        }
        
        if (propiedad.getSuperficieM2() == null) {
            throw new IllegalArgumentException("La superficie en m² es obligatoria");
        }
        if (propiedad.getSuperficieM2().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La superficie en m² debe ser mayor a cero");
        }
        
        if (propiedad.getAntiguedad() != null && propiedad.getAntiguedad() < 0) {
            throw new IllegalArgumentException("La antigüedad no puede ser negativa");
        }
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

