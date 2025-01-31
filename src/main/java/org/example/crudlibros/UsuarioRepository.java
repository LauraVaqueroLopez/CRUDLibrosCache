package org.example.crudlibros;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Aquí puedes agregar consultas personalizadas si es necesario
}
