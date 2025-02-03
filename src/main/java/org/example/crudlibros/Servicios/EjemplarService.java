package org.example.crudlibros.Servicios;

import org.example.crudlibros.Modelo.Ejemplar;
import org.example.crudlibros.Repositorios.EjemplarRepository;
import org.example.crudlibros.Repositorios.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjemplarService {
    private final LibroRepository libroRepository;

    private final EjemplarRepository ejemplarRepository;

    public EjemplarService(LibroRepository libroRepository, EjemplarRepository ejemplarRepository) {
        this.libroRepository = libroRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    public List<Ejemplar> obtenerTodos() {
        return ejemplarRepository.findAll();
    }

    public Optional<Ejemplar> obtenerPorId(Integer id) {
        return ejemplarRepository.findById(id);
    }

    public Ejemplar guardar(Ejemplar ejemplar) {
        return ejemplarRepository.save(ejemplar);
    }

    public void eliminar(Integer id) {
        ejemplarRepository.deleteById(id);
    }
}