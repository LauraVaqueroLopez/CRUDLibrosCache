package org.example.crudlibros;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {
    private final LibroRepository libroRepository;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerPorId(String isbn) {
        return libroRepository.findById(isbn);
    }

    public Libro guardar(Libro libro) {
        return libroRepository.save(libro);
    }

    public void eliminar(String isbn) {
        libroRepository.deleteById(isbn);
    }
}