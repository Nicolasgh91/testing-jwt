package ar.edu.uade.api.deliverygo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "comercios", uniqueConstraints = @UniqueConstraint(columnNames = "cuit"))
public class Comercio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false, unique = true)
    private String cuit;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private Boolean activo = true;

    protected Comercio() {
    }

    public Comercio(String nombre, String cuit, String email, Boolean activo) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.email = email;
        this.activo = activo;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String v) {
        nombre = v;
    }

    public String getCuit() {
        return cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String v) {
        email = v;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean v) {
        activo = v;
    }
}
