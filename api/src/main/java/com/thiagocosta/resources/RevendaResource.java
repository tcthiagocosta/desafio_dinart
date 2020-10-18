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
import org.springframework.web.server.ResponseStatusException;

import com.thiagocosta.model.dto.RevendaDTO;
import com.thiagocosta.model.entity.Revenda;
import com.thiagocosta.service.RevendaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/revenda")
@RequiredArgsConstructor
@Api
public class RevendaResource {
	
	private final RevendaService revendaService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Salva uma Revenda na base de dados")
	public RevendaDTO salvar(@RequestBody RevendaDTO revendaDTO) {
		try {
			return revendaService.salvar(revendaDTO);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@GetMapping("/listar")
	@ApiOperation(value = "Retodas lista das revendas na base de dados")
    public List<Revenda> obterTodos() {
        return revendaService.obterTodos();
    }
	
    @GetMapping("{id}")
    @ApiOperation(value = "Recupera uma revenda pelo id na base de dados")	
    public Revenda acharPorId(@PathVariable Integer id) {
        return revendaService.getPorId(id)
        		.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada"));
    }
    
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Remove uma revenda da base de dados")
    public void deletar(@PathVariable Integer id){
    	revendaService.getPorId(id)
                .map( revenda -> {
                	revendaService.deletar(revenda);
                    return Void.TYPE;
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada"));
    }
    
    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Atualiza uma revenda na base de dados")
    public void atualizar( @PathVariable Integer id, @RequestBody Revenda revendaAtualizada ) {

    	revendaService.getPorId(id)
                .map( revenda -> {
                	revendaAtualizada.setId(revenda.getId());
                    return revendaService.atualizar(revendaAtualizada);
                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Revenda não encontrada"));
    }
    
    @GetMapping
    @ApiOperation(value = "Retorna lista de revendas filtrada por data ou distribuidora")
    public List<Revenda> pesquisar(
        @RequestParam(value = "mes", required = false, defaultValue = "") Integer mes,
        @RequestParam(value = "idDistribuidora", required = false) Integer idDistribuidora
    ) {
       return revendaService.getRevendas(mes, idDistribuidora);

        }
	
	@GetMapping("mediaDePrecoDeVendaPorMunicipio")
	@ApiOperation(value = "Retorne a média de preço de combustível com base no nome do município")
	public ResponseEntity<?> getMediaDePrecoDeVendaPorMunicipio() throws Exception {
		try {			
			return new ResponseEntity<>(revendaService.getMediaDePrecoDeVendaPorMunicipio(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("informacoesAgrupadosPorData")
	@ApiOperation(value = "Retorne os dados agrupados pela data da coleta")
	public ResponseEntity<?> getInformacoesAgrupadosPorData() throws Exception {
		try {			
			return new ResponseEntity<>(revendaService.getInformacoesAgrupadosPorData(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("mediaValorCompraVendaPorMunicipio")
	@ApiOperation(value = "Retorne o valor médio do valor da compra e do valor da venda por município")
	public ResponseEntity<?> getMediaValorCompraVendaPorMunicipio() throws Exception {
		try {			
			return new ResponseEntity<>(revendaService.getMediaValorCompraVendaPorMunicipio(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("informacoesImportadasPorSiglaDaRegiao")
	@ApiOperation(value = "Retorne todas as informações importadas por sigla da região")
	public ResponseEntity<?> getInformacoesImportadasPorSiglaDaRegiao() throws Exception {
		try {			
			return new ResponseEntity<>(revendaService.getInformacoesImportadasPorSiglaDaRegiao(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("mediaValorCompraVendaPorBandeira")
	@ApiOperation(value = "Retorne o valor médio do valor da compra e do valor da venda por bandeira")
	public ResponseEntity<?> getMediaValorCompraVendaPorBandeira() throws Exception {
		try {			
			return new ResponseEntity<>(revendaService.getMediaValorCompraVendaPorBandeira(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}	
	
	@GetMapping("informacoesAgrupadosPorDistribuidora")
	@ApiOperation(value = "Retorne os dados agrupados por distribuidora")
	public ResponseEntity<?> getInformacoesAgrupadosPorDistribuidora() throws Exception {
		try {			
			return new ResponseEntity<>(revendaService.getInformacoesAgrupadosPorDistribuidora(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

}
