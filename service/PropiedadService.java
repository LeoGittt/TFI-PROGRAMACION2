package service;

import config.DatabaseConnection;          // ⚠ cambiá el nombre si tu clase es otra
import entities.Propiedad;
import entities.EscrituraNotarial;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropiedadService implements GenericService<Propiedad, Long> {

    private final Connection conn;

    public PropiedadService() throws Exception {
    	this.conn = DatabaseConnection.getConnection();
    }

    // ---------- VALIDACIONES ----------

    private void validarPropiedad(Propiedad p) throws Exception {
        if (p == null) throw new Exception("La propiedad no puede ser null.");

        if (p.getPadronCatastral() == null || p.getPadronCatastral().trim().isEmpty())
            throw new Exception("El padrón catastral es obligatorio.");

        if (p.getDireccion() == null || p.getDireccion().trim().isEmpty())
            throw new Exception("La dirección es obligatoria.");

        if (p.getSuperficieM2() == null || p.getSuperficieM2().compareTo(BigDecimal.ZERO) <= 0)
            throw new Exception("La superficie debe ser mayor a 0.");

        if (p.getDestino() == null)
            throw new Exception("El destino (RES/COM) es obligatorio.");

        if (p.getAntiguedad() == null || p.getAntiguedad() < 0)
            throw new Exception("La antigüedad no puede ser negativa.");
    }

    private boolean existePadron(String padron, Long idExcluir) throws SQLException {
        String sql = "SELECT COUNT(*) FROM propiedad WHERE padron_catastral = ?";
        if (idExcluir != null) {
            sql += " AND id <> ?";
        }

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, padron);
            if (idExcluir != null) {
                ps.setLong(2, idExcluir);
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    // ---------- CRUD ----------

    @Override
    public void insertar(Propiedad p) throws Exception {
        validarPropiedad(p);

        try {
            conn.setAutoCommit(false);

            if (existePadron(p.getPadronCatastral(), null)) {
                throw new Exception("El padrón catastral ya existe en la base de datos.");
            }

            String sql = "INSERT INTO propiedad " +
                    "(eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad, escritura_id) " +
                    "VALUES (?,?,?,?,?,?,?)";

            try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // eliminado
                ps.setBoolean(1, Boolean.TRUE.equals(p.getEliminado()));

                // resto de campos
                ps.setString(2, p.getPadronCatastral());
                ps.setString(3, p.getDireccion());
                ps.setBigDecimal(4, p.getSuperficieM2());
                ps.setString(5, p.getDestino().name()); // "RES" o "COM"
                ps.setInt(6, p.getAntiguedad() != null ? p.getAntiguedad() : 0);

                // FK escritura (puede ser null al crear)
                if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() != null) {
                    ps.setLong(7, p.getEscrituraNotarial().getId());
                } else {
                    ps.setNull(7, Types.BIGINT);
                }

                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        p.setId(rs.getLong(1));
                    }
                }
            }

            // --------- SIMULAR ERROR PARA VER ROLLBACK ---------
            // Descomentar esto para probar:
            // if (true) {
            //     throw new RuntimeException("Error simulado para probar rollback");
            // }
            // ----------------------------------------------------

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void actualizar(Propiedad p) throws Exception {
        if (p.getId() == null) {
            throw new Exception("La propiedad debe tener un ID para actualizarse.");
        }

        validarPropiedad(p);

        try {
            conn.setAutoCommit(false);

            if (existePadron(p.getPadronCatastral(), p.getId())) {
                throw new Exception("El padrón catastral ya existe en otra propiedad.");
            }

            String sql = "UPDATE propiedad SET " +
                    "eliminado = ?, padron_catastral = ?, direccion = ?, superficie_m2 = ?, " +
                    "destino = ?, antiguedad = ?, escritura_id = ? " +
                    "WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBoolean(1, Boolean.TRUE.equals(p.getEliminado()));
                ps.setString(2, p.getPadronCatastral());
                ps.setString(3, p.getDireccion());
                ps.setBigDecimal(4, p.getSuperficieM2());
                ps.setString(5, p.getDestino().name());
                ps.setInt(6, p.getAntiguedad() != null ? p.getAntiguedad() : 0);

                if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() != null) {
                    ps.setLong(7, p.getEscrituraNotarial().getId());
                } else {
                    ps.setNull(7, Types.BIGINT);
                }

                ps.setLong(8, p.getId());

                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null) throw new Exception("El ID no puede ser null.");

        try {
            conn.setAutoCommit(false);

            String sql = "DELETE FROM propiedad WHERE id = ?";

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                ps.executeUpdate();
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    @Override
    public Propiedad getById(Long id) throws Exception {
        String sql = "SELECT id, eliminado, padron_catastral, direccion, superficie_m2, " +
                     "destino, antiguedad, escritura_id " +
                     "FROM propiedad WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPropiedad(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Propiedad> getAll() throws Exception {
        List<Propiedad> lista = new ArrayList<>();
        String sql = "SELECT id, eliminado, padron_catastral, direccion, superficie_m2, " +
                     "destino, antiguedad, escritura_id " +
                     "FROM propiedad";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRowToPropiedad(rs));
            }
        }
        return lista;
    }

    public Propiedad getByPadronCatastral(String padron) throws Exception {
        String sql = "SELECT id, eliminado, padron_catastral, direccion, superficie_m2, " +
                     "destino, antiguedad, escritura_id " +
                     "FROM propiedad WHERE padron_catastral = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, padron);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToPropiedad(rs);
                }
            }
        }
        return null;
    }

    // ---------- MAPPER ROW -> OBJETO ----------

    private Propiedad mapRowToPropiedad(ResultSet rs) throws SQLException {
        Propiedad p = new Propiedad();
        p.setId(rs.getLong("id"));
        p.setEliminado(rs.getBoolean("eliminado"));
        p.setPadronCatastral(rs.getString("padron_catastral"));
        p.setDireccion(rs.getString("direccion"));
        p.setSuperficieM2(rs.getBigDecimal("superficie_m2"));
        p.setDestino(Propiedad.Destino.valueOf(rs.getString("destino")));
        p.setAntiguedad(rs.getInt("antiguedad"));

        long idEsc = rs.getLong("escritura_id");
        if (!rs.wasNull()) {
            EscrituraNotarial e = new EscrituraNotarial();
            e.setId(idEsc);
            p.setEscrituraNotarial(e);
        }

        return p;
    }
}
