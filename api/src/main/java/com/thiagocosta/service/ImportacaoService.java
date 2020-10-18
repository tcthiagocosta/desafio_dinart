package com.thiagocosta.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thiagocosta.model.entity.Combustivel;
import com.thiagocosta.model.entity.Distribuidora;
import com.thiagocosta.model.entity.Estado;
import com.thiagocosta.model.entity.Municipio;
import com.thiagocosta.model.entity.Regiao;
import com.thiagocosta.model.entity.Revenda;
import com.thiagocosta.model.repository.CombustivelRepository;
import com.thiagocosta.model.repository.DistribuidoraRepository;
import com.thiagocosta.model.repository.EstadoRepository;
import com.thiagocosta.model.repository.MunicipioRepository;
import com.thiagocosta.model.repository.RegiaoRepository;
import com.thiagocosta.model.repository.RevendaRepository;

@Service
public class ImportacaoService {
	
	@Autowired
	private CombustivelRepository combustivelRepository;
	
	@Autowired
	private DistribuidoraRepository distribuidoraRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private RegiaoRepository regiaoRepository;
	
	@Autowired
	private RevendaRepository revendaRepository;
	
	HashMap<String, Regiao> mapRegioes = new HashMap<String, Regiao>();
	HashMap<String, Estado> mapEstados = new HashMap<String, Estado>();
	HashMap<String, Municipio> mapMunicipios = new HashMap<String, Municipio>();
	HashMap<String, Distribuidora> mapDistribuidoras = new HashMap<String, Distribuidora>();
	HashMap<String, Combustivel> mapCombustiveis =  new HashMap<String, Combustivel>();
	
	
	public void importaArquivoCsv(MultipartFile file) throws Exception {
		
		Reader reader = new InputStreamReader(file.getInputStream(), Charset.forName("UTF-16"));
		
		
		BufferedReader arquivo = new BufferedReader(reader);
		arquivo.readLine(); // Pulando a primeira linha(Cabeçalho)
		arquivo.readLine();

		String linha = arquivo.readLine();
		String[] colunas = null;
		while (linha != null) {
			colunas = linha.split("\t");

			Regiao regiao = importaRegiao(colunas);
			Estado estado = importaEstado(colunas, regiao);
			Municipio municipio = importaMunicipio(colunas, estado);
			Distribuidora distribuidora = importaDistribuidora(colunas, municipio);
			Combustivel combustivel = importaCombustivel(colunas);
			importaRevenda(colunas, distribuidora, combustivel);

			linha = arquivo.readLine();
			
			if (linha == null || "".equals(linha.trim()))
				linha = arquivo.readLine();
		}

		reader.close();
	}
	
	private Regiao importaRegiao(String[] colunas) throws Exception {
		Regiao regiao = null;
		
		if (mapRegioes.get(colunas[0]) != null) { // Evita uma consulta no banco para verificar se o registro existe
    		regiao = mapRegioes.get(colunas[0]);
    	} else {
    		regiao = new Regiao();
    		regiao.setSigla(colunas[0]);
    		regiao = regiaoRepository.save(regiao);
    		mapRegioes.put(regiao.getSigla(), regiao);
    	}

		return regiao;
	}
	
	private Estado importaEstado(String[] colunas, Regiao regiao) throws Exception {
		Estado estado = null;
		
		if (mapEstados.get(colunas[1]) != null) { // Evita uma consulta no banco para verificar se o registro existe
    		estado = mapEstados.get(colunas[1]);
    	} else {
    		estado = new Estado();
    		estado.setSigla(colunas[1]);
    		estado.setRegiao(regiao);
    		estado = estadoRepository.save(estado);
    		mapEstados.put(estado.getSigla(), estado);
    	}
		
		return estado;
	}
	
	private Municipio importaMunicipio(String[] colunas, Estado estado) throws Exception {
		Municipio municipio = null;
		
		if (mapMunicipios.get(colunas[2]) != null) { // Evita uma consulta no banco para verificar se o registro existe
    		municipio = mapMunicipios.get(colunas[2]);
    	} else {
    		municipio = new Municipio();
    		municipio.setNome(colunas[2]);
    		municipio.setEstado(estado);
    		municipio = municipioRepository.save(municipio);
    		mapMunicipios.put(municipio.getNome(), municipio);
    	}
		
		return municipio;
	}
	
	private Distribuidora importaDistribuidora(String[] colunas, Municipio municipio) throws Exception {
		Distribuidora distribuidora = null;
		
		if (mapDistribuidoras.get(colunas[4]) != null) { // Evita uma consulta no banco para verificar se o registro existe
    		distribuidora = mapDistribuidoras.get(colunas[4]);
    	} else {
    		distribuidora = new Distribuidora();
    		distribuidora.setMunicipio(municipio);
    		distribuidora.setNome(colunas[3]);
    		distribuidora.setCnpj(colunas[4]);
    		distribuidora.setBandeira(colunas[10]);
    		distribuidora = distribuidoraRepository.save(distribuidora);
    		mapDistribuidoras.put(distribuidora.getCnpj(), distribuidora);
    	}
		
		return distribuidora;
	}
	
	private Combustivel importaCombustivel(String[] colunas) throws Exception {
		
		Combustivel combustivel = null;
    	
    	if (mapCombustiveis.get(colunas[5]) != null) { // Evita uma consulta no banco para verificar se o registro existe
    		combustivel = mapCombustiveis.get(colunas[5]);    		
    	} else {
    		combustivel = new Combustivel();
    		combustivel.setNome(colunas[5]);
    		combustivel.setUnidadeMedida(colunas[9]);	
    		combustivel = combustivelRepository.save(combustivel);
    		mapCombustiveis.put(combustivel.getNome(), combustivel);
    	}
    	
    	return combustivel;
	}
	
	private void importaRevenda(String[] colunas, Distribuidora distribuidora, Combustivel combustivel) throws Exception {
		Revenda revenda = new Revenda();
    	double valorVenda = 0.0000;
    	double valorCompra = 0.0000;
    	
    	if (!"".equals(colunas[7].trim())) {
    		String valorStr = colunas[7].replaceAll("[^0-9?!\\,]","");
    		valorVenda = Double.parseDouble(valorStr.replace(",", "."));
    	}
    	
    	if (!"".equals(colunas[8].trim())) {
    		String valorStr = colunas[8].replaceAll("[^0-9?!\\,]","");
    		valorCompra = Double.parseDouble(valorStr.replace(",", "."));
    	}
    	
    	revenda.setValorVenda(valorVenda);
    	revenda.setValorCompra(valorCompra);
    	
    	String data = colunas[6].replaceAll("[^0-9?!\\/]","");
    	revenda.setDataColeta(new SimpleDateFormat("dd/MM/yyyy").parse(data));
    	revenda.setDistribuidora(distribuidora);
    	revenda.setCombustivel(combustivel);
    	revenda = revendaRepository.save(revenda);
	}
	
}
