package dao;

import entities.Propiedad;
import config.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Interfaz DAO para la entidad Propiedad.
 * Define operaciones de acceso a datos específicas para Propiedades.
 */
public class PropiedadDao implements GenericDao<Propiedad> {
    
    @Override
    public void crear(Propiedad propiedad) throws Exception {
        crear(propiedad, null);
    }
    
    @Override
    public void crear(Propiedad propiedad, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "INSERT INTO propiedad (eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, propiedad.getEliminado() != null ? propiedad.getEliminado() : false);
            ps.setString(2, propiedad.getPadronCatastral());
            ps.setString(3, propiedad.getDireccion());
            ps.setBigDecimal(4, propiedad.getSuperficieM2());
            ps.setString(5, propiedad.getDestino() != null ? propiedad.getDestino().name() : null);
            ps.setObject(6, propiedad.getAntiguedad());
            
            ps.executeUpdate();
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    propiedad.setId(rs.getLong(1));
                }
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
    }
    
    @Override
    public Propiedad leer(long id) throws Exception {
        return leer(id, null);
    }
    
    @Override
    public Propiedad leer(long id, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad " +
                     "FROM propiedad WHERE id = ? AND eliminado = FALSE";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPropiedad(rs);
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
    public List<Propiedad> leerTodos() throws Exception {
        return leerTodos(null);
    }
    
    @Override
    public List<Propiedad> leerTodos(Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad " +
                     "FROM propiedad WHERE eliminado = FALSE ORDER BY id";
        
        List<Propiedad> propiedades = new ArrayList<>();
        
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                propiedades.add(mapResultSetToPropiedad(rs));
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
        
        return propiedades;
    }
    
    @Override
    public void actualizar(Propiedad propiedad) throws Exception {
        actualizar(propiedad, null);
    }
    
    @Override
    public void actualizar(Propiedad propiedad, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "UPDATE propiedad SET padronCatastral = ?, direccion = ?, superficieM2 = ?, " +
                     "destino = ?, antiguedad = ? WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, propiedad.getPadronCatastral());
            ps.setString(2, propiedad.getDireccion());
            ps.setBigDecimal(3, propiedad.getSuperficieM2());
            ps.setString(4, propiedad.getDestino() != null ? propiedad.getDestino().name() : null);
            ps.setObject(5, propiedad.getAntiguedad());
            ps.setLong(6, propiedad.getId());
            
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
        
        String sql = "UPDATE propiedad SET eliminado = TRUE WHERE id = ?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
    }
    
    // Método auxiliar para buscar por padrón catastral
    public Propiedad buscarPorPadron(String padronCatastral) throws Exception {
        return buscarPorPadron(padronCatastral, null);
    }
    
    public Propiedad buscarPorPadron(String padronCatastral, Connection conn) throws Exception {
        boolean ownConnection = (conn == null);
        if (ownConnection) {
            conn = DatabaseConnection.getConnection();
        }
        
        String sql = "SELECT id, eliminado, padronCatastral, direccion, superficieM2, destino, antiguedad " +
                     "FROM propiedad WHERE padronCatastral = ? AND eliminado = FALSE";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, padronCatastral);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPropiedad(rs);
                }
            }
        } finally {
            if (ownConnection && conn != null) {
                conn.close();
            }
        }
        return null;
    }
    
    private Propiedad mapResultSetToPropiedad(ResultSet rs) throws SQLException {
        Propiedad propiedad = new Propiedad();
        propiedad.setId(rs.getLong("id"));
        propiedad.setEliminado(rs.getBoolean("eliminado"));
        propiedad.setPadronCatastral(rs.getString("padronCatastral"));
        propiedad.setDireccion(rs.getString("direccion"));
        propiedad.setSuperficieM2(rs.getBigDecimal("superficieM2"));
        
        String destinoStr = rs.getString("destino");
        if (destinoStr != null) {
            propiedad.setDestino(Propiedad.Destino.valueOf(destinoStr));
        }
        
        Integer antiguedad = rs.getObject("antiguedad", Integer.class);
        propiedad.setAntiguedad(antiguedad);
        
        return propiedad;
    }
}

