package entities;

// Leonel: Esta es la entidad principal (A)
// TODO: Completar atributos, constructores, getters/setters y toString según la consigna
public class Empleado {
    private Long id;
    private Boolean eliminado;
    private String nombre;
    private String apellido;
    private String dni;
    private String email;
    private java.time.LocalDate fechaIngreso;
    private String area;
    private Legajo legajo; // Relación 1→1 unidireccional

    // TODO: Agregar constructores (vacío y completo)
    // TODO: Agregar getters y setters
    // TODO: Agregar método toString legible
}
