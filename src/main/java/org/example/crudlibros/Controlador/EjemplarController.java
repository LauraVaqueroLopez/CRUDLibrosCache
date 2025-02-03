package org.example.crudlibros.Controlador;

import org.example.crudlibros.Modelo.Ejemplar;
import org.example.crudlibros.Servicios.EjemplarService;
import org.example.crudlibros.Modelo.Libro;
import org.example.crudlibros.Servicios.LibroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;
    private final LibroService libroService;

    public EjemplarController(EjemplarService ejemplarService, LibroService libroService) {
        this.ejemplarService = ejemplarService;
        this.libroService = libroService;
    }

    @GetMapping
    public List<Ejemplar> listar() {
        return ejemplarService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ejemplar> obtener(@PathVariable Integer id) {

        Optional<Ejemplar> ejemplar = ejemplarService.obtenerPorId(id);
        return ejemplar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/{isbn}/{estado}")
    public ResponseEntity<?> crear(@PathVariable String isbn, @PathVariable String estado) {
        // Buscar el libro correspondiente al ISBN proporcionado
        Optional<Libro> libroOptional = libroService.obtenerPorId(isbn);
        if (libroOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("El libro con el ISBN proporcionado no existe.");
        }

        // Validar el estado proporcionado
        if (!estado.matches("^(disponible|prestado|dañado)$")) {
            return ResponseEntity.badRequest().body("El estado debe ser 'disponible', 'prestado' o 'dañado'.");
        }

        // Crear el nuevo ejemplar con el libro asociado y el estado recibido
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setIsbn(libroOptional.get());
        ejemplar.setEstado(estado);

        // Guardar el ejemplar
        return ResponseEntity.status(201).body(ejemplarService.guardar(ejemplar));
    }

    @PutMapping("/{id}/{estado}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @PathVariable String estado) {
        // Buscar el ejemplar por ID
        Optional<Ejemplar> ejemplarOptional = ejemplarService.obtenerPorId(id);
        if (ejemplarOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Validar el estado proporcionado
        if (!estado.matches("^(disponible|prestado|dañado)$")) {
            return ResponseEntity.badRequest().body("El estado debe ser 'disponible', 'prestado' o 'dañado'.");
        }

        // Actualizar el estado del ejemplar
        Ejemplar ejemplar = ejemplarOptional.get();
        ejemplar.setEstado(estado);

        // Guardar el ejemplar actualizado
        return ResponseEntity.ok(ejemplarService.guardar(ejemplar));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!ejemplarService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        ejemplarService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}