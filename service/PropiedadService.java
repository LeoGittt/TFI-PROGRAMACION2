package service;

import config.DatabaseConnection;
import dao.EscrituraNotarialDaoJdbc;
import dao.PropiedadDaoJdbc;
import entities.EscrituraNotarial;
import entities.Propiedad;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class PropiedadService implements GenericService<Propiedad, Long> {

    private final PropiedadDaoJdbc propiedadDao;
    private final EscrituraNotarialDaoJdbc escrituraDao;

    public PropiedadService() {
        this.propiedadDao = new PropiedadDaoJdbc();
        this.escrituraDao = new EscrituraNotarialDaoJdbc();
    }

    private void validarPropiedad(Propiedad p) throws Exception {
        if (p == null) throw new Exception("La propiedad no puede ser null.");
        if (p.getPadronCatastral() == null || p.getPadronCatastral().trim().isEmpty())
            throw new Exception("El padrón catastral es obligatorio.");
        if (p.getDireccion() == null || p.getDireccion().trim().isEmpty())
            throw new Exception("La dirección es obligatoria.");
        if (p.getSuperficieM2() == null || p.getSuperficieM2().compareTo(java.math.BigDecimal.ZERO) <= 0)
            throw new Exception("La superficie debe ser mayor a 0.");
        if (p.getDestino() == null) throw new Exception("El destino (RES/COM) es obligatorio.");
        if (p.getAntiguedad() == null || p.getAntiguedad() < 0) throw new Exception("La antigüedad no puede ser negativa.");
    }

    private boolean existePadron(String padron, Long idExcluir, Connection conn) throws Exception {
        String sql = "SELECT COUNT(*) FROM propiedad WHERE padron_catastral = ?";
        if (idExcluir != null) sql += " AND id <> ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, padron);
            if (idExcluir != null) ps.setLong(2, idExcluir);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    @Override
    public void insertar(Propiedad p) throws Exception {
        validarPropiedad(p);
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                if (existePadron(p.getPadronCatastral(), null, conn)) {
                    throw new Exception("El padrón catastral ya existe en la base de datos.");
                }

                // Si la escritura existe pero no tiene id, crearla en la misma transacción
                if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() == null) {
                    escrituraDao.crear(p.getEscrituraNotarial(), conn);
                }

                propiedadDao.crear(p, conn);

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public void actualizar(Propiedad p) throws Exception {
        if (p.getId() == null) throw new Exception("La propiedad debe tener un ID para actualizarse.");
        validarPropiedad(p);
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);

                if (existePadron(p.getPadronCatastral(), p.getId(), conn)) {
                    throw new Exception("El padrón catastral ya existe en otra propiedad.");
                }

                // Manejo de escritura: si hay escritura sin id, crearla
                if (p.getEscrituraNotarial() != null && p.getEscrituraNotarial().getId() == null) {
                    escrituraDao.crear(p.getEscrituraNotarial(), conn);
                }

                propiedadDao.actualizar(p, conn);

                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public void eliminar(Long id) throws Exception {
        if (id == null) throw new Exception("El ID no puede ser null.");
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                propiedadDao.eliminar(id, conn);
                conn.commit();
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }

    @Override
    public Propiedad getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return propiedadDao.leer(id, conn);
        }
    }

    @Override
    public List<Propiedad> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return propiedadDao.leerTodos(conn);
        }
    }

    public Propiedad getByPadronCatastral(String padron) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT id FROM propiedad WHERE padron_catastral = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, padron);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        return propiedadDao.leer(id, conn);
                    }
                }
            }
            return null;
        }
    }
}
