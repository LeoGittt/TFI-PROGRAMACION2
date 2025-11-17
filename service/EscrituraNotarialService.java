package service;

import config.DatabaseConnection;
import entities.EscrituraNotarial;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EscrituraNotarialService implements GenericService<EscrituraNotarial, Long> {

    private final Connection conn;

    public EscrituraNotarialService() throws Exception {
        this.conn = DatabaseConnection.getConnection();
    }

    // ---------- VALIDACIONES ----------
    private void validarEscritura(EscrituraNotarial e) throws Exception {
        if (e == null) {
            throw new Exception("La escritura no puede ser null.");
        }
        if (e.getNroEscritura() == null || e.getNroEscritura().trim().isEmpty()) {
            throw new Exception("El número de escritura es obligatorio.");
        }
        if (e.getFecha() == null) {
            throw new Exception("La fecha es obligatoria.");
        }
        if (e.getNotaria() == null || e.getNotaria().trim().isEmpty()) {
            throw new Exception("La notaría es obligatoria.");
        }
        // Tomo, folio y observaciones los dejamos opcionales
    }

    // ---------- INSERT ----------
    @Override
    public void insertar(EscrituraNotarial e) throws Exception {
        validarEscritura(e);

        try {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO escritura_notarial " +
                    "(eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones) " +
                    "VALUES (?,?,?,?,?,?,?)";

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

            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // ---------- UPDATE ----------
    @Override
    public void actualizar(EscrituraNotarial e) throws Exception {
        if (e.getId() == null) {
            throw new Exception("La escritura debe tener ID para actualizarse.");
        }
        validarEscritura(e);

        try {
            conn.setAutoCommit(false);

            String sql = "UPDATE escritura_notarial SET " +
                    "eliminado = ?, nro_escritura = ?, fecha = ?, notaria = ?, " +
                    "tomo = ?, folio = ?, observaciones = ? " +
                    "WHERE id = ?";

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

            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // ---------- DELETE ----------
    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null) {
            throw new Exception("El ID no puede ser null.");
        }

        try {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM escritura_notarial WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception ex) {
            conn.rollback();
            throw ex;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    // ---------- GET BY ID ----------
    @Override
    public EscrituraNotarial getById(Long id) throws Exception {
        String sql = "SELECT id, eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones " +
                     "FROM escritura_notarial WHERE id = ?";

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

    // ---------- GET ALL ----------
    @Override
    public List<EscrituraNotarial> getAll() throws Exception {
        List<EscrituraNotarial> lista = new ArrayList<>();

        String sql = "SELECT id, eliminado, nro_escritura, fecha, notaria, tomo, folio, observaciones " +
                     "FROM escritura_notarial";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRowToEscritura(rs));
            }
        }

        return lista;
    }

    // ---------- MAPPER ----------
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
