package org.example.crudlibros.Controlador;

import jakarta.validation.Valid;
import org.example.crudlibros.Modelo.Libro;
import org.example.crudlibros.Servicios.LibroService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> crear(@Valid @RequestBody Libro libro, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            FieldError fieldError = (FieldError) error;
                            return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                        }
                        return error.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorMessages);
        }
        return ResponseEntity.ok(libroService.guardar(libro));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<?> actualizar(@PathVariable String isbn, @Valid @RequestBody Libro libro, BindingResult result) {
        if (!libroService.obtenerPorId(isbn).isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(error -> {
                        if (error instanceof FieldError) {
                            FieldError fieldError = (FieldError) error;
                            return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                        }
                        return error.getDefaultMessage();
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errorMessages);
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