package org.example.crudlibros;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EjemplarService {

    private final EjemplarRepository ejemplarRepository;

    public EjemplarService(EjemplarRepository ejemplarRepository) {
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
