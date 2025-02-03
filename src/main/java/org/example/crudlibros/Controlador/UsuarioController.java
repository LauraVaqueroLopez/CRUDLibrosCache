package org.example.crudlibros.Controlador;

import jakarta.validation.Valid;
import org.example.crudlibros.Modelo.Usuario;
import org.example.crudlibros.Servicios.UsuarioService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@CacheConfig(cacheNames = {"usuarioCache"})
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<Usuario> listar() {
        return usuarioService.obtenerTodos();
    }

    @Cacheable
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {

        Optional<Usuario> usuario = usuarioService.obtenerPorId(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> crear(@Valid @RequestBody Usuario usuario, BindingResult result) {
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
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody Usuario usuario, BindingResult result) {
        if (!usuarioService.obtenerPorId(id).isPresent()) {
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

        usuario.setId(id);
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (!usuarioService.obtenerPorId(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
