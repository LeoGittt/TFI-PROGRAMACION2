package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Clase para obtener la conexión a la base de datos

// Configuración de Prueba:
        // URL: jdbc:mysql://localhost:3306/tfi_prog2
        // Usuario: root
        // Contraseña: vacía (común en desarrollo local)

public final class DatabaseConnection {
    private static String URL = System.getProperty("db.url", "jdbc:mysql://localhost:3306/tfi_prog2");
    private static String USER = System.getProperty("db.user", "root");
    private static String PASSWORD = System.getProperty("db.password", "Lobo-Sabio-868");

    static {
        try {
            // Carga  del driver (requerido en algunas versiones de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
            validateConfiguration();
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Error: No se encontró el driver JDBC de MySQL: " + e.getMessage());
        } catch (IllegalStateException e) {
            throw new ExceptionInInitializerError("Error en la configuración de la base de datos: " + e.getMessage());
        }
    }

    // Constructor privado para prevenir instanciación.
    private DatabaseConnection() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada");
    }

    //Obtiene una nueva conexión a la base de datos.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Validaciones de configuracion
    private static void validateConfiguration() {
        if (URL == null || URL.trim().isEmpty()) {
            throw new IllegalStateException("La URL de la base de datos no está configurada");
        }
        if (USER == null || USER.trim().isEmpty()) {
            throw new IllegalStateException("El usuario de la base de datos no está configurado");
        }
        // PASSWORD puede ser vacío (común en MySQL local con usuario root sin contraseña)
        // Solo validamos que no sea null
        if (PASSWORD == null) {
            throw new IllegalStateException("La contraseña de la base de datos no está configurada");
        }
    }
}