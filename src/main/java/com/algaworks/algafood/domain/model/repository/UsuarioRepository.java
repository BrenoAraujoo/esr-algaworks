package com.algaworks.algafood.domain.model.repository;

import com.algaworks.algafood.domain.model.Usuario;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
