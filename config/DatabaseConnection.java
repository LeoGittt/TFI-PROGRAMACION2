package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * DatabaseConnection
 * <p>
 * Combina la lógica de ambas versiones: primero intenta cargar configuración desde
 * el archivo `config/db.properties`. Si no existe o falla, utiliza system properties
 * o valores por defecto (localhost). Carga el driver MySQL y valida la configuración.
 */
public final class DatabaseConnection {
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    static {
        try {
            // Carga del driver (requerido en algunas versiones de Java)
            Class.forName("com.mysql.cj.jdbc.Driver");
            loadConfiguration();
            validateConfiguration();
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError("Error: No se encontró el driver JDBC de MySQL: " + e.getMessage());
        } catch (IllegalStateException e) {
            throw new ExceptionInInitializerError("Error en la configuración de la base de datos: " + e.getMessage());
        }
    }

    private DatabaseConnection() {
        throw new UnsupportedOperationException("Esta es una clase utilitaria y no debe ser instanciada");
    }

    private static void loadConfiguration() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("config/db.properties")) {
            props.load(fis);
        } catch (IOException e) {
            // No encontré el archivo, se usarán system properties o valores por defecto
        }

        URL = System.getProperty("db.url", props.getProperty("jdbc.url", "jdbc:mysql://localhost:3306/tfi_prog2"));
        USER = System.getProperty("db.user", props.getProperty("jdbc.user", "root"));
        PASSWORD = System.getProperty("db.password", props.getProperty("jdbc.password", ""));
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void validateConfiguration() {
        if (URL == null || URL.trim().isEmpty()) {
            throw new IllegalStateException("La URL de la base de datos no está configurada");
        }
        if (USER == null || USER.trim().isEmpty()) {
            throw new IllegalStateException("El usuario de la base de datos no está configurado");
        }
        if (PASSWORD == null) {
            throw new IllegalStateException("La contraseña de la base de datos no está configurada");
        }
    }
}
