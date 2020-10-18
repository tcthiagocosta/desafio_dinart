package com.thiagocosta.resources;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.thiagocosta.model.entity.Usuario;
import com.thiagocosta.service.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Api
public class UsuarioResource {

    private final UsuarioService servicoUsuario;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Salva um usuario na base de dados")
    public Usuario salvar(@RequestBody Usuario usuario) {
       try{
          return servicoUsuario.salvar(usuario);
       } catch (Exception e) {
           throw new ResponseStatusException(
                   HttpStatus.BAD_REQUEST,
                   e.getMessage());
       }
    }
    
    @GetMapping("/listarNomesUsuarios")
    @ApiOperation(value = "Recupera os nomes dos usuario na base de dados")
    public List<String> listarNomeUsuario() {    	   
           return servicoUsuario.listarNomeUsuarios();       
    }
    
    @GetMapping
    @ApiOperation(value = "Recupera os usuarios na base de dados")
    public List<Usuario> obterTodos() {
        return servicoUsuario.obterTodos();
    }
    
    @GetMapping("{id}")
    @ApiOperation(value = "Recupera a usuario pelo id na base de dados")	
    public Usuario acharPorId(@PathVariable Integer id) {
        return servicoUsuario.getPorId(id)
        		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove um usuario da base de dados")
    public void deletar(@PathVariable Integer id){
    	servicoUsuario.getPorId(id)
                .map( usuario -> {
                	servicoUsuario.deletar(usuario);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
    
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualiza um usuario na base de dados")
    public void atualizar( @PathVariable Integer id, @RequestBody Usuario usuarioAtualizado ) {

    	servicoUsuario.getPorId(id)
                .map( usuario -> {
                	usuarioAtualizado.setId(usuario.getId());
                    return servicoUsuario.atualizar(usuarioAtualizado);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }
    
}
