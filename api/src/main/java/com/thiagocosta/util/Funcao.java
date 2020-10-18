package com.thiagocosta.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Funcao {

	public static List<ObjectNode> formataJson(List<Tuple> results) {

		List<ObjectNode> json = new ArrayList<ObjectNode>();

		ObjectMapper mapper = new ObjectMapper();

		for (Tuple t : results) {
		    List<TupleElement<?>> cols = t.getElements();

		    ObjectNode one = mapper.createObjectNode();

		    for (TupleElement<?> col : cols) {
		    	one.put(col.getAlias(), t.get(col.getAlias()).toString());
		    }

		    json.add(one);
		}

		return json;
	}
	
	public static double strToDouble(String valor) {
		StringBuffer buffer = new StringBuffer(valor);
		
		while (buffer.indexOf(".") > 0) {
			buffer.deleteCharAt(buffer.indexOf("."));
		}
		
		valor = buffer.toString();
		valor = valor.replace(",", ".");
		
		return Double.parseDouble(valor);
	}
	
	public static String convertDataSqlToDataStr(String dataSql) throws Exception {
		if (vazia(dataSql)) return null;

		SimpleDateFormat formatoSql = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat formatoRetorno = new SimpleDateFormat("dd/MM/yyy");
		
		try {
			return formatoRetorno.format(formatoSql.parse(dataSql));
		} catch (Exception e) {
			throw new Exception("Data invalida");
		}
	}
	
	public static boolean vazia(String string) {
		return string == null || string.trim().equals("");
	}
	
	public static Date strToDate(String data) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date date = (Date) formatter.parse(data);
		
		return date;
	}
}
