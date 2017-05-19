package com.app.modelo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PreguntaFormato {
	
	private int id;
	private String pregunta;
	private int tipo;
	private List<DatosCombo> opciones;

	public PreguntaFormato() {
		// TODO Auto-generated constructor stub
	}
	

	public PreguntaFormato(int id, String pregunta, int tipo,
			List<DatosCombo> opciones) {
		super();
		this.id = id;
		this.pregunta = pregunta;
		this.tipo = tipo;
		this.opciones = opciones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPregunta() {
		return pregunta;
	}

	public void setPregunta(String pregunta) {
		this.pregunta = pregunta;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public List<DatosCombo> getOpciones() {
		return opciones;
	}

	public void setOpciones(List<DatosCombo> opciones) {
		this.opciones = opciones;
	}
	
	public List<PreguntaFormato> getLista(String datos){
		List<PreguntaFormato> lista =new ArrayList<PreguntaFormato>();
		JsonParser parser = new JsonParser();
		Object obje = parser.parse(datos);
 		JsonObject array=(JsonObject)obje;
 		JsonArray r=array.get("preguntas").getAsJsonArray();
		
 		for(int x=0;x<r.size();x++){
			JsonObject obj=r.get(x).getAsJsonObject();
			String pregunta =obj.get("pregdesc").getAsString();
			int id =obj.get("pregindx").getAsInt();
			int tipo=Integer.valueOf(obj.get("pregtico").getAsString());
			JsonArray arr=obj.get("opciones").getAsJsonArray();
			List<DatosCombo> opciones=new ArrayList<DatosCombo>();
			
			for(int y=0;y<arr.size();y++){
				JsonObject objO=arr.get(y).getAsJsonObject();
				int ido=objO.get("opciindx").getAsInt();
				String opc="";
				if (!(objO.get("opcidesc").isJsonNull())) {
					opc=objO.get("opcidesc").getAsString();
				}				
				DatosCombo dato=new DatosCombo(ido,opc);
				opciones.add(dato);
			}
			
			PreguntaFormato pregunt=new PreguntaFormato(id,pregunta,tipo,opciones);
			lista.add(pregunt);
		}
		
 		return lista;
	}

}
