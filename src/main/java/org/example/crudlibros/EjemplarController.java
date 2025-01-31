package org.example.crudlibros;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
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

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Ejemplar ejemplar, BindingResult result) {
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
        return ResponseEntity.ok(ejemplarService.guardar(ejemplar));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @Valid @RequestBody Ejemplar ejemplar, BindingResult result) {
        if (!ejemplarService.obtenerPorId(id).isPresent()) {
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

        ejemplar.setId(id);
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
