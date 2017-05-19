package com.app.modelo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

public class DatosCombo {
	private int id;
	private String nombre;

	public DatosCombo() {
		// TODO Auto-generated constructor stub
	}

	public DatosCombo(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public List<DatosCombo> getListDatosCombo(String datos){
		List<DatosCombo> datosCombo=new ArrayList<DatosCombo>();
 		JsonParser parser = new JsonParser();
 		Object obje = parser.parse(datos);
 		JsonArray array=(JsonArray)obje;
 		for(int x=0;x<array.size();x++){
 			String dato=array.get(x).toString();
 			Object obj = parser.parse(dato);
 	 		JsonArray arra=(JsonArray)obj;
 			int codigo=arra.get(0).getAsInt();
 			String nombre=arra.get(1).getAsString();
 			DatosCombo combo=new DatosCombo(codigo,nombre);
 			datosCombo.add(combo);
 		}
		return datosCombo;
	}
}
