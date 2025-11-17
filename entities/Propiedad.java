package entities;

import java.math.BigDecimal;

// Leonel: Entidad principal (A) - Propiedad
public class Propiedad {
    private Long id;
    private Boolean eliminado;
    private String padronCatastral;
    private String direccion;
    private BigDecimal superficieM2;
    private Destino destino; // Enum RES, COM
    private Integer antiguedad;
    private EscrituraNotarial escrituraNotarial; // Relación 1→1 unidireccional

    public enum Destino { RES, COM }

    // Constructor vacío
    public Propiedad() {}

    // Constructor completo
    public Propiedad(Long id, Boolean eliminado, String padronCatastral, String direccion, BigDecimal superficieM2, Destino destino, Integer antiguedad, EscrituraNotarial escrituraNotarial) {
        this.id = id;
        this.eliminado = eliminado;
        this.padronCatastral = padronCatastral;
        this.direccion = direccion;
        this.superficieM2 = superficieM2;
        this.destino = destino;
        this.antiguedad = antiguedad;
        this.escrituraNotarial = escrituraNotarial;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Boolean getEliminado() { return eliminado; }
    public void setEliminado(Boolean eliminado) { this.eliminado = eliminado; }
    public String getPadronCatastral() { return padronCatastral; }
    public void setPadronCatastral(String padronCatastral) { this.padronCatastral = padronCatastral; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public BigDecimal getSuperficieM2() { return superficieM2; }
    public void setSuperficieM2(BigDecimal superficieM2) { this.superficieM2 = superficieM2; }
    public Destino getDestino() { return destino; }
    public void setDestino(Destino destino) { this.destino = destino; }
    public Integer getAntiguedad() { return antiguedad; }
    public void setAntiguedad(Integer antiguedad) { this.antiguedad = antiguedad; }
    public EscrituraNotarial getEscrituraNotarial() { return escrituraNotarial; }
    public void setEscrituraNotarial(EscrituraNotarial escrituraNotarial) { this.escrituraNotarial = escrituraNotarial; }

    @Override
    public String toString() {
        return "Propiedad{" +
                "id=" + id +
                ", eliminado=" + eliminado +
                ", padronCatastral='" + padronCatastral + '\'' +
                ", direccion='" + direccion + '\'' +
                ", superficieM2=" + superficieM2 +
                ", destino=" + destino +
                ", antiguedad=" + antiguedad +
                ", escrituraNotarial=" + (escrituraNotarial != null ? escrituraNotarial.getId() : null) +
                '}';
    }
}
