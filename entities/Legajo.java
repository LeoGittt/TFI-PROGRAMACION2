package entities;

// Leonel: Esta es la entidad secundaria (B)
// TODO: Completar atributos, constructores, getters/setters y toString según la consigna
public class Legajo {
    private Long id;
    private Boolean eliminado;
    private String nroLegajo;
    private String categoria;
    private Estado estado; // Enum ACTIVO/INACTIVO
    private java.time.LocalDate fechaAlta;
    private String observaciones;

    // Enum para el estado
    public enum Estado { ACTIVO, INACTIVO }

    // TODO: Agregar constructores (vacío y completo)
    // TODO: Agregar getters y setters
    // TODO: Agregar método toString legible
}
