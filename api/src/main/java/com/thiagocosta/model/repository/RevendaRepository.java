package com.thiagocosta.model.repository;

import java.util.List;

import javax.persistence.Tuple;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.thiagocosta.model.entity.Revenda;


public interface RevendaRepository extends JpaRepository<Revenda, Integer> {
	
	List<Revenda> findAllByOrderByIdAsc();
	
	 	@Query(
			  value = "SELECT * "
			  		+ " FROM REVENDA r "
			  		+ "WHERE MONTH(r.data_coleta) = COALESCE(:mes, MONTH(r.data_coleta))"
			  		+ "and r.distribuidora_id = COALESCE(:idDistribuidora, r.distribuidora_id)  ", 
			  nativeQuery = true)
	    List<Revenda> findByMesAndidDistribuidora(@Param("mes") Integer mes, @Param("idDistribuidora")Integer idDistribuidora);	
	
	@Query(value = "SELECT m.nome municipio, \n" + 
			"       Round(Avg(r.valor_venda), 4) AS VALOR_VENDA \n" + 
			"FROM   revenda r \n" + 
			"       JOIN distribuidora d \n" + 
			"         ON ( d.id = r.distribuidora_id ) \n" + 
			"       JOIN municipio m \n" + 
			"         ON ( m.id = d.municipio_id ) \n" + 
			"GROUP  BY m.nome ",
			nativeQuery = true)
	List<Tuple> getMediaDePrecoDeVendaPorMunicipio();
	
	@Query(value = "SELECT r.sigla regiao, \n" + 
			"       e.sigla estado, \n" + 
			"       m.nome  municipio, \n" + 
			"       d.nome  distribuidora, d.cnpj  distribuidora_cnpj, \n" + 
			"       rvd.data_coleta, rvd.valor_compra, rvd.valor_venda, \n" + 
			"       c.nome  combustivel, c.unidade_medida \n" + 
			"FROM   regiao r \n" + 
			"       JOIN estado e ON( e.regiao_id = r.id ) \n" + 
			"       JOIN municipio m ON( m.estado_id = e.id ) \n" + 
			"       JOIN distribuidora d ON( d.municipio_id = m.id ) \n" + 
			"       JOIN revenda rvd ON( rvd.distribuidora_id = d.id ) \n" + 
			"       JOIN combustivel c ON( c.id = rvd.combustivel_id ) \n" + 
			"ORDER  BY regiao, estado, municipio, data_coleta, combustivel",
		   nativeQuery = true)
	List<Tuple> getInformacoesImportadasPorSiglaDaRegiao();
	
	@Query(value = "SELECT d.nome  distribuidora, d.cnpj  distribuidora_cnpj, \n" + 
			"       r.sigla regiao, \n" + 
			"       e.sigla estado, \n" + 
			"       m.nome  municipio, \n" + 
			"       rvd.data_coleta, rvd.valor_compra, rvd.valor_venda, \n" + 
			"       c.nome  combustivel, c.unidade_medida \n" + 
			"FROM   distribuidora d  \n" + 
			"       JOIN municipio m ON( m.id = d.municipio_id ) \n" + 
			"       JOIN estado e ON( e.id = m.estado_id ) \n" + 
			"       JOIN regiao r ON( r.id = e.regiao_id) \n" + 
			"       JOIN revenda rvd ON( rvd.distribuidora_id = d.id ) \n" + 
			"       JOIN combustivel c ON( c.id = rvd.combustivel_id ) \n" + 
			"GROUP BY distribuidora, distribuidora_cnpj, regiao, estado, municipio, data_coleta, combustivel, unidade_medida,valor_compra, valor_venda \n" + 
			"ORDER BY distribuidora, regiao, estado, municipio, data_coleta, combustivel",
			nativeQuery = true)
	List<Tuple> getInformacoesAgrupadosPorDistribuidora();
	
	@Query(value = "SELECT rvd.data_coleta, d.nome  distribuidora, d.cnpj  distribuidora_cnpj, \n" + 
			"       r.sigla regiao, \n" + 
			"       e.sigla estado, \n" + 
			"       m.nome  municipio, \n" + 
			"       rvd.valor_compra, rvd.valor_venda, \n" + 
			"       c.nome  combustivel, c.unidade_medida \n" + 
			"FROM   revenda rvd \n" + 
			"       JOIN combustivel c ON( c.id = rvd.combustivel_id ) \n" + 
			"       JOIN distribuidora d ON( d.id = rvd.distribuidora_id) \n" + 
			"       JOIN municipio m ON( m.id = d.municipio_id ) \n" + 
			"       JOIN estado e ON( e.id = m.estado_id ) \n" + 
			"       JOIN regiao r ON( r.id = e.regiao_id) \n" + 
			"GROUP BY data_coleta, distribuidora, distribuidora_cnpj, regiao, estado, municipio, combustivel, unidade_medida,valor_compra, valor_venda \n" + 
			"ORDER BY data_coleta, regiao, estado, municipio, distribuidora, combustivel",
			nativeQuery = true)
	List<Tuple> getInformacoesAgrupadosPorData();
	
	@Query(value = "SELECT m.nome municipio, \n" + 
			"       Round(Avg(r.valor_venda), 4) media_valor_venda, \n" +
			"       Round(Avg(r.valor_compra), 4) media_valor_compra \n" +
			"FROM   revenda r \n" + 
			"       JOIN distribuidora d ON ( d.id = r.distribuidora_id ) \n" + 
			"       JOIN municipio m ON ( m.id = d.municipio_id ) \n" + 
			"GROUP  BY municipio ",
			nativeQuery = true)
	List<Tuple> getMediaValorCompraVendaPorMunicipio();
	
	@Query(value = "SELECT d.bandeira bandeira, \n" + 
			"       Round(Avg(r.valor_venda), 4) media_valor_venda, \n" +
			"       Round(Avg(r.valor_compra), 4) media_valor_compra \n" +
			"FROM   revenda r \n" + 
			"       JOIN distribuidora d ON ( d.id = r.distribuidora_id ) \n" +
			"GROUP  BY bandeira ",
			nativeQuery = true)
	List<Tuple> getMediaValorCompraVendaPorBandeira();

   
}
