package org.example.crudlibros.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Date;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dni", nullable = false, length = 15)
    @NotBlank(message = "El DNI no puede estar en blanco")
    @Pattern(regexp = "^[0-9]{8}[A-Za-z]{1}$", message = "El DNI debe tener un formato válido (8 dígitos seguidos de una letra)")
    private String dni;

    @Column(name = "nombre", nullable = false, length = 100)
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Pattern(regexp = "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s]+$", message = "El nombre solo puede contener caracteres alfanuméricos y espacios")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
    private String nombre;

    @Column(name = "email", nullable = false, length = 100)
    @NotBlank(message = "El email no puede estar en blanco")
    @Pattern(regexp = "^[A-Za-z0-9._-]+@gmail.com$", message = "El email debe tener un formato válido y debe ser de Gmail (ejemplo: usuario@gmail.com)")

    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Pattern(regexp = "^[a-zA-Z0-9]{4,12}$", message = "La contraseña debe ser alfanumérica y tener entre 4 y 12 caracteres")
    private String password;

    @Column(name = "tipo", nullable = false)
    @NotBlank(message = "El tipo de usuario no puede estar en blanco")
    @Pattern(regexp = "^(normal|administrador)$", message = "El tipo debe ser 'normal' o 'administrador'")
    private String tipo;

    @Column(name = "penalizacionhasta")
    private Date penalizacionhasta;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getPenalizacionhasta() {
        return penalizacionhasta;
    }

    public void setPenalizacionhasta(Date penalizacionhasta) {
        this.penalizacionhasta = penalizacionhasta;
    }
}
