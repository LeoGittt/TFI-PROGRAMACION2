package service;

import config.DatabaseConnection;
import dao.EscrituraNotarialDaoJdbc;
import entities.EscrituraNotarial;

import java.sql.Connection;
import java.util.List;

public class EscrituraNotarialService implements GenericService<EscrituraNotarial, Long> {

    private final EscrituraNotarialDaoJdbc dao;

    public EscrituraNotarialService() {
        this.dao = new EscrituraNotarialDaoJdbc();
    }

    private void validarEscritura(EscrituraNotarial e) throws Exception {
        if (e == null) throw new Exception("La escritura no puede ser null.");
        if (e.getNroEscritura() == null || e.getNroEscritura().trim().isEmpty())
            throw new Exception("El número de escritura es obligatorio.");
        if (e.getFecha() == null) throw new Exception("La fecha es obligatoria.");
        if (e.getNotaria() == null || e.getNotaria().trim().isEmpty())
            throw new Exception("La notaría es obligatoria.");
    }

    @Override
    public void insertar(EscrituraNotarial e) throws Exception {
        validarEscritura(e);
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                dao.crear(e, conn);
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
    public void actualizar(EscrituraNotarial e) throws Exception {
        if (e.getId() == null) throw new Exception("La escritura debe tener ID para actualizarse.");
        validarEscritura(e);
        try (Connection conn = DatabaseConnection.getConnection()) {
            try {
                conn.setAutoCommit(false);
                dao.actualizar(e, conn);
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
                dao.eliminar(id, conn);
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
    public EscrituraNotarial getById(Long id) throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.leer(id, conn);
        }
    }

    @Override
    public List<EscrituraNotarial> getAll() throws Exception {
        try (Connection conn = DatabaseConnection.getConnection()) {
            return dao.leerTodos(conn);
        }
    }
}
