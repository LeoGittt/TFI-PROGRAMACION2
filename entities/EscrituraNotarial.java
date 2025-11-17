package entities;

import java.time.LocalDate;

/**
 * Entidad que representa una Escritura Notarial asociada a una Propiedad.
 * Relación 1→1 unidireccional desde Propiedad.
 */
public class EscrituraNotarial {
    private Long id;
    private Boolean eliminado;
    private String nroEscritura;
    private LocalDate fecha;
    private String notaria;
    private String tomo;
    private String folio;
    private String observaciones;
    // Campo temporal para persistencia (FK a propiedad)
    private Long propiedadId;

    // Constructor vacío
    public EscrituraNotarial() {}

    // Constructor completo
    public EscrituraNotarial(Long id, Boolean eliminado, String nroEscritura, LocalDate fecha, String notaria, String tomo, String folio, String observaciones) {
        this.id = id;
        this.eliminado = eliminado;
        this.nroEscritura = nroEscritura;
        this.fecha = fecha;
        this.notaria = notaria;
        this.tomo = tomo;
        this.folio = folio;
        this.observaciones = observaciones;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getNroEscritura() { return nroEscritura; }
    public void setNroEscritura(String nroEscritura) { this.nroEscritura = nroEscritura; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getNotaria() { return notaria; }
    public void setNotaria(String notaria) { this.notaria = notaria; }
    public String getTomo() { return tomo; }
    public void setTomo(String tomo) { this.tomo = tomo; }
    public String getFolio() { return folio; }
    public void setFolio(String folio) { this.folio = folio; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Long getPropiedadId() { return propiedadId; }
    public void setPropiedadId(Long propiedadId) { this.propiedadId = propiedadId; }

    @Override
    public String toString() {
        return "EscrituraNotarial{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", nroEscritura='" + nroEscritura + '\'' +
                ", fecha=" + fecha +
                ", notaria='" + notaria + '\'' +
                ", tomo='" + tomo + '\'' +
                ", folio='" + folio + '\'' +
                ", observaciones='" + observaciones + '\'' +
                '}';
    }
}
