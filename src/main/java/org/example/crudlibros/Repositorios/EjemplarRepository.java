package org.example.crudlibros.Repositorios;

import org.example.crudlibros.Modelo.Ejemplar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends JpaRepository<Ejemplar, Integer> {

}
