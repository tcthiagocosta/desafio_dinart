package com.thiagocosta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thiagocosta.model.entity.Usuario;
import com.thiagocosta.model.repository.UsuarioRepository;



@Service
public class UsuarioService  {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario salvar(Usuario usuario) throws Exception {

        boolean exists = usuarioRepository.existsByNome(usuario.getNome());

        if (exists) {
            throw new Exception(usuario.getNome());
        }

        return usuarioRepository.save(usuario);
    }

	public List<String> listarNomeUsuarios() {
		return usuarioRepository.listarUsuarios();		
	}
	
	

	public List<Usuario> obterTodos() {
		return usuarioRepository.findAllByOrderByIdAsc();
	}
	
    public Optional<Usuario> getPorId(Integer id) {
        return usuarioRepository
                .findById(id);
                
    }
    
    public void deletar(Usuario usuario) {
    	usuarioRepository.delete(usuario);
    }
    
    public Usuario atualizar(Usuario usuario) {
    	return usuarioRepository.save(usuario);
    }
	
}
