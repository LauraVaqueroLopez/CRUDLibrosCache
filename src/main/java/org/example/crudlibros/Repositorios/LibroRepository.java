package org.example.crudlibros.Repositorios;

import org.example.crudlibros.Modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, String> {

}