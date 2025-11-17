package dao;

import config.DatabaseConnection;
import entities.EscrituraNotarial;
import entities.Propiedad;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PropiedadDaoJdbc implements GenericDao<Propiedad> {

    @Override
    public void crear(Propiedad p) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            crear(p, conn);
        }
    }

    @Override
    public void crear(Propiedad p, Connection conn) throws Exception {
        String sql = "INSERT INTO propiedad (eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad, escritura_id) VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setBoolean(1, Boolean.TRUE.equals(p.getEliminado()));
            ps.setString(2, p.getPadronCatastral());
            ps.setString(3, p.getDireccion());
            ps.setBigDecimal(4, p.getSuperficieM2());
            ps.setString(5, p.getDestino() != null ? p.getDestino().name() : null);
            ps.setInt(6, p.getAntiguedad() != null ? p.getAntiguedad() : 0);

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
    }

    @Override
    public Propiedad leer(long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leer(id, conn);
        }
    }

    @Override
    public Propiedad leer(long id, Connection conn) throws Exception {
        String sql = "SELECT id, eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad, escritura_id FROM propiedad WHERE id = ?";
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
    public List<Propiedad> leerTodos() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return leerTodos(conn);
        }
    }

    @Override
    public List<Propiedad> leerTodos(Connection conn) throws Exception {
        List<Propiedad> lista = new ArrayList<>();
        String sql = "SELECT id, eliminado, padron_catastral, direccion, superficie_m2, destino, antiguedad, escritura_id FROM propiedad";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapRowToPropiedad(rs));
            }
        }
        return lista;
    }

    @Override
    public void actualizar(Propiedad p) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            actualizar(p, conn);
        }
    }

    @Override
    public void actualizar(Propiedad p, Connection conn) throws Exception {
        String sql = "UPDATE propiedad SET eliminado = ?, padron_catastral = ?, direccion = ?, superficie_m2 = ?, destino = ?, antiguedad = ?, escritura_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, Boolean.TRUE.equals(p.getEliminado()));
            ps.setString(2, p.getPadronCatastral());
            ps.setString(3, p.getDireccion());
            ps.setBigDecimal(4, p.getSuperficieM2());
            ps.setString(5, p.getDestino() != null ? p.getDestino().name() : null);
            ps.setInt(6, p.getAntiguedad() != null ? p.getAntiguedad() : 0);

            if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() != null) {
                ps.setLong(7, p.getEscrituraNotarial().getId());
            } else {
                ps.setNull(7, Types.BIGINT);
            }

            ps.setLong(8, p.getId());
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
        String sql = "UPDATE propiedad SET eliminado = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, true);
            ps.setLong(2, id);
            ps.executeUpdate();
        }
    }

    private Propiedad mapRowToPropiedad(ResultSet rs) throws SQLException {
        Propiedad p = new Propiedad();
        p.setId(rs.getLong("id"));
        p.setEliminado(rs.getBoolean("eliminado"));
        p.setPadronCatastral(rs.getString("padron_catastral"));
        p.setDireccion(rs.getString("direccion"));
        p.setSuperficieM2(rs.getBigDecimal("superficie_m2"));
        String dest = rs.getString("destino");
        if (dest != null) p.setDestino(Propiedad.Destino.valueOf(dest));
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
