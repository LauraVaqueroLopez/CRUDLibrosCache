package org.example.crudlibros.Modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @Column(name = "isbn", nullable = false, length = 20)
    @NotBlank(message = "El ISBN no puede estar en blanco")
    @Pattern(regexp = "[A-Z]{4}[0-9]{4}", message = "El ISBN debe tener un formato válido")
    private String isbn;

    @Column(name = "titulo", nullable = false, length = 200)
    @NotBlank(message = "El título no puede estar en blanco")
    @Pattern(regexp = "^[a-zA-Z0-9\s]+$", message = "El título solo puede contener caracteres alfanuméricos")
    @Size(max = 200, message = "El título no puede exceder los 200 caracteres")
    private String titulo;

    @Column(name = "autor", nullable = false, length = 100)
    @NotBlank(message = "El autor no puede estar en blanco")
    @Pattern(regexp = "^[a-zA-Z0-9\s]+$", message = "El autor solo puede contener caracteres alfanuméricos")
    @Size(max = 100, message = "El autor no puede exceder los 100 caracteres")
    private String autor;

    @OneToMany(mappedBy = "isbn")
    private Set<Ejemplar> ejemplars = new LinkedHashSet<>();

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Set<Ejemplar> getEjemplars() {
        return ejemplars;
    }

    public void setEjemplars(Set<Ejemplar> ejemplars) {
        this.ejemplars = ejemplars;
    }
}
