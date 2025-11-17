package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.FileInputStream;

// Fede: Clase para obtener la conexión a la base de datos
public class DatabaseConnection {
    public static Connection getConnection() throws Exception {
        Properties props = new Properties();
        // TODO: Cambiar la ruta si es necesario
        FileInputStream fis = new FileInputStream("config/db.properties");
        props.load(fis);
        String url = props.getProperty("jdbc.url");
        String user = props.getProperty("jdbc.user");
        String pass = props.getProperty("jdbc.password");
        return DriverManager.getConnection(url, user, pass);
    }
}
