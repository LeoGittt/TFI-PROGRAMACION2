package main;

import java.sql.Connection;
import java.sql.SQLException;
import config.DatabaseConnection;

public class TestConexion {
public static void main(String[] args) {
        /*
        Se usa un bloque try-with-resources para asegurar que la conexión
        se cierre automáticamente al salir del bloque.
        */
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("- Conexión establecida con éxito! -");
            } else {
                System.out.println("- NO se pudo establecer la conexión. -");
            }
        } catch (SQLException e) {
            // Manejo de errores en la conexión a la base de datos
            System.err.println("ERROR al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace(); // Imprime el stack trace completo para depuración
        }
    }
}