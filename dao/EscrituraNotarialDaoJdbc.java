package dao;

import config.DatabaseConnection;
import entities.EscrituraNotarial;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscrituraNotarialDaoJdbc implements GenericDao<EscrituraNotarial> {

    @Override
    public void crear(EscrituraNotarial e) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(e, conn);
        }
    }

    @Override
    public void crear(EscrituraNotarial e, Connection conn) throws Exception {
        String sql = "INSERT INTO escritura_notarial (eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, Boolean.TRUE.equals(e.getEliminado()));
            ps.setString(2, e.getNroEscritura());
            ps.setDate(3, Date.valueOf(e.getFecha()));
            ps.setString(4, e.getNotaria());
            ps.setString(5, e.getTomo());
            ps.setString(6, e.getFolio());
            ps.setString(7, e.getObservaciones());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    e.setId(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public EscrituraNotarial leer(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leer(id, conn);
        }
    }

    @Override
    public EscrituraNotarial leer(long id, Connection conn) throws Exception {
        String sql = "SELECT id, eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones FROM escritura_notarial WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToEscritura(rs);
                }
            }
        }
        return null;
    }

    @Override
    public java.util.List<EscrituraNotarial> leerTodos() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerTodos(conn);
        }
    }

    @Override
    public java.util.List<EscrituraNotarial> leerTodos(Connection conn) throws Exception {
        List<EscrituraNotarial> lista = new ArrayList<>();
        String sql = "SELECT id, eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones FROM escritura_notarial";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToEscritura(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(EscrituraNotarial e) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(e, conn);
        }
    }

    @Override
    public void actualizar(EscrituraNotarial e, Connection conn) throws Exception {
        String sql = "UPDATE escritura_notarial SET eliminado = ?, nro_escritura = ?, fecha = ?, notaria = ?, tomo = ?, folio = ?, observaciones = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, Boolean.TRUE.equals(e.getEliminado()));
            ps.setString(2, e.getNroEscritura());
            ps.setDate(3, Date.valueOf(e.getFecha()));
            ps.setString(4, e.getNotaria());
            ps.setString(5, e.getTomo());
            ps.setString(6, e.getFolio());
            ps.setString(7, e.getObservaciones());
            ps.setLong(8, e.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            eliminar(id, conn);
        }
    }

    @Override
    public void eliminar(long id, Connection conn) throws Exception {
        String sql = "UPDATE escritura_notarial SET eliminado = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    private EscrituraNotarial mapRowToEscritura(ResultSet rs) throws SQLException {
        EscrituraNotarial e = new EscrituraNotarial();
        e.setId(rs.getLong("id"));
        e.setEliminado(rs.getBoolean("eliminado"));
        e.setNroEscritura(rs.getString("nro_escritura"));
        if (rs.getDate("fecha") != null) {
            e.setFecha(rs.getDate("fecha").toLocalDate());
        }
        e.setNotaria(rs.getString("notaria"));
        e.setTomo(rs.getString("tomo"));
        e.setFolio(rs.getString("folio"));
        e.setObservaciones(rs.getString("observaciones"));
        return e;
    }
}
