package com.thiagocosta.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.thiagocosta.model.entity.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByNome(String username);

    boolean existsByNome(String username);
    
    @Query(" select nome " +
            "from Usuario " +            
            "order by nome")
    List<String> listarUsuarios();
    
    List<Usuario> findAllByOrderByIdAsc();
}
