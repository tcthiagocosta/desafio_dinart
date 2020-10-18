package com.thiagocosta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Tuple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thiagocosta.model.dto.RetornoAgrupadoPorDataDTO;
import com.thiagocosta.model.dto.RetornoAgrupadoPorDataDTO.InformacaoDTO;
import com.thiagocosta.model.dto.RetornoAgrupadoPorDistribuidoraDTO;
import com.thiagocosta.model.dto.RetornoAgrupadoPorDistribuidoraDTO.InformacaoDistribuidoraDTO;
import com.thiagocosta.model.dto.RetornoAgrupadoPorSiglaDTO;
import com.thiagocosta.model.dto.RetornoAgrupadoPorSiglaDTO.InformacaoAgrupadoPorSiglaDTO;
import com.thiagocosta.model.dto.RevendaDTO;
import com.thiagocosta.model.entity.Revenda;
import com.thiagocosta.model.repository.RevendaRepository;
import com.thiagocosta.util.Funcao;

@Service
public class RevendaService {

	@Autowired
	public RevendaRepository revendaRepository;
	
	@Autowired
	public CombustivelService combustivelService;

	@Autowired
	public DistribuidoraService distribuidoraService; 
	
	public RevendaDTO salvar(RevendaDTO revendaDTO) throws Exception {
		
		Revenda revenda = new Revenda();
		revenda.setDataColeta(Funcao.strToDate(revendaDTO.getDataColeta()));
		revenda.setCombustivel(revendaDTO.getCombustivel());
		revenda.setDistribuidora(revendaDTO.getDistribuidora());
		revenda.setValorCompra(Funcao.strToDouble(revendaDTO.getValorCompra()));
		revenda.setValorVenda(Funcao.strToDouble(revendaDTO.getValorVenda()));
		
		revendaRepository.save(revenda);
		
		revendaDTO.setId(revenda.getId());
		
		return revendaDTO;
	}
	
	
	public List<ObjectNode> getMediaDePrecoDeVendaPorMunicipio() throws Exception {
		List<Tuple> lista = revendaRepository.getMediaDePrecoDeVendaPorMunicipio();
		List<ObjectNode> result = Funcao.formataJson(lista);
		
		return result;
	}
	
	public List<ObjectNode> getMediaValorCompraVendaPorMunicipio() throws Exception {
		List<Tuple> lista = revendaRepository.getMediaValorCompraVendaPorMunicipio();
		List<ObjectNode> jsonFormatado = Funcao.formataJson(lista);
		
		return jsonFormatado;
	}
	
	public List<ObjectNode> getMediaValorCompraVendaPorBandeira() throws Exception {
		List<Tuple> lista = revendaRepository.getMediaValorCompraVendaPorBandeira();
		List<ObjectNode> jsonFormatado = Funcao.formataJson(lista);
		
		return jsonFormatado;
	}
	
	public List<RetornoAgrupadoPorSiglaDTO> getInformacoesImportadasPorSiglaDaRegiao() throws Exception {
		List<Tuple> lista = revendaRepository.getInformacoesImportadasPorSiglaDaRegiao();
		List<ObjectNode> jsonFormatado = Funcao.formataJson(lista);
		
		List<RetornoAgrupadoPorSiglaDTO> jsonFormatadoRetorno = new ArrayList<RetornoAgrupadoPorSiglaDTO>();
		
		String sigla = null;
		String siglaAnterior = null;
		List<InformacaoAgrupadoPorSiglaDTO> listaInformacaoAgrupadoPorSiglaDTO = null;
		RetornoAgrupadoPorSiglaDTO retornoAgrupadoPorSiglaDTO = null;
		
		for (ObjectNode objectNode : jsonFormatado) {
			
			sigla = objectNode.get("REGIAO").asText();

			if (siglaAnterior == null) {
				
				retornoAgrupadoPorSiglaDTO = new RetornoAgrupadoPorSiglaDTO();
				retornoAgrupadoPorSiglaDTO.setSigla(sigla);
				
				listaInformacaoAgrupadoPorSiglaDTO = new ArrayList<InformacaoAgrupadoPorSiglaDTO>();
				listaInformacaoAgrupadoPorSiglaDTO.add(criaInformacaoAgrupadoPorSiglaDTO(objectNode));
				
				retornoAgrupadoPorSiglaDTO.setListaInformacaoAgrupadoPorSiglaDTO(listaInformacaoAgrupadoPorSiglaDTO);
				
				siglaAnterior = sigla;
				
			} else if (sigla.equals(siglaAnterior)) {
				
				siglaAnterior = sigla;
				listaInformacaoAgrupadoPorSiglaDTO.add(criaInformacaoAgrupadoPorSiglaDTO(objectNode));
				retornoAgrupadoPorSiglaDTO.setListaInformacaoAgrupadoPorSiglaDTO(listaInformacaoAgrupadoPorSiglaDTO);
				
			} else {
				
				jsonFormatadoRetorno.add(retornoAgrupadoPorSiglaDTO);
				
				siglaAnterior = null;
				
				retornoAgrupadoPorSiglaDTO = new RetornoAgrupadoPorSiglaDTO();
				retornoAgrupadoPorSiglaDTO.setSigla(sigla);
				
				listaInformacaoAgrupadoPorSiglaDTO = new ArrayList<InformacaoAgrupadoPorSiglaDTO>();
				listaInformacaoAgrupadoPorSiglaDTO.add(criaInformacaoAgrupadoPorSiglaDTO(objectNode));
				
				retornoAgrupadoPorSiglaDTO.setListaInformacaoAgrupadoPorSiglaDTO(listaInformacaoAgrupadoPorSiglaDTO);
			}
		}
		
		jsonFormatadoRetorno.add(retornoAgrupadoPorSiglaDTO);
		
		return jsonFormatadoRetorno;
	}
	
	private InformacaoAgrupadoPorSiglaDTO criaInformacaoAgrupadoPorSiglaDTO (ObjectNode objectNode) throws Exception {
		
		InformacaoAgrupadoPorSiglaDTO informacaoAgrupadoPorSiglaDTO = new InformacaoAgrupadoPorSiglaDTO();
		informacaoAgrupadoPorSiglaDTO.setNomeDistribuidora(objectNode.get("DISTRIBUIDORA").asText());
		informacaoAgrupadoPorSiglaDTO.setCnpjDistribuidora(objectNode.get("DISTRIBUIDORA_CNPJ").asText());
		informacaoAgrupadoPorSiglaDTO.setEstado(objectNode.get("ESTADO").asText());
		informacaoAgrupadoPorSiglaDTO.setMunicipio(objectNode.get("MUNICIPIO").asText());
		informacaoAgrupadoPorSiglaDTO.setValorCompra(objectNode.get("VALOR_COMPRA").asDouble());
		informacaoAgrupadoPorSiglaDTO.setValorCompra(objectNode.get("VALOR_VENDA").asDouble());
		informacaoAgrupadoPorSiglaDTO.setCombustivel(objectNode.get("COMBUSTIVEL").asText());
		informacaoAgrupadoPorSiglaDTO.setUnidadeMedida(objectNode.get("UNIDADE_MEDIDA").asText());
		informacaoAgrupadoPorSiglaDTO.setDataColeta(Funcao.convertDataSqlToDataStr(objectNode.get("DATA_COLETA").asText()));

		return informacaoAgrupadoPorSiglaDTO;
	}
	

	public List<RetornoAgrupadoPorDataDTO> getInformacoesAgrupadosPorData() throws Exception {
		
		List<Tuple> lista = revendaRepository.getInformacoesAgrupadosPorData();
		List<ObjectNode> jsonFormatado = Funcao.formataJson(lista);
		
		List<RetornoAgrupadoPorDataDTO> jsonFormatadoRetorno = new ArrayList<RetornoAgrupadoPorDataDTO>();
		
		String data = null;
		String dataAnterior = null;
		List<InformacaoDTO> listaInformacaoDTO = null;
		RetornoAgrupadoPorDataDTO retornoAgrupadoPorDataDTO = null;
		
		for (ObjectNode objectNode : jsonFormatado) {
			
			
			data = Funcao.convertDataSqlToDataStr(objectNode.get("DATA_COLETA").asText());

			if (dataAnterior == null) {
				
				retornoAgrupadoPorDataDTO = new RetornoAgrupadoPorDataDTO();
				retornoAgrupadoPorDataDTO.setData(data);
				
				listaInformacaoDTO = new ArrayList<InformacaoDTO>();
				listaInformacaoDTO.add(criarInformacaoDTO(objectNode));
				
				retornoAgrupadoPorDataDTO.setListaImformacao(listaInformacaoDTO);
				
				dataAnterior = data;
				
			} else if (data.equals(dataAnterior)) {
				
				dataAnterior = data;
				listaInformacaoDTO.add(criarInformacaoDTO(objectNode));
				retornoAgrupadoPorDataDTO.setListaImformacao(listaInformacaoDTO);
				
			} else {
				
				jsonFormatadoRetorno.add(retornoAgrupadoPorDataDTO);
				
				dataAnterior = null;
				
				retornoAgrupadoPorDataDTO = new RetornoAgrupadoPorDataDTO();
				retornoAgrupadoPorDataDTO.setData(data);
				
				listaInformacaoDTO = new ArrayList<InformacaoDTO>();
				listaInformacaoDTO.add(criarInformacaoDTO(objectNode));
				
				retornoAgrupadoPorDataDTO.setListaImformacao(listaInformacaoDTO);
			}
			
			
		}
		
		return jsonFormatadoRetorno;
	}
	
	private InformacaoDTO criarInformacaoDTO (ObjectNode objectNode) {
		
		InformacaoDTO informacaoDTO = new InformacaoDTO();
		informacaoDTO.setNomeDistribuidora(objectNode.get("DISTRIBUIDORA").asText());
		informacaoDTO.setCnpjDistribuidora(objectNode.get("DISTRIBUIDORA_CNPJ").asText());
		informacaoDTO.setEstado(objectNode.get("REGIAO").asText());
		informacaoDTO.setEstado(objectNode.get("ESTADO").asText());
		informacaoDTO.setMunicipio(objectNode.get("MUNICIPIO").asText());
		informacaoDTO.setValorCompra(objectNode.get("VALOR_COMPRA").asDouble());
		informacaoDTO.setValorCompra(objectNode.get("VALOR_VENDA").asDouble());
		informacaoDTO.setCombustivel(objectNode.get("COMBUSTIVEL").asText());
		informacaoDTO.setUnidadeMedida(objectNode.get("UNIDADE_MEDIDA").asText());

		return informacaoDTO;
	}
	
	public List<RetornoAgrupadoPorDistribuidoraDTO> getInformacoesAgrupadosPorDistribuidora() throws Exception {
		
		List<Tuple> lista = revendaRepository.getInformacoesAgrupadosPorData();
		List<ObjectNode> jsonFormatado = Funcao.formataJson(lista);
		
		List<RetornoAgrupadoPorDistribuidoraDTO> jsonFormatadoRetorno = new ArrayList<RetornoAgrupadoPorDistribuidoraDTO>();
		
		String distribuidora = null;
		String distribuidoraAnterior = null;
		List<InformacaoDistribuidoraDTO> listaInformacaoDTO = null;
		RetornoAgrupadoPorDistribuidoraDTO agrupadoPorDistribuidoraDTO = null;
		
		for (ObjectNode objectNode : jsonFormatado) {
			
			
			distribuidora = objectNode.get("DISTRIBUIDORA").asText();

			if (distribuidoraAnterior == null) {
				
				agrupadoPorDistribuidoraDTO = new RetornoAgrupadoPorDistribuidoraDTO();
				agrupadoPorDistribuidoraDTO.setNomeDistribuidora(distribuidora);
				
				listaInformacaoDTO = new ArrayList<InformacaoDistribuidoraDTO>();
				listaInformacaoDTO.add(criaInformacaoDistribuidoraDTO(objectNode));
				
				agrupadoPorDistribuidoraDTO.setListaInformacaoDistribuidoraDTO(listaInformacaoDTO);
				
				distribuidoraAnterior = distribuidora;
				
			} else if (distribuidora.equals(distribuidoraAnterior)) {
				
				distribuidoraAnterior = distribuidora;
				listaInformacaoDTO.add(criaInformacaoDistribuidoraDTO(objectNode));
				agrupadoPorDistribuidoraDTO.setListaInformacaoDistribuidoraDTO(listaInformacaoDTO);
				
			} else {
				
				jsonFormatadoRetorno.add(agrupadoPorDistribuidoraDTO);
				
				distribuidoraAnterior = null;
				
				agrupadoPorDistribuidoraDTO = new RetornoAgrupadoPorDistribuidoraDTO();
				agrupadoPorDistribuidoraDTO.setNomeDistribuidora(distribuidora);
				
				listaInformacaoDTO = new ArrayList<InformacaoDistribuidoraDTO>();
				listaInformacaoDTO.add(criaInformacaoDistribuidoraDTO(objectNode));
				
				agrupadoPorDistribuidoraDTO.setListaInformacaoDistribuidoraDTO(listaInformacaoDTO);
			}
			
		}
		
		return jsonFormatadoRetorno;
	}
	
	private InformacaoDistribuidoraDTO criaInformacaoDistribuidoraDTO (ObjectNode objectNode) throws Exception {
		
		InformacaoDistribuidoraDTO informacaoDistribuidoraDTO = new InformacaoDistribuidoraDTO();
		informacaoDistribuidoraDTO.setCnpjDistribuidora(objectNode.get("DISTRIBUIDORA_CNPJ").asText());
		informacaoDistribuidoraDTO.setEstado(objectNode.get("REGIAO").asText());
		informacaoDistribuidoraDTO.setEstado(objectNode.get("ESTADO").asText());
		informacaoDistribuidoraDTO.setMunicipio(objectNode.get("MUNICIPIO").asText());
		informacaoDistribuidoraDTO.setValorCompra(objectNode.get("VALOR_COMPRA").asDouble());
		informacaoDistribuidoraDTO.setValorCompra(objectNode.get("VALOR_VENDA").asDouble());
		informacaoDistribuidoraDTO.setCombustivel(objectNode.get("COMBUSTIVEL").asText());
		informacaoDistribuidoraDTO.setUnidadeMedida(objectNode.get("UNIDADE_MEDIDA").asText());
		informacaoDistribuidoraDTO.setDataColeta(Funcao.convertDataSqlToDataStr(objectNode.get("DATA_COLETA").asText()));

		return informacaoDistribuidoraDTO;
	}

	public List<Revenda> obterTodos() {
		return revendaRepository.findAllByOrderByIdAsc();		
	}

	public Optional<Revenda> getPorId(Integer id) {
		return revendaRepository.findById(id);
	}

	public void deletar(Revenda revenda) {
		revendaRepository.delete(revenda);		
	}
	
	public Revenda atualizar(Revenda revenda) {
    	return revendaRepository.save(revenda);
    }


	public List<Revenda> getRevendas(Integer mes, Integer idDistribuidora) {
		return revendaRepository.findByMesAndidDistribuidora(mes, idDistribuidora);
	}
	
}
