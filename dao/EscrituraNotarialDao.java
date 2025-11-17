package dao;

import entities.EscrituraNotarial;
import config.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Fede: DAO concreto para EscrituraNotarial
public class EscrituraNotarialDao implements GenericDao<EscrituraNotarial> {
    
    @Override
    public void crear(EscrituraNotarial escritura) throws Exception {
        crear(escritura, null);
    }
    
    @Override
    public void crear(EscrituraNotarial escritura, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "INSERT INTO escrituraNotarial (eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, escritura.getEliminado() != null ? escritura.getEliminado() : false);
            ps.setString(2, escritura.getNroEscritura());
            ps.setDate(3, escritura.getFecha() != null ? Date.valueOf(escritura.getFecha()) : null);
            ps.setString(4, escritura.getNotaria());
            ps.setString(5, escritura.getTomo());
            ps.setString(6, escritura.getFolio());
            ps.setString(7, escritura.getObservaciones());
            
            // Obtener el ID de la propiedad asociada
            if (escritura.getPropiedadId() == null) {
                throw new IllegalArgumentException("EscrituraNotarial debe tener una propiedad asociada (propiedad_id)");
            }
            ps.setLong(8, escritura.getPropiedadId());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    escritura.setId(rs.getLong(1));
                }
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
    }
    
    @Override
    public EscrituraNotarial leer(long id) throws Exception {
        return leer(id, null);
    }
    
    @Override
    public EscrituraNotarial leer(long id, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id " +
                     "FROM escrituraNotarial WHERE id = ? AND eliminado = FALSE";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEscritura(rs);
                }
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
        return null;
    }
    
    @Override
    public List<EscrituraNotarial> leerTodos() throws Exception {
        return leerTodos(null);
    }
    
    @Override
    public List<EscrituraNotarial> leerTodos(Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id " +
                     "FROM escrituraNotarial WHERE eliminado = FALSE ORDER BY id";
        
        List<EscrituraNotarial> escrituras = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                escrituras.add(mapResultSetToEscritura(rs));
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
        
        return escrituras;
    }
    
    @Override
    public void actualizar(EscrituraNotarial escritura) throws Exception {
        actualizar(escritura, null);
    }
    
    @Override
    public void actualizar(EscrituraNotarial escritura, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "UPDATE escrituraNotarial SET nroEscritura = ?, fecha = ?, notaria = ?, " +
                     "tomo = ?, folio = ?, observaciones = ? WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, escritura.getNroEscritura());
            ps.setDate(2, escritura.getFecha() != null ? Date.valueOf(escritura.getFecha()) : null);
            ps.setString(3, escritura.getNotaria());
            ps.setString(4, escritura.getTomo());
            ps.setString(5, escritura.getFolio());
            ps.setString(6, escritura.getObservaciones());
            ps.setLong(7, escritura.getId());
            
            ps.executeUpdate();
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
    }
    
    @Override
    public void eliminar(long id) throws Exception {
        eliminar(id, null);
    }
    
    @Override
    public void eliminar(long id, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "UPDATE escrituraNotarial SET eliminado = TRUE WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
    }
    
    // Método auxiliar para buscar por número de escritura
    public EscrituraNotarial buscarPorNumero(String nroEscritura) throws Exception {
        return buscarPorNumero(nroEscritura, null);
    }
    
    public EscrituraNotarial buscarPorNumero(String nroEscritura, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id " +
                     "FROM escrituraNotarial WHERE nroEscritura = ? AND eliminado = FALSE";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nroEscritura);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEscritura(rs);
                }
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
        return null;
    }
    
    // Método para buscar escritura por propiedad_id
    public EscrituraNotarial buscarPorPropiedadId(long propiedadId) throws Exception {
        return buscarPorPropiedadId(propiedadId, null);
    }
    
    public EscrituraNotarial buscarPorPropiedadId(long propiedadId, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, nroEscritura, fecha, notaria, tomo, folio, observaciones, propiedad_id " +
                     "FROM escrituraNotarial WHERE propiedad_id = ? AND eliminado = FALSE";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, propiedadId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEscritura(rs);
                }
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
        return null;
    }
    
    private EscrituraNotarial mapResultSetToEscritura(ResultSet rs) throws SQLException {
        EscrituraNotarial escritura = new EscrituraNotarial();
        escritura.setId(rs.getLong("id"));
        escritura.setEliminado(rs.getBoolean("eliminado"));
        escritura.setNroEscritura(rs.getString("nroEscritura"));
        
        Date fecha = rs.getDate("fecha");
        if (fecha != null) {
            escritura.setFecha(fecha.toLocalDate());
        }
        
        escritura.setNotaria(rs.getString("notaria"));
        escritura.setTomo(rs.getString("tomo"));
        escritura.setFolio(rs.getString("folio"));
        escritura.setObservaciones(rs.getString("observaciones"));
        
        // Guardar propiedad_id para uso interno
        escritura.setPropiedadId(rs.getLong("propiedad_id"));
        
        return escritura;
    }
}

