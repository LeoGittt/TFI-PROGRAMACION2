package entities;

// Empleado (A) - completo según la especificación
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

    public Empleado() {}

    public Empleado(Long id, Boolean eliminado, String nombre, String apellido, String dni, String email, java.time.LocalDate fechaIngreso, String area, Legajo legajo) {
        this.id = id;
        this.eliminado = eliminado;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.area = area;
        this.legajo = legajo;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public java.time.LocalDate getFechaIngreso() { return fechaIngreso; }
    public void setFechaIngreso(java.time.LocalDate fechaIngreso) { this.fechaIngreso = fechaIngreso; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public Legajo getLegajo() { return legajo; }
    public void setLegajo(Legajo legajo) { this.legajo = legajo; }

    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", email='" + email + '\'' +
                ", fechaIngreso=" + fechaIngreso +
                ", area='" + area + '\'' +
                ", legajoId=" + (legajo != null ? legajo.getId() : null) +
                '}';
    }
}
