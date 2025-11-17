package entities;

// Legajo (B) - completo según la especificación
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

    public Legajo() {}

    public Legajo(Long id, Boolean eliminado, String nroLegajo, String categoria, Estado estado, java.time.LocalDate fechaAlta, String observaciones) {
        this.id = id;
        this.eliminado = eliminado;
        this.nroLegajo = nroLegajo;
        this.categoria = categoria;
        this.estado = estado;
        this.fechaAlta = fechaAlta;
        this.observaciones = observaciones;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getNroLegajo() { return nroLegajo; }
    public void setNroLegajo(String nroLegajo) { this.nroLegajo = nroLegajo; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public java.time.LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(java.time.LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    @Override
    public String toString() {
        return "Legajo{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nroLegajo='" + nroLegajo + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado=" + estado +
                ", fechaAlta=" + fechaAlta +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}

