package org.example.crudlibros;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/libros")
@CacheConfig(cacheNames = {"libros"})
public class LibroController {

    private final LibroService libroService;

    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public List<Libro> listar() {
        return libroService.obtenerTodos();
    }

    @GetMapping("/{isbn}")
    @Cacheable
    public ResponseEntity<Libro> obtener(@PathVariable String isbn) {

        try {
            Thread.sleep(3000);
            Optional<Libro> libro = libroService.obtenerPorId(isbn);
            return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
 }

    @PostMapping
    public ResponseEntity<Libro> crear(@RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.guardar(libro));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> actualizar(@PathVariable String isbn, @RequestBody Libro libro) {
        if (!libroService.obtenerPorId(isbn).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        libro.setIsbn(isbn);
        return ResponseEntity.ok(libroService.guardar(libro));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> eliminar(@PathVariable String isbn) {
        if (!libroService.obtenerPorId(isbn).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        libroService.eliminar(isbn);
        return ResponseEntity.noContent().build();
    }
}