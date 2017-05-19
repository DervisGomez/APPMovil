package com.app.modelo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class DatosFormato {
	private int id;
	private String descripcion;
	private String sigla;
	private int centronNegocio;
	public DatosFormato() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DatosFormato(int id, String descripcion, String sigla,
			int centronNegocio) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.sigla = sigla;
		this.centronNegocio = centronNegocio;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public int getCentronNegocio() {
		return centronNegocio;
	}
	public void setCentronNegocio(int centronNegocio) {
		this.centronNegocio = centronNegocio;
	}
	
	public List<DatosFormato> getListDatosFormato(String datos){
		List<DatosFormato> datosCombo=new ArrayList<DatosFormato>();
 		JsonParser parser = new JsonParser();
 		Object obje = parser.parse(datos);
 		JsonArray array=(JsonArray)obje;
 		for(int x=0;x<array.size();x++){
 			String dato=array.get(x).toString();
 			Object obj = parser.parse(dato);
 	 		JsonArray arra=(JsonArray)obj;
 			int codigo=arra.get(0).getAsInt();
 			String descripcion=arra.get(1).getAsString();
 			String sigla=arra.get(2).getAsString();
 			int centroNegocio=arra.get(3).getAsInt();
 			DatosFormato combo=new DatosFormato(codigo,descripcion,sigla,centroNegocio);
 			datosCombo.add(combo);
 		}
		return datosCombo;
	}
}
