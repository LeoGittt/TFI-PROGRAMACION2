package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Clase para gestionar la conexión a la base de datos MySQL.
 * Lee las credenciales desde el archivo config/db.properties.
 */
public class DatabaseConnection {
    public static Connection getConnection() throws Exception {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream("config/db.properties");
        props.load(fis);
        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.user");
        String pass = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
